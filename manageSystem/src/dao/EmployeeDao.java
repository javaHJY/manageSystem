package dao;

import java.util.List;

import pojo.Employee;

public interface EmployeeDao {
	//存储员工信息
	boolean add(Employee emp);
	//按照id查询员工
	Employee findById(int id);
	//组合查询员工
	List<Employee> findByItem(Employee emp);
	//查找所有员工
	List<Employee> find();
	//获取分页后的员工
	List<Employee> find(Employee condition,int begin,int dataNums);
	//修改员工信息
	boolean update(Employee emp);
	//按选中行删除员工信息
	boolean delete(int id);
	//获取查询后的员工总数
	int getDataCount(Employee emp);
}
