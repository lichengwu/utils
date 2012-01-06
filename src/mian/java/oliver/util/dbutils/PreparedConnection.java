package oliver.util.dbutils;

public class PreparedConnection
{
	private String name;
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getLocal()
	{
		return local;
	}

	public void setLocal(String local)
	{
		this.local = local;
	}

	public String getDatabaseName()
	{
		return databaseName;
	}

	public void setDatabaseName(String databaseName)
	{
		this.databaseName = databaseName;
	}

	private String password;
	private int port;
	private String local;
	private String databaseName;
	
	public PreparedConnection(String name,String password,int port,String local,String databaseName)
	{
		this.name=name;
		this.password=password;
		this.port=port;
		this.local=local;
		this.databaseName=databaseName;
	}
}
