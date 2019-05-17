package dao;

import java.util.List;

import pojo.Project;

public interface ProjectDao {
	//添加项目
	boolean add(Project pro);
	//按照id查询项目
	Project findById(int id);
	//按照项目信息查询项目
	List<Project> findByItem(Project pro);
	//查询所有项目
	List<Project> find();
	//修改项目
	boolean update(Project pro);
	//删除项目
	boolean delete(int id);
}
