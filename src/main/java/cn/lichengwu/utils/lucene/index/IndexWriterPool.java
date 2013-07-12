/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.utils.lucene.index;

import cn.lichengwu.utils.lucene.util.LuceneUtil;
import cn.lichengwu.utils.date.DateUtil;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IndexWriter线程池
 * 
 * @author lichengwu
 * @created 2011-10-26
 * 
 * @version 1.0
 */
public class IndexWriterPool {

    private static final Logger logger = LoggerFactory.getLogger(IndexWriterPool.class);

    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认索引优化后文件段的数量
     */
    @SuppressWarnings("unused")
    private static final int DEFAULT_MAX_NUM_SEGMENTS = 2;

    /**
     * 索引优化后文件段的最大数量
     */
    private Integer maxNumSegments = null;

    /**
     * 索引跟路径
     */
    private String indexRootDirectory = null;

    /**
     * 存放IndexReader
     */
    private Map<String, IndexWriter> indexWriterMap = new ConcurrentHashMap<String, IndexWriter>();

    /**
     * 索引目录名字
     */
    private List<String> indexDirNameList = null;

    /**
     * 初始化
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     */
    @PostConstruct
    protected void init() {
        logger.info("begin initialize IndexWriterPool");

        // indexRootDirectory =
        // ConfigUtil.getValueByKey("lucene.pool.index.path");
        // indexDirNameList =
        // ConfigUtil.getConfigValueListByKey("lucene.pool.index.dirs", ",");
        //
        // if(indexRootDirectory==null){
        // throw new
        // IllegalAddException("no index root directory found for path:"+indexRootDirectory);
        // }
        // if(indexDirNameList==null || indexDirNameList.size()<1){
        // throw new
        // IllegalAddException("no index directory found for path:"+indexRootDirectory);
        // }
        // maxNumSegments =
        // ConfigUtil.getValue2IntByKey("lucene.pool.index.maxNumSegments");
        //
        // synchronized (indexWriterMap) {
        // if (maxNumSegments == null || maxNumSegments < 1) {
        // maxNumSegments = DEFAULT_MAX_NUM_SEGMENTS;
        // }
        // for (String indexDirName : indexDirNameList) {
        // indexWriterMap.put(indexDirName, createIndexWriter(indexDirName));
        // }
        // }
        logger.info("IndexWriterPool initialization completed");
    }

    /**
     * <pre>
     * 创建IndexWriter
     * 这个方法不允许多个线程同时调用。
     * </pre>
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     * @param indexDirName
     * @return
     */
    private IndexWriter createIndexWriter(String indexDirName) {
        try {
            File indexPath = LuceneUtil.getFilePath(indexRootDirectory, indexDirName);
            IndexWriterConfig conf = new IndexWriterConfig(LuceneUtil.CURRENT_VERSION,
                    new StandardAnalyzer(LuceneUtil.CURRENT_VERSION));
            conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
            return new IndexWriter(FSDirectory.open(indexPath), conf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得IndexWriter
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     * @param indexDirName
     * @return
     */
    public synchronized IndexWriter getIndexWriter(String indexDirName) {
        if (!indexWriterMap.containsKey(indexDirName)) {
            indexWriterMap.put(indexDirName, createIndexWriter(indexDirName));
        }
        return indexWriterMap.get(indexDirName);
    }

    /**
     * 提交索引
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     */
    public void commit() {
        logger.info("begin to commit all IndexWiters of pool,the pool size:{}", size());
        synchronized (indexWriterMap) {
            Iterator<Entry<String, IndexWriter>> iterator = indexWriterMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, IndexWriter> entry = iterator.next();
                IndexWriter indexWriter = entry.getValue();
                try {
                    indexWriter.commit();
                } catch (Exception e) {
                    logger.error(
                            "exception while commiting pending updates,indexDir:{},exception:{}",
                            entry.getKey(), e.getMessage());
                    try {
                        indexWriter.close();
                        iterator.remove();
                    } catch (CorruptIndexException ex) {
                        logger.error(
                                "CorruptIndexException while closing indexWriter,the root cause:{}",
                                ex.getMessage());
                    } catch (IOException ex) {
                        logger.error("IOException while closing indexWriter,the root cause:{}",
                                ex.getMessage());
                    }
                }
            }
        }
        logger.info("{} IndexWiters had committed pending updates", size());
    }

    /**
     * <pre>
     * 重新加载所有的IndexWriter。
     * IndexWriter不会及时释放哪些在创建索引过程中产生的索引文件碎片，哪怕哪些索引文件已经消失。 
     * {@link #reload()}就是为了释放那些文件句柄，防止进程持有过多的文件句柄。
     * </pre>
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     */
    public void reload() {
        logger.info("begin to reload IndexWriterPool at {}",
                DateUtil.date2String(new Date(), DATE_FORMATTER));
        // 需要重新加载的索引目录列表
        List<String> indexDirNameList = new ArrayList<String>();
        synchronized (indexWriterMap) {
            Iterator<Entry<String, IndexWriter>> iterator = indexWriterMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, IndexWriter> entry = iterator.next();
                indexDirNameList.add(entry.getKey());
                IndexWriter indexWriter = entry.getValue();
                try {
                    indexWriter.commit();
                } catch (Exception e) {
                    logger.error("Exception while commiting {},the root cause:{}", entry.getKey(),
                            e.getMessage());
                } finally {
                    try {
                        indexWriter.close();
                        iterator.remove();
                    } catch (CorruptIndexException ex) {
                        logger.error(
                                "CorruptIndexException while closing indexWriter,the root cause:{}",
                                ex.getMessage());
                    } catch (IOException ex) {
                        logger.error("IOException while closing indexWriter,the root cause:{}",
                                ex.getMessage());
                    }
                }
            }

            for (String indexDirName : indexDirNameList) {
                indexWriterMap.put(indexDirName, createIndexWriter(indexDirName));
            }
        }
        logger.info("IndexWriterPool reload end at {}",
                DateUtil.date2String(new Date(), DATE_FORMATTER));
    }

    /**
     * 销毁{@link IndexWriterPool}，释放持有的资源。 程序关闭或退出时调用。
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     */
    public void destroy() {
        synchronized (indexWriterMap) {
            Iterator<Entry<String, IndexWriter>> iterator = indexWriterMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, IndexWriter> entry = iterator.next();
                IndexWriter indexWriter = entry.getValue();
                try {
                    indexWriter.commit();
                    indexWriter.close();
                } catch (Exception e) {
                    logger.error("Exception while closing {},the root cause:{}", entry.getKey(),
                            e.getMessage());
                    try {
                        indexWriter.close();
                        iterator.remove();
                    } catch (CorruptIndexException ex) {
                        logger.error(
                                "CorruptIndexException while closing indexWriter,the root cause:{}",
                                ex.getMessage());
                    } catch (IOException ex) {
                        logger.error("IOException while closing indexWriter,the root cause:{}",
                                ex.getMessage());
                    }
                }
            }
            logger.info("IndexWriterPool destoryed");
        }
        indexWriterMap = null;
    }

    /**
     * 优化索引，提升检索速度。
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     */
    public void optimize() {
        logger.info("begin to optimize at {}", DateUtil.date2String(new Date(), DATE_FORMATTER));
        synchronized (indexWriterMap) {
            Iterator<Entry<String, IndexWriter>> iterator = indexWriterMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, IndexWriter> entry = iterator.next();
                IndexWriter indexWriter = entry.getValue();
                try {
                    indexWriter.optimize(maxNumSegments);
                    indexWriter.commit();
                } catch (Exception e) {
                    logger.error("Exception while optimizing {},the root cause:{}", entry.getKey(),
                            e.getMessage());
                    try {
                        indexWriter.close();
                        iterator.remove();
                    } catch (CorruptIndexException ex) {
                        logger.error(
                                "CorruptIndexException while closing indexWriter,the root cause:{}",
                                ex.getMessage());
                    } catch (IOException ex) {
                        logger.error("IOException while closing indexWriter,the root cause:{}",
                                ex.getMessage());
                    }
                }
            }
        }
        logger.info("end optimize at {}", DateUtil.date2String(new Date(), DATE_FORMATTER));
    }

    /**
     * 当前线程池中IndexWriter数量
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     * @return
     */
    public int size() {
        return indexWriterMap.size();
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

    /**
     * @return the maxNumSegments
     */
    public Integer getMaxNumSegments() {
        return maxNumSegments;
    }

}
