package web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DepartmentDaoImpl;
import pojo.Department;
import util.Constant;
import util.Pagination;

public class DepartmentServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		String name=request.getParameter("depName");
		Department dep=new Department();
		dep.setName(name);
		DepartmentDaoImpl ddi=new DepartmentDaoImpl();
		ddi.add(dep);
		return "r:/DepartmentServlet?method=show";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		int id=Integer.parseInt(request.getParameter("id"));
		DepartmentDaoImpl ddi=new DepartmentDaoImpl();
		ddi.delete(id);
		return "r:/DepartmentServlet?method=show";
	}
	
	public String update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("depName");
		Department dep=new Department();
		dep.setId(id);
		dep.setName(name);
		DepartmentDaoImpl ddi=new DepartmentDaoImpl();
		ddi.update(dep);
		return "r:/DepartmentServlet?method=show";
	}
	
	public String show(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		String depName=request.getParameter("depName");
		Integer empNums=null;
		if(null!=request.getParameter("empNums")&&!"".equals(request.getParameter("empNums"))) {
			empNums=Integer.parseInt(request.getParameter("empNums"));
		}
		Department dep=new Department();
		dep.setName(depName);
		dep.setEmpNums(empNums);
		int pageNow=1;
		if(request.getParameter("pageNow")!=null) {
			pageNow=Integer.parseInt(request.getParameter("pageNow"));
		}
		DepartmentDaoImpl ddi=new DepartmentDaoImpl();
		int dataCount=ddi.getDataCount(dep);
		Pagination p=new Pagination(pageNow, dataCount);
		List<Department> deps=new ArrayList<>();
		deps=ddi.find(dep, p.getBegin(), p.getDataNums());
		List<Department> depList=new ArrayList<>();
		depList=ddi.find();
		request.setAttribute("dep", dep);
		request.setAttribute("p", p);
		request.setAttribute("deps", deps);
		request.setAttribute("depList", depList);
		return "f:"+Constant.DEP_NAME+"show.jsp";
	}
	
	public String addView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		DepartmentDaoImpl ddi=new DepartmentDaoImpl();
		List<Department> deps=ddi.find();
		request.setAttribute("deps", deps);
		return "f:"+Constant.DEP_NAME+"add.jsp";
	}
	
	public String updateView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		int id=Integer.parseInt(request.getParameter("id"));
		Department dep=new Department();
		DepartmentDaoImpl ddi=new DepartmentDaoImpl();
		dep=ddi.findById(id);
		request.setAttribute("dep", dep);
		return "f:"+Constant.DEP_NAME+"update.jsp";
	}
}
