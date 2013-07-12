package cn.util.data;

import java.io.*;

import cn.lichengwu.util.data.CompressionUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * CompressionUtilTest
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-02-07 10:37 AM
 */
public class CompressionUtilTest {

    private static final int SIZE = 100000;

    protected File origin;

    private File cz;


    @Before
    public void setUp() throws IOException {
        origin = File.createTempFile("compress", "origin");
        cz = File.createTempFile("compress", "cz");
    }

    @Test
    public void testCompress() throws Exception {
        prepare(origin);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(origin));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

        byte[] temp = new byte[1024];
        int size;
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();

        byte[] data = out.toByteArray();

        byte[] output = CompressionUtil.compress(data, CompressionUtil.Level.BEST_COMPRESSION);
        System.out.println("before : " + (data.length / 1024) + "k");
        System.out.println("after : " + (output.length / 1024) + "k");

        FileOutputStream fos = new FileOutputStream(cz);
        fos.write(output);
        out.close();
        fos.close();

    }

    @Test
    public void testDecompress() throws Exception {

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(cz));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

        byte[] temp = new byte[1024];
        int size;
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();

        byte[] data = out.toByteArray();

        byte[] output = CompressionUtil.decompress(data);
        System.out.println("before : " + (data.length / 1024) + "k");
        System.out.println("after : " + (output.length / 1024) + "k");

        FileOutputStream fos = new FileOutputStream(origin);
        fos.write(output);
        out.close();
        fos.close();
    }

    private void prepare(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < SIZE; i++) {
            writer.write("this is test text...." + i);
        }
        writer.close();
    }
}
