package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pojo.Department;
import pojo.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

	public boolean add(Employee emp) {
		int rs = 0;
		Connection con = null;
		Statement stat = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "insert into employee (name,sex,age,d_id,avatar) values('" + emp.getName() + "','"
					+ emp.getSex() + "'," + emp.getAge() + "," + emp.getDep().getId() + ",'" + emp.getAvatar() + "');";
			rs = stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(null, stat, con);
		}
		return rs > 0;
	}

	public boolean delete(int id) {
		int rs = 0;
		Connection con = null;
		Statement stat = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "delete from employee where id=" + id;
			rs = stat.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(null, stat, con);
		}
		return rs > 0;
	}

	public boolean update(Employee emp) {
		int rs = 0;
		Connection con = null;
		Statement stat = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "update employee set name='" + emp.getName() + "',sex='" + emp.getSex() + "',age="
					+ emp.getAge() + ",d_id=" + emp.getDep().getId() + ",avatar='" + emp.getAvatar() + "' where id="
					+ emp.getId();
			rs = stat.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(null, stat, con);
		}
		return rs > 0;
	}

	public List<Employee> findByItem(Employee emp) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		List<Employee> searchList = new ArrayList<>();
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "select e.id,e.name,e.sex,e.age,e.d_id,d.name from employee as e"
					+ " left join department as d on e.d_id=d.id where 1=1";
			if (!emp.getName().equals(""))
				sql += " and e.name like '%" + emp.getName() + "%'";
			if (!"-1".equals(emp.getSex()) && !emp.getSex().equals(""))
				sql += " and e.sex='" + emp.getSex() + "'";
			if (emp.getAge() != -1)
				sql += " and e.age=" + emp.getAge();
			if (emp.getDep().getId() != null)
				sql += " and e.d_id=" + emp.getDep().getId();
			rs = stat.executeQuery(sql);
			if (rs == null)
				return null;
			while (rs.next()) {
				Employee empNext = new Employee();
				empNext.setId(rs.getInt("e.id"));
				empNext.setName(rs.getString("e.name"));
				empNext.setSex(rs.getString("e.sex"));
				empNext.setAge(rs.getInt("e.age"));
				empNext.setD_id(rs.getInt("e.d_id"));
				Department dep = new Department();
				dep.setId(rs.getInt("e.d_id"));
				dep.setName(rs.getString("d.name"));
				empNext.setDep(dep);
				searchList.add(empNext);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return searchList;
	}

	public Employee findById(int id) {
		Employee emp = new Employee();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "select e.id,e.name,e.sex,e.age,e.d_id,e.avatar,d.name from employee as e"
					+ " left join department as d on e.d_id=d.id" + " where e.id=" + id;
			rs = stat.executeQuery(sql);
			if (rs == null)
				return null;
			while (rs.next()) {
				emp.setId(rs.getInt("e.id"));
				emp.setName(rs.getString("e.name"));
				emp.setSex(rs.getString("e.sex"));
				emp.setAge(rs.getInt("e.age"));
				emp.setD_id(rs.getInt("e.d_id"));
				Department dep = new Department();
				dep.setId(rs.getInt("e.d_id"));
				dep.setName(rs.getString("d.name"));
				emp.setDep(dep);
				emp.setAvatar(rs.getString("e.avatar"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return emp;
	}

	public List<Employee> find(Employee condition, int begin, int dataNums) {
		List<Employee> list = new ArrayList<>();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String where = " where 1=1";
			if (!"".equals(condition.getName()) && null != condition.getName()) {
				where += " and e.name like '%" + condition.getName() + "%'";
			}
			if (!"-1".equals(condition.getSex()) && !"".equals(condition.getSex()) && null != condition.getSex()) {
				where += " and e.sex='" + condition.getSex() + "'";
			}
			if (null != condition.getAge()) {
				where += " and e.age=" + condition.getAge();
			}
			if (-1 != condition.getDep().getId()) {
				where += " and e.d_id=" + condition.getDep().getId();
			}
			String sql = "select e.id,e.name,e.sex,e.age,e.d_id,e.avatar,d.name from employee as e"
					+ " left join department as d on e.d_id=d.id" + where + " limit " + begin + "," + dataNums;
			rs = stat.executeQuery(sql);
			if (rs == null)
				return null;
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("e.id"));
				emp.setName(rs.getString("e.name"));
				emp.setSex(rs.getString("e.sex"));
				emp.setAge(rs.getInt("e.age"));
				emp.setAvatar(rs.getString("avatar"));
				emp.setD_id(rs.getInt("e.d_id"));
				Department dep = new Department();
				dep.setId(rs.getInt("e.d_id"));
				dep.setName(rs.getString("d.name"));
				emp.setDep(dep);
				list.add(emp);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return list;
	}

	@Override
	public List<Employee> find() {
		List<Employee> list = new ArrayList<>();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "select e.id,e.name,e.sex,e.age,e.d_id,d.name from employee as e"
					+ " left join department as d on e.d_id=d.id;";
			rs = stat.executeQuery(sql);
			if (rs == null)
				return null;
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("e.id"));
				emp.setName(rs.getString("e.name"));
				emp.setSex(rs.getString("e.sex"));
				emp.setAge(rs.getInt("e.age"));
				emp.setD_id(rs.getInt("e.d_id"));
				Department dep = new Department();
				dep.setId(rs.getInt("e.d_id"));
				dep.setName(rs.getString("d.name"));
				emp.setDep(dep);
				list.add(emp);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return list;
	}

	public int getDataCount(Employee emp) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String where = " where 1=1";
			if (!"".equals(emp.getName()) && null != emp.getName()) {
				where += " and e.name like '%" + emp.getName() + "%'";
			}
			if (!"-1".equals(emp.getSex()) && !"".equals(emp.getSex()) && null != emp.getSex()) {
				where += " and e.sex='" + emp.getSex() + "'";
			}
			if (null != emp.getAge()) {
				where += " and e.age=" + emp.getAge();
			}
			if (-1 != emp.getDep().getId()) {
				where += " and e.d_id=" + emp.getDep().getId();
			}
			String sql = "select count(*) as count from employee as e left join department as d on e.d_id=d.id" + where;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return count;
	}

}
