package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import pojo.Score;

public interface ScoreDao {
	//添加绩效
	int add(Connection con,PreparedStatement ps,Score sc);
	//按照绩效信息查询绩效
	List<Score> findByItem(Score sc);
	//修改绩效
	boolean update(Connection con,PreparedStatement ps,Score sc);
}
