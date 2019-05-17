package dao;

import java.util.List;

import pojo.Department;

public interface DepartmentDao {
	//添加部门
	boolean add(Department dep);
	//按id删除部门
	boolean delete(int id);
	//修改部门
	boolean update(Department dep);
	//按id查询部门
	Department findById(int id);
	//查询所有部门
	List<Department> find();
	List<Department> find(Department condition, int begin, int dataNums);
	int getDataCount(Department dep);
}
