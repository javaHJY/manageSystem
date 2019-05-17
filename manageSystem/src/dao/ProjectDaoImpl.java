package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pojo.Project;

public class ProjectDaoImpl implements ProjectDao {

	@Override
	public boolean add(Project pro) {
		int rs=0;
		Connection con=null;
		Statement stat=null;
		try {
			con=JDBCUtils.getConnection();
			stat=con.createStatement();
			String sql="insert into project (name) values('"+pro.getName()+"')";
			rs=stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(null, stat, con);
		}
		return rs>0;
	}
	
	@Override
	public boolean delete(int id) {
		int rs=0;
		Connection con=null;
		Statement stat=null;
		try {
			con=JDBCUtils.getConnection();
			stat=con.createStatement();
			String sql="delete from project where id="+id;
			rs=stat.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(null, stat, con);
		} 
		return rs>0;	
	}
	
	@Override
	public boolean update(Project pro) {
		int rs=0;
		Connection con=null;
		Statement stat=null;
		try {
			con=JDBCUtils.getConnection();
			stat=con.createStatement();
			String sql="update project set name='"
					+pro.getName()+"' where id="+pro.getId();
			rs=stat.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(null, stat, con);
		} 
		return rs>0;
	}

	@Override
	public Project findById(int id) {
		Connection con=null;
		Statement stat=null;
		ResultSet rs=null;
		Project pro=null;
		try {
			con=JDBCUtils.getConnection();
			stat=con.createStatement();
			String sql="select * from project where id="+id;
			rs=stat.executeQuery(sql);
			while(rs.next()) {
				pro=new Project();
				pro.setId(id);
				pro.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return pro;
	}

	@Override
	public List<Project> findByItem(Project pro) {
		Connection con=null;
		Statement stat=null;
		ResultSet rs=null;
		List<Project> searchList=new ArrayList<>();
		try {
			con=JDBCUtils.getConnection();
			stat = con.createStatement();
			String sql="select id,name from project where 1=1";
			if(pro.getId()!=-1)
				sql+=" and id="+pro.getId();
			if(pro.getName()!=null)
				sql+=" and name like'%"+pro.getName()+"%'";
			rs=stat.executeQuery(sql);
			if(rs==null)
				return null;
			while(rs.next()) {
				Project proNext=new Project();
				proNext.setId(rs.getInt("id"));
				proNext.setName(rs.getString("name"));
				searchList.add(proNext);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return searchList;
	}
	public List<Project> find(Project condition,int begin,int dataNums) {
		List<Project> list = new ArrayList<>();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String where=" where 1=1";
			if(null!=condition.getId()) {
				where+=" and id="+condition.getId();
			}
			String sql = "select * from project"+where+" limit " + begin + "," + dataNums;
			rs = stat.executeQuery(sql);
			if (rs == null)
				return null;
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));
				list.add(pro);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return list;
	}
	@Override
	public List<Project> find() {
		Connection con=null;
		Statement stat=null;
		ResultSet rs=null;
		List<Project> searchList=new ArrayList<>();
		try {
			con=JDBCUtils.getConnection();
			stat=con.createStatement();
			String sql="select * from project";
			rs=stat.executeQuery(sql);
			while(rs.next()) {
				Project pro=new Project();
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

	
	public int getDataCount(Project pro) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = JDBCUtils.getConnection();
			stat = con.createStatement();
			String where=" where 1=1";
			if(null!=pro.getId()) {
				where+=" and id="+pro.getId();
			}
			String sql = "select count(*) as count from project"+where;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				count=rs.getInt("count");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeAll(rs, stat, con);
		}
		return count;
	}
}
