/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import cn.lichengwu.lang.StringUtil;
import cn.lichengwu.lucene.util.LuceneUtil;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IndexReader线程池
 * 
 * @author lichengwu
 * @created 2011-10-26
 * 
 * @version 1.0
 */
public class IndexReaderPool {

    private static final Logger logger = LoggerFactory.getLogger(IndexReaderPool.class);

    /**
     * 旧的IndexReader存活时间 10s
     */
    private static final int STALE_INDEXREADER_SURVIVAL_TIME = 10000;

    /**
     * 索引目录的根路径
     */
    private String indexRootDirectory = null;

    /**
     * 索引文件目录
     */
    private List<String> indexDirNameList = null;

    /**
     * 存放IndexReader的Map，Map里存放的都是已经实例化好的IndexReader
     */
    private final Map<String, IndexReader> indexReaderMap = new ConcurrentHashMap<String, IndexReader>();

    /**
     * <pre>
     * 待关闭的IndexReader。indexReader.reopen()之后，会产生新的IndexReader。
     * 但是旧的IndexReader有可能还被其他线程调用着。
     * 旧的IndexReader都要放置到staleIndexReadersMap里，10秒之后再释放资源。
     * </pre>
     */
    private final Map<Long, IndexReader> staleIndexReaderMap = new ConcurrentHashMap<Long, IndexReader>();

    /**
     * 初始化
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     */
    @PostConstruct
    protected void init() {
        logger.info(this.getClass().getSimpleName() + " start initialize");

		// indexRootDirectory =
		// ConfigUtil.getValueByKey("lucene.pool.index.path");
		// indexDirNameList =
		// ConfigUtil.getConfigValueListByKey("lucene.pool.index.dirs", ",");
		//
		// if (indexRootDirectory == null) {
		// throw new
		// IllegalAddException("no index root directory found for path:"
		// + indexRootDirectory);
		// }
		// if (indexDirNameList == null || indexDirNameList.size() < 1) {
		// throw new IllegalAddException("no index directory found for path:" +
		// indexRootDirectory);
		// }
		//
		// for (String indexDirName : indexDirNameList) {
		// try {
		// IndexReader indexReader = createIndexReader(indexDirName);
		// if (indexReader != null) {
		// indexReaderMap.put(indexDirName, indexReader);
		// }
		// } catch (Exception e) {
		// throw new RuntimeException(e);
		// }
		// }
        logger.info(this.getClass().getSimpleName() + " initialization completed");
    }

    /**
     * 创建IndexReader
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     * @param indexDirName
     * @return
     * @throws CorruptIndexException
     * @throws IOException
     */
    private IndexReader createIndexReader(String indexDirName) throws CorruptIndexException,
            IOException {
        File indexPath = LuceneUtil.getFilePath(indexRootDirectory, indexDirName);
        if (!LuceneUtil.isIndexDir(indexPath)) {
            logger.warn(indexDirName + " is not an index resource folder");
            return null;
        }
        if (LuceneUtil.ensurePath(indexPath)) {
            return IndexReader.open(FSDirectory.open(indexPath));
        }
        return null;
    }

    /**
     * 获得IndexReader
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     * @param indexDirName
     * @return
     */
    public IndexReader getIndexReader(String indexDirName) {
        if (StringUtil.isBlank(indexDirName)) {
            throw new IllegalArgumentException("the indexDirName must not be empty");
        }
        IndexReader indexReader = indexReaderMap.get(indexDirName);
        if (indexReader != null) {
            return refreshIndexReader(indexDirName, indexReader);
        }
        synchronized (indexReaderMap) {
            if (!indexReaderMap.containsKey(indexDirName)) {
                try {
                    indexReader = createIndexReader(indexDirName);
                } catch (CorruptIndexException e) {
                    logger.error(
                            "CorruptIndexException while creating IndexReader of {},the root cause is {}",
                            indexDirName, e.getMessage());
                } catch (IOException e) {
                    logger.error("IOException while creating IndexReader of {},{}", indexDirName,
                            e.getMessage());
                }
                if (indexReader != null)
                    indexReaderMap.put(indexDirName, indexReader);
            }
        }
        return indexReaderMap.get(indexDirName);
    }

    private synchronized IndexReader refreshIndexReader(String indexDirName, IndexReader indexReader) {
        try {
            closeStaleIndexReaders(staleIndexReaderMap);
            IndexReader newIndexReader = indexReader.reopen();
            if (newIndexReader != indexReader) {
                IndexReader oldIndexReader = indexReader;
                // oldIndexReader可能在其他线程使用，所以把他放到staleIndexReaderMap里面，10s后关闭
                staleIndexReaderMap.put(System.currentTimeMillis(), oldIndexReader);
                // 替换掉oldIndexReader
                indexReaderMap.put(indexDirName, newIndexReader);
            }
        } catch (Exception e) {
            logger.error("Exception while getting IndexReader of {},the root cause is {}",
                    indexDirName, e.getMessage());
        }
        return indexReaderMap.get(indexDirName);
    }

    /**
     * 关闭旧的IndexReader
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     * @param staleIndexReadersMap
     */
    private void closeStaleIndexReaders(Map<Long, IndexReader> staleIndexReadersMap) {
        Iterator<Entry<Long, IndexReader>> entryIteator = staleIndexReadersMap.entrySet()
                .iterator();
        while (entryIteator.hasNext()) {
            Entry<Long, IndexReader> entry = entryIteator.next();
            if ((System.currentTimeMillis() - entry.getKey()) > STALE_INDEXREADER_SURVIVAL_TIME) {
                try {
                    entry.getValue().close();
                    logger.info("a stale IndexWriter has been closed");
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    entryIteator.remove();
                    logger.info("delete a stale IndexReader from pool");
                }
            }
        }
    }

    /**
     * 销毁IndexReader
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     */
    public void destroy() {
        Iterator<Entry<String, IndexReader>> iterator = indexReaderMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, IndexReader> entry = iterator.next();
            IndexReader indexReader = entry.getValue();
            try {
                indexReader.close();
                indexReader = null;
            } catch (IOException e) {
                logger.info("IOException while closing IndexReader whose indexDirName is {}",
                        entry.getKey());
            }
        }
        indexReaderMap.clear();
        logger.info("{} destroyed", this.getClass().getSimpleName());
    }

    /**
     * 线程池中IndexReader数量
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     * @return
     */
    public int size() {
        return indexReaderMap.size();
    }

    /**
     * @return the indexRootDirectory
     */
    public String getIndexRootDirectory() {
        return indexRootDirectory;
    }

    /**
     * @return the indexDirNameList
     */
    public List<String> getIndexDirNameList() {
        return indexDirNameList;
    }

}
