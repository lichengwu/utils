/**
 * Formatter.java 2008-10-12
 * 
 * 版权所有(c) 2008 。
 */
package cn.lichengwu.utils.lang;

/**
 * <b>Formatter。</b>
 * <p>
 * <b>详细说明：</b>
 * </p>
 * <!-- 在此添加详细说明 --> String格式化工具集。
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
 * <td>2008-10-12 下午07:19:24</td>
 * <td>建立类型</td>
 * </tr>
 * 
 * </table>
 * 
 * @version 1.0
 * @author Oliver
 * @since 1.0
 */
public final class Formatter {
    /**
     * <b>构造方法。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 无。
     */
    private Formatter() {
    }

    /**
     * <b>forShortFormat。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 把长字符串格"abcdef"格式化成形如"abc..."的字符串。
     * 
     * @param src
     *            - 源字符串
     * @param length
     *            - 格式化后长度
     * @param suffixLength
     *            - 格式化后缀长度
     * @param suffix
     *            - 省略后缀
     * @return format 字符串
     */
    public static String forShortFormat(String src, int length, int suffixLength, char suffix) {
        if (src == null || src.length() < 2 || (length < suffixLength)) {
            return src;
        }
        StringBuilder value = new StringBuilder(src.substring(0, (length - suffixLength)));
        for (byte i = 0; i < suffixLength; i++) {
            value.append(suffix);
        }
        return src;
    }
}
