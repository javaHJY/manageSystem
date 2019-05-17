package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pojo.Department;

public class DepartmentDaoImpl implements DepartmentDao {

	public boolean add(Department dep) {
		int rs = 0;
		Connection con = null;
		Statement stat = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "insert into department(name) values('" + dep.getName() + "');";
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
			con.setAutoCommit(false);
			stat = con.createStatement();
			String sql = "delete from department where id=" + id;
			rs = stat.executeUpdate(sql);
			sql = "update employee set d_id=null where d_id=" + id;
			rs = stat.executeUpdate(sql);
			con.commit();
		} catch (SQLException e) {
			try {
				if (con != null)
					con.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(null, stat, con);
		}
		return rs > 0;
	}

	public boolean update(Department dep) {
		int rs = 0;
		Connection con = null;
		Statement stat = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "update department set name='" + dep.getName() + "' where id=" + dep.getId();
			rs = stat.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(null, stat, con);
		}
		return rs > 0;
	}

	@Override
	public Department findById(int id) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Department dep = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "select * from department where id=" + id;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				dep = new Department();
				dep.setId(id);
				dep.setName(rs.getString("name"));
				dep.setEmpNums(rs.getInt("empNums"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return dep;
	}

	public List<Department> find(Department condition, int begin, int dataNums) {
		List<Department> list = new ArrayList<>();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String where = " where 1=1";
			if (null != condition.getName()&&!"-1".equals(condition.getName())&&!"".equals(condition.getName())) {
				where += " and name like '%" + condition.getName() + "%'";
			}
			if (null != condition.getEmpNums()) {
				where += " and empNums=" + condition.getEmpNums();
			}
			String sql = "select * from department" + where + " limit " + begin + "," + dataNums;
			rs = stat.executeQuery(sql);
			if (rs == null)
				return null;
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpNums(rs.getInt("empNums"));
				list.add(dep);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return list;
	}

	@Override
	public List<Department> find() {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		List<Department> searchList = new ArrayList<>();
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "select * from department";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpNums(rs.getInt("empNums"));
				searchList.add(dep);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return searchList;
	}

	public int getDataCount(Department dep) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String where = " where 1=1";
			if (null != dep.getName()&&!"-1".equals(dep.getName())) {
				where += " and name like '%" + dep.getName() + "%'";
			}
			if (null != dep.getEmpNums()) {
				where += " and empNums=" + dep.getEmpNums();
			}
			String sql = "select count(*) as count from department" + where;
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
