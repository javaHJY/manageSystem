package dao;

import pojo.User;

public interface UserDao {
	//用户名查找用户
	User search(String username);
	//添加用户
	boolean add(User user);
}
