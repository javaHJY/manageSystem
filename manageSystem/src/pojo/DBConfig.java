package pojo;

public class DBConfig {
	private String driverName;
	private String username;  
	private String password;  
	private String url;
	
	public DBConfig() {
		super();
	}

	public DBConfig(String driverName, String username, String password, String url) {
		super();
		this.driverName = driverName;
		this.username = username;
		this.password = password;
		this.url = url;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
