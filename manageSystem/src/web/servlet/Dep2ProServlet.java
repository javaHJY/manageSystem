package web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DepManageProDaoImpl;
import dao.DepartmentDaoImpl;
import pojo.Department;
import pojo.Project;
import util.Constant;

public class Dep2ProServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public String manageProView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DepartmentDaoImpl ddi=new DepartmentDaoImpl();
		DepManageProDaoImpl dmpdi=new DepManageProDaoImpl();
		int depId = Integer.parseInt(request.getParameter("depId"));
		// 选择的部门
		Department dep = new Department();
		dep = ddi.findById(depId);
		// 部门已有项目
		List<Project> proList = new ArrayList<>();
		proList = dmpdi.findById(depId);
		// 部门没有的项目
		List<Project> proNoList = new ArrayList<>();
		proNoList = dmpdi.findByNoDep(depId);
		request.setAttribute("dep", dep);
		request.setAttribute("proList", proList);
		request.setAttribute("proNoList", proNoList);
		return "f:" + Constant.DEP_NAME + "managePro.jsp";
	}
	
	public String depAddPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		DepManageProDaoImpl dmpdi=new DepManageProDaoImpl();
		Integer noProId=null;
		if(null!=request.getParameter("noProId")&&!"".equals(request.getParameter("noProId"))) {
			noProId=Integer.parseInt(request.getParameter("noProId"));
		}
		Integer depId=null;
		if(null!=request.getParameter("depId")&&!"".equals(request.getParameter("depId"))) {
			depId=Integer.parseInt(request.getParameter("depId"));
		}
		if(null!=noProId) {
			dmpdi.add(depId,noProId);
		}
		if(null!=request.getParameter("noProIds")) {
			String unHave=request.getParameter("noProIds");
			String[] noProIds=unHave.split(",");
			dmpdi.add(depId,noProIds);
		}
		return "r:/Dep2ProServlet?method=manageProView&depId="+depId;
	}
	public String depDeletePro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		DepManageProDaoImpl dmpdi=new DepManageProDaoImpl();
		Integer proId=null;
		if(null!=request.getParameter("proId")&&!"".equals(request.getParameter("proId"))) {
			proId=Integer.parseInt(request.getParameter("proId"));
		}
		Integer depId=null;
		if(null!=request.getParameter("depId")&&!"".equals(request.getParameter("depId"))) {
			depId=Integer.parseInt(request.getParameter("depId"));
		}
		if(null!=proId) {
			dmpdi.delete(depId,proId);
		}
		if(null!=request.getParameter("proIds")) {
			String have=request.getParameter("proIds");
			String[] proIds=have.split(",");
			dmpdi.delete(depId,proIds);
		}
		return "r:/Dep2ProServlet?method=manageProView&depId="+depId;
	}
}
