package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pojo.Project;

public class DepManageProDaoImpl implements DepManageProDao {

	@Override
	public boolean add(int depId,int proId) {
		int rs = 0;
		Connection con = null;
		Statement stat = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "insert into m_dep_pro(d_id,p_id) values(" + depId + "," + proId + ")";
			rs = stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(null, stat, con);
		}
		return rs > 0;
	}

	public boolean add(int depId, String[] noProIds) {
		boolean flag=false;
		for (String noProId : noProIds) {
			flag=add(depId, Integer.parseInt(noProId));
		}
		return flag;
	}

	@Override
	public List<Project> findById(Integer id) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		List<Project> searchList = new ArrayList<>();
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "select * from v_dep_pro where depId=" + id;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("proId"));
				pro.setName(rs.getString("proName"));
				searchList.add(pro);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return searchList;
	}

	@Override
	public List<Project> findByNoDep(Integer id) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		List<Project> searchList = new ArrayList<>();
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "select * from project where id not in" + "(select proId from v_dep_pro where depId=" + id
					+ ")";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));
				searchList.add(pro);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return searchList;
	}

	@Override
	public boolean delete(int depId,int proId) {
		int rs = 0;
		Connection con = null;
		Statement stat = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql = "delete from m_dep_pro where d_id=" + depId + " and p_id=" + proId;
			rs = stat.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(null, stat, con);
		}
		return rs > 0;
	}


	public boolean delete(int depId, String[] proIds) {
		boolean flag=false;
		for (String proId : proIds) {
			flag=delete(depId, Integer.parseInt(proId));
		}
		return flag;
	}
}
