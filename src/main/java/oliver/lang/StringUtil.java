package oliver.lang;

import java.io.*;

/**
 * <b>StringUtils。</b>
 * <p>
 * <b>详细说明：</b>
 * </p>
 * <!-- 在此添加详细说明 --> String工具集。
 * <p>
 * <b>修改列表：</b>
 * </p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF">
 * <td>序号</td>
 * <td>作者</td>
 * <td>修改日期</td>
 * <td>修改内容</td>
 * </tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr>
 * <td>1</td>
 * <td>Oliver</td>
 * <td>2008-10-12 下午08:17:18</td>
 * <td>建立类型</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>Oliver</td>
 * <td>2010-10-23 下午9:40:18</td>
 * <td>建立类型</td>
 * </tr>
 * 
 * </table>
 * 
 * @version 1.02
 * @author Oliver
 * @since 1.0
 */
public final class StringUtil {
    /**
     * <b>构造方法。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 无。
     */
    private StringUtil() {
    }

    /**
     * <b>检查字符串是否有内容。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 无。
     * 
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        } else
            return false;
    }

    /**
     * 判断对象是否空
     * 
     * @author lichengwu
     * @created Aug 7, 2011
     * 
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof String) {
            String str = (String) obj;
            return "".equals(str.trim());
        }
        try {
            String str = String.valueOf(obj);
            return "".equals(str.trim());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * <b>填充空字符串。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 如果源字符串不为空，则返回源字符串，否则返回填充字符串。
     * 
     * @param src
     *            源字符串
     * @param fill
     *            填充字符串
     * @return
     */
    public static String changeNull(String src, String fill) {
        if (isBlank(src)) {
            return fill;
        } else
            return src;
    }

    /**
     * <b>过滤html字符串。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 过滤html字符串，替换html中的“<”“>”“&”等。
     * 
     * @param html
     * @return
     */
    public static String encodeHTML(String html) {

        if (!isBlank(html)) {
            html = html.replaceAll("&", "&amp;");
            html = html.replaceAll("<", "&lt;");
            html = html.replaceAll(">", "&gt;");
            html = html.replaceAll(" ", "&nbsp;");
            html = html.replaceAll("'", "&#39;");
            html = html.replaceAll("\"", "&quot;");
            html = html.replaceAll("\n", "<br>");
        }
        return html;
    }

    public static String decodeHTML(String html) {

        if (!isBlank(html)) {
            html = html.replaceAll("&amp;", "&");
            html = html.replaceAll("&lt;", "<");
            html = html.replaceAll("&gt;", ">");
            html = html.replaceAll("&nbsp;", " ");
            html = html.replaceAll("&#39;", "'");
            html = html.replaceAll("&quot;", "\"");
            html = html.replaceAll("<br>", "\n");
        }
        return html;
    }

    /**
     * <b>split。</b>
     * <p>
     * <b>详细说明：字符串分隔</b>
     * </p>
     * <!-- 在此添加详细说明 --> 无。
     * 
     * @param str
     * @param pattern
     * @return
     */
    public static String[] split(String str, String pattern) {
        return str.split(pattern);
    }

    /**
     * 将输入流转换成字符串
     * 
     * @author lichengwu
     * @created 2012-1-1
     * 
     * @param is
     * 
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream is) throws IOException {
        if (is == null) {
            return null;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder str = new StringBuilder();
        String temp = br.readLine();
        while (temp != null) {
            str.append(temp).append(System.getProperty("line.separator"));
            temp = br.readLine();
        }
        return str.toString();
    }

    /**
     * 字符串转换成输入流
     * 
     * @author lichengwu
     * @created 2012-1-1
     * 
     * @param str
     * @return
     */
    public static InputStream string2InputStream(String str) throws UnsupportedEncodingException {
        Assert.notNull(str, "字符串不能为空");
        ByteArrayInputStream bai = new ByteArrayInputStream(str.getBytes("UTF-8"));
        return bai;
    }
}
