package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pojo.DBConfig;

public class JDBCUtils {
	private static Connection con = null;
	private static String driverName = "";
	private static String username = "";
	private static String password = "";
	private static String url = "";

	public static void init(DBConfig db) {
		JDBCUtils.driverName = db.getDriverName();
		JDBCUtils.username = db.getUsername();
		JDBCUtils.password = db.getPassword();
		JDBCUtils.url = db.getUrl();
	}

	public static Connection getConnection() {
		// 2.加载驱动
		try {
			Class.forName(JDBCUtils.driverName);
			JDBCUtils.con = DriverManager.getConnection(JDBCUtils.url, JDBCUtils.username, JDBCUtils.password);
			return JDBCUtils.con;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static void closeConnection() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeAll(ResultSet rs, Statement stat, Connection con) {
		try {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
}
