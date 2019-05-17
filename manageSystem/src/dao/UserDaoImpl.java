package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pojo.User;

public class UserDaoImpl implements UserDao {

	@Override
	public User search(String username) {
		Connection con=null;
		Statement stat=null;
		ResultSet rs=null;
		User user=null;
		try {
			con=JDBCUtils.getConnection();
			stat=con.createStatement();
			String sql="select * from user where username='"+username+"'";
			rs=stat.executeQuery(sql);
			if(rs.next()) {
				user=new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
		return user;
	}

	@Override
	public boolean add(User user) {
		Connection con=null;
		Statement stat=null;
		int rs=0;
		try {
			con=JDBCUtils.getConnection();
			stat=con.createStatement();
			String sql="insert into user(username,password) values('"+user.getUsername()+"','"+user.getPassword()+"')";
			rs=stat.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
		return rs>0;
	}

}
