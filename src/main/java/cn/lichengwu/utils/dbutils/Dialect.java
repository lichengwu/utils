package cn.lichengwu.utils.dbutils;

public class Dialect
{
	public static final String Oracle="oracle";
	public static final  String MySQL="mysql";
	public static final  String MSSQL="mssql";
	private String DatabaseType;
	public Dialect(String dialectName)
	{
		DatabaseType=dialectName;
	}
	public String getDatabaseType()
	{
		return DatabaseType;
	}
	public void setDatabaseType(String databaseType)
	{
		DatabaseType = databaseType;
	}
}
