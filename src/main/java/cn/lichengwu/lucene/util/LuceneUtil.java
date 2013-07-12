/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.lucene.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.util.Version;

/**
 * lucene 工具类
 * 
 * @author lichengwu
 * @created 2011-10-17
 * 
 * @version 1.0
 */
final public class LuceneUtil {

    private LuceneUtil() {
    }

    /**
     * lucene版本
     */
    public static final Version CURRENT_VERSION = Version.LUCENE_34;

    /**
     * <pre>
     * 根据根路径和文件夹名字获得文件路径 
     * 如果不存在路径，则创建路径并返回
     * </pre>
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     * @param root
     * @param dir
     * @return
     */
    public static File getFilePath(String root, String dir) throws IOException {
        File file = new File(root, dir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new IOException("can not create file : " + file.getCanonicalPath());
            }
        }
        return file;
    }

    /**
     * <pre>
     * 保证文件路径存在
     * 如果不存在，则创建路径
     * </pre>
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     * @param path
     * @return
     */
    public static boolean ensurePath(File path) {
        if (!path.exists()) {
            return path.mkdirs();
        }
        if (path.isFile()) {
            return false;
        }
        return true;
    }

    /**
     * 目录是否为索引目录
     * 
     * @author lichengwu
     * @created 2011-10-26
     * 
     * @param path
     * @return
     */
    public static boolean isIndexDir(File path) {
        if (!path.exists() || path.isFile()) {
            return false;
        }
        File[] segments = path.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(IndexFileNames.SEGMENTS)
                        || name.equals(IndexFileNames.SEGMENTS_GEN);
            }
        });
        return segments != null && segments.length > 1;
    }
}
