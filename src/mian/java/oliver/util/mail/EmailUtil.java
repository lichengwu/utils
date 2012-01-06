package oliver.util.mail;

import java.util.List;

import oliver.util.string.StringUtil;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

/**
 * <b>EmailUtil。</b>
 * <p><b>详细说明：邮件工具箱</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>oliver</td><td>2010-12-26 上午11:07:23</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author oliver
 * @since 1.0
 */
public class EmailUtil
{
	private static final Logger logger = Logger.getLogger(EmailUtil.class);
	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：默认私有构造方法</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 多例工厂模式。
	 */
	private EmailUtil() {
	}

	/**
	 * <b>newInstance。</b>  
	 * <p><b>详细说明：获得一个Email实例</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 每次调用返回一个新的实例，多线程安全。
	 * @param sendTo
	 * @param title
	 * @param content
	 * @return
	 * @throws EmailException
	 */
	public static  Email newInstance(List<String> sendTo, String title,
			String content) throws EmailException {
		//参数检验，放在最前面，一点出现错误，避免创建不必要的对象。
		if (sendTo == null || sendTo.size() <= 0)
			throw new EmailException("No send to email address");

		HtmlEmail email = new HtmlEmail();
		//设置SMTP
		email.setHostName("smtp.gmail.com");
		//设置端口
		email.setSmtpPort(587);
		//授权
		email.setAuthenticator(new DefaultAuthenticator(
				"aaaa@gmail.com", "password"));
		email.setTLS(true);
		email.setDebug(false);
		//发信人
		email.setFrom("xxxx@gmail.com");
		//设置编码，防止乱码
		email.setCharset("utf-8");
		//邮件标题
		email.setSubject(title);
		//邮件正文
		email.setHtmlMsg(content);
		//收信人
		email.addTo("ol_l@msn.com");
		for (String address : sendTo) {
			if (!StringUtil.isBlank(address))
				//email.addTo(address);
				email.addBcc(address);
		}
		logger.info("===>send email[title:"+title+",body:"+content+",members:"+sendTo.toString()+"]");
		return email;
	}
}
