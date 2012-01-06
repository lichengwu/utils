//package oliver.util.dbutils;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.ResultSetHandler;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//public class DbUtils
//{
//	/**
//	 * 加入log日志。
//	 */
//	private static Log log = LogFactory.getLog(DbUtils.class);
//
//	@SuppressWarnings("unchecked")
//	public static List<Map<String, Object>> query(DataSource ds, String sql,
//			Object params[], ResultSetHandler rs) throws SQLException
//	{
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		QueryRunner qr = new QueryRunner(ds);
//		result = (List) qr.query(sql, params, rs);
//		return result;
//	}
//
//	@SuppressWarnings("unchecked")
//	public static List<Map<String, Object>> query(Connection conn, String sql,
//			Object params[], ResultSetHandler rs) throws SQLException
//	{
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		QueryRunner qr = new QueryRunner();
//		result = (List) qr.query(conn, sql, params, rs);
//		return result;
//	}
//
//	public boolean queryNull(Connection conn, String sql, Object params[])
//			throws SQLException
//	{
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		if (params != null)
//		{
//			if (params.length > 0)
//			{
//				for (int i = 1; i <= params.length; i++)
//				{
//					pstmt.setObject(i, params[i]);
//				}
//			}
//		}
//		ResultSet rs = pstmt.executeQuery();
//		if (rs.next())
//		{
//			rs.close();
//			return true;
//		}
//		rs.close();
//		return false;
//
//	}
//
//	public static int update(Connection conn,String sql,Object params[]) throws SQLException
//	{
//		QueryRunner qr = new QueryRunner();
//		return qr.update(conn,sql,params);
//	}
//	public static int update(DataSource ds,String sql,Object params[]) throws SQLException
//	{
//		QueryRunner qr = new QueryRunner(ds);
//		return qr.update(sql,params);
//	}
//	public static void delete(DataSource ds,String sql,Object [] params) throws SQLException
//	{
//		QueryRunner qr = new QueryRunner(ds);
//		qr.update(sql,params);
//	}
//	public static void delete(Connection conn,String sql,Object [] params) throws SQLException
//	{
//		QueryRunner qr = new QueryRunner();
//		qr.update(conn,sql,params);
//	}
//	public static void close(Connection conn) 
//	{
//		try
//		{
//			if (conn != null)
//			{
//				if (!conn.isClosed())
//				{
//					log.debug("关闭数据库连接");
//					conn.close();
//				}
//			}
//		} catch (SQLException e)
//		{
//			log.error(e);
//		}
//	}
//	public static void close(ResultSet rs)
//	{
//		if(rs!=null)
//		{
//			try
//			{
//				
//					log.debug("关闭ResultSet");
//					rs.close();
//				
//			} catch (SQLException e)
//			{
//				log.error(e);
//			}
//		}
//	}
//	public static void close(PreparedStatement pstmt)
//	{
//		if(pstmt!=null)
//		{
//			try
//			{
//				{
//					log.debug("关闭PreparedStatement");
//					pstmt.close();
//				}
//			} catch (SQLException e)
//			{
//				log.error(e);
//			}
//		}
//	}
//}
