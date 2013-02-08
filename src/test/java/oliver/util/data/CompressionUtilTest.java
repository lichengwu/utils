package oliver.util.data;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Test;

/**
 * CompressionUtilTest
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-02-07 10:37 AM
 */
public class CompressionUtilTest {
    @Test
    public void testCompress() throws Exception {

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                "/Users/lichengwu/tmp/out_put.txt.bak"));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();

        byte[] data = out.toByteArray();

        byte[] output = CompressionUtil.compress(data, CompressionUtil.Level.BEST_COMPRESSION);
        System.out.println("before : " + (data.length / 1024) + "k");
        System.out.println("after : " + (output.length / 1024) + "k");

        FileOutputStream fos = new FileOutputStream("/Users/lichengwu/tmp/out_put.txt.bak.compress");
        fos.write(output);
        out.close();
        fos.close();

    }

    @Test
    public void testDecompress() throws Exception {

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                "/Users/lichengwu/tmp/out_put.txt.bak.compress"));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();

        byte[] data = out.toByteArray();

        byte[] output = CompressionUtil.decompress(data);
        System.out.println("before : " + (data.length / 1024) + "k");
        System.out.println("after : " + (output.length / 1024) + "k");

        FileOutputStream fos = new FileOutputStream("/Users/lichengwu/tmp/out_put.txt.bak.decompress");
        fos.write(output);
        out.close();
        fos.close();


    }
}
