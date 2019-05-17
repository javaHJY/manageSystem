package web.listener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dao.JDBCUtils;
import pojo.DBConfig;

public class JDBCListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Integer count=0;
		sce.getServletContext().setAttribute("online_session_count", count);
		try {
			Properties prop = new Properties();
			InputStream is = JDBCListener.class.getClassLoader().getResourceAsStream("jdbcConfig.properties");
			prop.load(is);
			String driverName = prop.getProperty("driverName");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			String url = prop.getProperty("url");
			DBConfig db = new DBConfig(driverName, username, password, url);
			JDBCUtils.init(db);
			System.out.println("ServletContext已创建");
		} catch (FileNotFoundException e) {
			System.out.println("配置文件未找到");
		} catch (IOException e) {
			System.out.println("io连接失败");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ServletContext已销毁");
		JDBCUtils.closeConnection();
	}

}
