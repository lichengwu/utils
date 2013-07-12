package cn.lichengwu.util.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <b>ConnectionFactory。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 数据库连接工厂，返回数据库连接。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Oliver</td><td>2008-10-18 下午05:01:51</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Oliver
 * @since 1.0
 */
public class ConnectionFactory
{
	/**
	 * 加入log日志。
	 */
	private static Log log = LogFactory.getLog(ConnectionFactory.class);

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 默认构造方法。
	 */
	public ConnectionFactory()
	{
	}

	/**
	 * <b>getConnection。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 根据数据库方言和PreparedConnection对象获得数据库连接。
	 * @param pc PreparedConnection对象
	 * @param dialect 数据库方言对象
	 * @return Connection数据库连接
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection(PreparedConnection pc,
			Dialect dialect) throws ClassNotFoundException, SQLException
	{
		if (dialect.getDatabaseType().equals(Dialect.Oracle))
		{
			return getOracleConnection(pc, dialect);
		} else if (dialect.getDatabaseType().equals(Dialect.MSSQL))
		{
			return getMSSQLConnection(pc, dialect);
		} else if (dialect.getDatabaseType().equals(Dialect.MySQL))
		{
			return getMySQLConnection(pc, dialect);
		} else
			return null;
	}

	/**
	 * <b>getConnectionByDataSource。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 根据数据源获得数据库连接。
	 * @param ds数据源
	 * @return Connection数据库连接
	 * @throws SQLException
	 */
	public static Connection getConnectionByDataSource(DataSource ds)
			throws SQLException
	{
		return ds.getConnection();
	}

	/**
	 * <b>getMySQLConnection。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获得MySQL数据库连接。
	 * @param pc PreparedConnection对象
	 * @param dialect 数据库方言对象
	 * @return Connection数据库连接
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	private static Connection getMySQLConnection(PreparedConnection pc,
			Dialect dialect) throws ClassNotFoundException, SQLException
	{
		log.debug("获得MySQL数据库连接。");
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://" + pc.getLocal() + ":" + pc.getPort() + "/"
				+ pc.getDatabaseName()
				+ "?useUnicode=true&characterEncoding=utf-8";
		return DriverManager.getConnection(url, pc.getName(), pc.getPassword());

	}

	/**
	 * <b>getOracleConnection。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获得Oracle数据库连接。
	 * @param pc PreparedConnection对象
	 * @param dialect 数据库方言对象
	 * @return Connection数据库连接
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	private static Connection getOracleConnection(PreparedConnection pc,
			Dialect dialect) throws ClassNotFoundException, SQLException
	{
		log.debug("获得oracle数据库连接。");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@" + pc.getLocal() + ":" + pc.getPort()
				+ ":" + pc.getDatabaseName();
		return DriverManager.getConnection(url, pc.getName(), pc.getPassword());
	}

	/**
	 * <b>getMSSQLConnection。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获得MS SQL Server数据库连接。
	 * @param pc PreparedConnection对象
	 * @param dialect 数据库方言对象
	 * @return Connection数据库连接
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static Connection getMSSQLConnection(PreparedConnection pc,
			Dialect dialect) throws ClassNotFoundException, SQLException
	{
		log.debug("获得MSSQL Server数据库连接。");
		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		String url = "jdbc:microsoft:sqlserver://" + pc.getLocal() + ":"
				+ pc.getPort() + ";DatabaseName=" + pc.getDatabaseName();
		return DriverManager.getConnection(url, pc.getName(), pc.getPassword());

	}

}
