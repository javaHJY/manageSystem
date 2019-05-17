package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pojo.Department;
import pojo.Employee;
import pojo.Project;
import pojo.Score;

public class ScoreDaoImpl implements ScoreDao {

	public int add(Connection con, PreparedStatement ps, Score sc) {
		@SuppressWarnings("unused")
		int r = 0;
		con = JDBCUtils.getConnection();
		ResultSet rs = null;
		int id = 0;
		String sql = "insert into score(e_id,p_id,value) values(?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, sc.getEmp().getId());
			ps.setInt(2, sc.getPro().getId());
			ps.setDouble(3, sc.getValue());
			r = ps.executeUpdate();
			sql = "select last_insert_id()";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return id;
	}

	public boolean update(Connection con, PreparedStatement ps, Score sc) {
		int rs = 0;
		con = JDBCUtils.getConnection();
		String sql = "update score set value=? where e_id=? and p_id=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setDouble(1, sc.getValue());
			ps.setInt(2, sc.getEmp().getId());
			ps.setInt(3, sc.getPro().getId());
			rs = ps.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return rs > 0;
	}

	public String save(Score sc) {
		Connection con = null;
		con = JDBCUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String grade = null;
		int id = 0;
		try {
			con.setAutoCommit(false);
			if (sc.getId() == 0) {
				id = add(con, ps, sc);
			} else {
				update(con, ps, sc);
			}
			String sql = "select grade from v_emp_dep_pro_score_2 where empId=" + sc.getEmp().getId() + " and proId="
					+ sc.getPro().getId();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				grade = rs.getString("grade");
			}
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, ps, con);
		}
		return id + "," + grade;
	}

	public List<Score> findByItem(Score sc) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		List<Score> searchList = new ArrayList<>();
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "select * from v_emp_dep_pro_score_2 where 1=1";
			if (!sc.getEmp().getName().equals(""))
				sql += " and empName like '%" + sc.getEmp().getName() + "%'";
			if (!sc.getEmp().getSex().equals(""))
				sql += " and sex='" + sc.getEmp().getSex() + "'";
			if (sc.getEmp().getAge() != -1)
				sql += " and age=" + sc.getEmp().getAge();
			if (sc.getEmp().getDep().getId() != null)
				sql += " and depId=" + sc.getEmp().getDep().getId();
			rs = stat.executeQuery(sql);
			if (rs == null)
				return null;
			while (rs.next()) {
				Score score = new Score();
				score.setId(rs.getInt("id"));
				Employee empNext = new Employee();
				empNext.setId(rs.getInt("empId"));
				empNext.setName(rs.getString("empName"));
				Department dep = new Department();
				dep.setName(rs.getString("depName"));
				empNext.setDep(dep);
				score.setEmp(empNext);
				Project pro = new Project();
				pro.setName(rs.getString("proName"));
				score.setPro(pro);
				score.setValue((Double)rs.getObject("value"));
				score.setGrade(rs.getString("grade"));
				searchList.add(score);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return searchList;
	}

	public List<Score> find(int begin, int end) {
		List<Score> searchList = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		try {
			con = JDBCUtils.getConnection();
			String sql = "select * from v_emp_dep_pro_score_2 limit " + begin + ", " + end;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Score score = new Score();
				score.setId(rs.getInt("id"));
				Employee emp = new Employee();
				emp.setId(rs.getInt("empId"));
				emp.setName(rs.getString("empName"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Department dep = new Department();
				dep.setId(rs.getInt("depId"));
				dep.setName(rs.getString("depName"));
				emp.setDep(dep);
				score.setEmp(emp);
				Project pro = new Project();
				pro.setId(rs.getInt("proId"));
				pro.setName(rs.getString("proName"));
				score.setPro(pro);
				score.setValue((Double)rs.getObject("value"));
				score.setGrade(rs.getString("grade"));
				searchList.add(score);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, ps, con);
		}
		return searchList;
	}

	public int getDataCount() {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "select count(*) as count from v_emp_dep_pro_score_2";
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
