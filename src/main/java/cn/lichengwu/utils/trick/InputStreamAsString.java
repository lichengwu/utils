package cn.lichengwu.utils.trick;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * 最简单的方式把一个InputStream转换成String
 *
 * @author lichengwu
 * @version 1.0
 * @created 2013-12-28 10:37 AM
 */
public class InputStreamAsString {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = new URL("http://blog.lichengwu.cn/atom.xml").openStream();
        String str = new Scanner(inputStream).useDelimiter("\\A").next();
        System.out.println(str);
    }

}
