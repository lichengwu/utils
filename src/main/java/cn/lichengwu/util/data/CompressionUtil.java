package cn.lichengwu.util.data;

import java.io.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import cn.lichengwu.lang.Assert;
import cn.lichengwu.lang.Closer;

/**
 * util for compress/decompress data
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-02-07 10:14 AM
 */
public final class CompressionUtil {

    private static final int BUFFER_SIZE = 4 * 1024;

    /**
     * compress data by {@linkplain Level}
     *
     * @param data
     * @param level see {@link Level}
     * @return
     * @throws IOException
     * @author lichengwu
     * @created 2013-02-07
     */
    public static byte[] compress(byte[] data, Level level) throws IOException {

        Assert.notNull(data);
        Assert.notNull(level);

        Deflater deflater = new Deflater();
        // set compression level
        deflater.setLevel(level.getLevel());
        deflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[BUFFER_SIZE];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated
            // code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

    /**
     * compress {@linkplain Object}
     *
     * @param data
     * @param level
     * @return
     * @throws IOException
     */
    public static byte[] compress(Object data, Level level) throws IOException {
        byte[] result = new byte[0];
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream(BUFFER_SIZE);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            oos.flush();
            result = bos.toByteArray();


        } finally {
            Closer.close(bos, oos);
        }

        return compress(result, level);

    }

    /**
     * decompress data
     *
     * @param data
     * @return
     * @throws IOException
     * @throws DataFormatException
     * @author lichengwu
     * @created 2013-02-07
     */
    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {

        Assert.notNull(data);

        if (data.length == 0) {
            return data;
        }

        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[BUFFER_SIZE];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();

        return output;
    }

    /**
     * decompress byte[] to Object
     *
     * @param data
     * @return
     * @throws IOException
     * @throws DataFormatException
     * @throws ClassNotFoundException
     */
    public static Object decompress2Object(byte[] data) throws IOException, DataFormatException, ClassNotFoundException {
        Assert.notNull(data);
        byte[] result = decompress(data);
        Object object = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(result);
            ois = new ObjectInputStream(bis);
            object = ois.readObject();
        } finally {
            Closer.close(bis, ois);
        }
        return object;
    }

    /**
     * Compression level
     */
    public static enum Level {

        /**
         * Compression level for no compression.
         */
        NO_COMPRESSION(0),

        /**
         * Compression level for fastest compression.
         */
        BEST_SPEED(1),

        /**
         * Compression level for best compression.
         */
        BEST_COMPRESSION(9),

        /**
         * Default compression level.
         */
        DEFAULT_COMPRESSION(-1);

        private int level;

        Level(

                int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

    }
}
