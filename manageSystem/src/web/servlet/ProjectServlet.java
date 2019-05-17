package web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProjectDaoImpl;
import pojo.Project;
import util.Constant;
import util.Pagination;

public class ProjectServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		String name=request.getParameter("proName");
		Project pro=new Project();
		pro.setName(name);
		ProjectDaoImpl pdi=new ProjectDaoImpl();
		pdi.add(pro);
		return "r:/ProjectServlet?method=show";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		int id=Integer.parseInt(request.getParameter("id"));
		ProjectDaoImpl pdi=new ProjectDaoImpl();
		pdi.delete(id);
		return "r:/ProjectServlet?method=show";
	}
	
	public String update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		int id=Integer.parseInt(request.getParameter("id"));
		String proName=request.getParameter("proName");
		Project pro=new Project();
		pro.setId(id);
		pro.setName(proName);
		ProjectDaoImpl pdi=new ProjectDaoImpl();
		pdi.update(pro);
		return "r:/ProjectServlet?method=show";
	}
	
	public String show(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		//获取条件查询的参数
		Integer proId=null;
		if(null!=request.getParameter("proId")&&!"".equals(request.getParameter("proId"))&&!"-1".equals(request.getParameter("proId"))) {
			proId=Integer.parseInt(request.getParameter("proId"));
		}
		Project pro=new Project();
		pro.setId(proId);
		int pageNow=1;
		if(request.getParameter("pageNow")!=null) {
			pageNow=Integer.parseInt(request.getParameter("pageNow"));
		}
		ProjectDaoImpl pdi=new ProjectDaoImpl();
		int dataCount=pdi.getDataCount(pro);
		Pagination p=new Pagination(pageNow, dataCount);
		List<Project> pros=new ArrayList<>();
		pros=pdi.find(pro, p.getBegin(), p.getDataNums());
		List<Project> proList=new ArrayList<>();
		proList=pdi.find();
		request.setAttribute("pro", pro);
		request.setAttribute("p", p);
		request.setAttribute("pros", pros);
		request.setAttribute("proList", proList);
		return "f:"+Constant.PRO_NAME+"show.jsp";
	}
	
	public String addView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		return "f:"+Constant.PRO_NAME+"add.jsp";
	}
	
	public String updateView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		int id=Integer.parseInt(request.getParameter("id"));
		Project pro=new Project();
		ProjectDaoImpl pdi=new ProjectDaoImpl();
		pro=pdi.findById(id);
		request.setAttribute("pro", pro);
		return "f:"+Constant.PRO_NAME+"update.jsp";
	}
}
