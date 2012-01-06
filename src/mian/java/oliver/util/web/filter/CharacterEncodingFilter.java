package oliver.util.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <b>CharacterEncodingFilter。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Administrator</td><td>2010-9-11 下午01:43:19</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Oliver
 * @since 1.0
 */
public class CharacterEncodingFilter implements Filter {

	protected String encoding=null;
	
	protected FilterConfig filterConfig=null;
	

	public void destroy()
	{
		encoding=null;
		filterConfig=null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		if(encoding!=null){
			request.setCharacterEncoding(encoding);
		}
		chain.doFilter(request,response);
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig=filterConfig;
		this.encoding=this.filterConfig.getInitParameter("encoding");
	}

	
}
