package dao;

import java.util.List;

import pojo.Project;

public interface DepManageProDao {
	//添加项目
	boolean add(int depId,int proId);
	//按照id查询项目
	List<Project> findById(Integer id);
	//查询部门没有的项目
	List<Project> findByNoDep(Integer id);
	//删除项目
	boolean delete(int depId,int proId);
}
