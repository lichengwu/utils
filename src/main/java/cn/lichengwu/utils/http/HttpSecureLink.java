package cn.lichengwu.utils.http;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HttpSecureLink for nginx HttpSecureLinkModule
 * http://wiki.nginx.org/HttpSecureLinkModule User: lichengwu Date: 9/28/12
 * Time: 2:58 PM
 */
public class HttpSecureLink {

    private HttpSecureLink() {
    }

    /**
     * 生成带超时的加密串
     * 
     * @author lichengwu
     * @created 2012-4-16
     * 
     * @param secret
     *            公匙
     * @param uri
     *            需要加密的uri
     * @param expire
     *            超时时间
     * @return 带超时的加密串
     */
    public static String getCode(String secret, String uri, long expire) {
        try {
            MessageDigest alga = MessageDigest.getInstance("MD5");
            alga.update((secret + uri + expire).getBytes("UTF-8"));
            String code = new sun.misc.BASE64Encoder().encode(alga.digest());
            code = code.replace('+', '-');
            code = code.replace('/', '_');
            code = code.replace("=", "");
            return code;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
