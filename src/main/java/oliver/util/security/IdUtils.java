package oliver.util.security;

import java.util.Random;

/**
 * <b>IdUtils。</b>
 * <p>
 * <b>详细说明：Id工具类，对数字和字符串的id进行编码和解码</b>
 * </p>
 * <!-- 在此添加详细说明 --> 无。
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
 * <td>2010-10-24 上午10:21:38</td>
 * <td>建立类型</td>
 * </tr>
 * 
 * </table>
 * 
 * @version 1.0
 * @author lichengwu
 * @since 1.0
 */
public class IdUtils {
	/**
	 * <b>encode。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 无。
	 * 
	 * @param id
	 * @param length
	 *            生成加密数字的长度
	 * @return 指定长度加密数字
	 * @throws EncodeLengthTooShortException
	 */
	public static Integer encode(Integer id, int length) throws Exception {
		String idStr = Integer.toOctalString(id);
		if (idStr.length() > length) {
			throw new Exception("生成加密数字的长度太短");
		} else if (idStr.length() == length) {
			return Integer.parseInt(idStr);
		} else {
			int suffixLength = length - idStr.length() - 1;
			String suffix = "";
			if (suffixLength > 0) {
				suffix = ""
						+ new Random()
								.nextInt((int) Math.pow(10, suffixLength));
				while (suffix.length() < suffixLength) {
					suffix = "0" + suffix;
				}
			}
			return Integer.parseInt(idStr + "9" + suffix);
		}
	}

	/**
	 * 解码
	 * 
	 * @author lichengwu
	 * @created 2011-12-10
	 * 
	 * @param id
	 * @return
	 */
	public static Integer decode(Integer id) throws Exception {
		String str = String.valueOf(id);
		String idStr = null;
		try {
			idStr = str.substring(0, str.indexOf("9"));
		} catch (Exception e) {
			throw new Exception("id格式错误");
		}
		return Integer.valueOf(idStr, 8);
	}

}
