package web.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.DepartmentDaoImpl;
import dao.EmployeeDaoImpl;
import pojo.Department;
import pojo.Employee;
import util.Constant;
import util.Pagination;

public class EmployeeServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	/*
	 * fileName:用户上传的文件名 basePath:存储文件的物理路径 suffixName:上传文件后缀名 avatar:文件存储的相对路径
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("username");
		String sex = request.getParameter("sex");
		int age = Integer.parseInt(request.getParameter("age"));
		Integer depId = null;
		if (null != request.getParameter("depId")) {
			depId = Integer.parseInt(request.getParameter("depId"));
		}
		String avatar = request.getParameter("img");
		Employee emp = new Employee();
		emp.setName(name);
		emp.setSex(sex);
		emp.setAge(age);
		Department dep = new Department();
		dep.setId(depId);
		emp.setDep(dep);
		emp.setAvatar(avatar);
		EmployeeDaoImpl edi = new EmployeeDaoImpl();
		edi.add(emp);
		return "r:/EmployeeServlet?method=show";
	}

	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmployeeDaoImpl edi = new EmployeeDaoImpl();
		int id = Integer.parseInt(request.getParameter("id"));
		edi.delete(id);
		return "r:/EmployeeServlet?method=show";
	}

	public String update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id =Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("username");
		String sex = request.getParameter("sex");
		int age = Integer.parseInt(request.getParameter("age"));
		Integer depId = null;
		if (null != request.getParameter("depId")) {
			depId = Integer.parseInt(request.getParameter("depId"));
		}
		String avatar = request.getParameter("img");
		Employee emp = new Employee();
		emp.setId(id);
		emp.setName(name);
		emp.setSex(sex);
		emp.setAge(age);
		Department dep = new Department();
		dep.setId(depId);
		emp.setDep(dep);
		emp.setAvatar(avatar);
		EmployeeDaoImpl edi = new EmployeeDaoImpl();
		edi.update(emp);
		return "r:/EmployeeServlet?method=show";
	}

	public String show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("username");
		String sex = request.getParameter("sex");
		Integer age = null;
		if ((!"".equals(request.getParameter("age"))) && null != request.getParameter("age")) {
			age = Integer.parseInt(request.getParameter("age"));
		}
		int depId = -1;
		if (null != request.getParameter("depId")) {
			depId = Integer.parseInt(request.getParameter("depId"));
		}
		Employee emp = new Employee();
		emp.setName(name);
		emp.setSex(sex);
		emp.setAge(age);
		Department dep = new Department();
		dep.setId(depId);
		emp.setDep(dep);
		int pageNow = 1;
		if (request.getParameter("pageNow") != null) {
			pageNow = Integer.parseInt(request.getParameter("pageNow"));
		}
		EmployeeDaoImpl edi = new EmployeeDaoImpl();
		int dataCount = edi.getDataCount(emp);
		Pagination p = new Pagination(pageNow, dataCount);
		List<Employee> list = new ArrayList<>();
		list = edi.find(emp, p.getBegin(), p.getDataNums());
		DepartmentDaoImpl ddi = new DepartmentDaoImpl();
		List<Department> deps = ddi.find();
		request.setAttribute("deps", deps);
		request.setAttribute("emp", emp);
		request.setAttribute("p", p);
		request.setAttribute("emps", list);
		return "f:" + Constant.EMP_NAME + "show.jsp";
	}

	public String addView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DepartmentDaoImpl ddi = new DepartmentDaoImpl();
		List<Department> deps = ddi.find();
		request.setAttribute("deps", deps);
		return "f:" + Constant.EMP_NAME + "add.jsp";
	}

	public String updateView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Employee emp = new Employee();
		EmployeeDaoImpl edi = new EmployeeDaoImpl();
		emp = edi.findById(id);
		request.setAttribute("emp", emp);
		DepartmentDaoImpl ddi = new DepartmentDaoImpl();
		List<Department> deps = ddi.find();
		request.setAttribute("deps", deps);
		return "f:" + Constant.EMP_NAME + "update.jsp";
	}

	public void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileItemFactory dfif = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(dfif);
		sfu.setHeaderEncoding("UTF-8");
		try {
			List<FileItem> items = sfu.parseRequest(request);
			for (FileItem item : items) {
				if ("avatar".equals(item.getFieldName())) {
					String basePath = "G:/java/project/manageWeb/";
					String fileName = item.getName();
					String id = UUID.randomUUID().toString();
					int location = fileName.lastIndexOf(".");
					String suffixName = fileName.substring(location, fileName.length());
					String avatar = "upload/" + id + suffixName;
					File file = new File(basePath + avatar);
					item.write(file);
					response.getWriter().print(avatar);
				}
			}
		} catch (FileUploadException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void downloadAvatar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		EmployeeDaoImpl edi = new EmployeeDaoImpl();
		Employee emp = edi.findById(Integer.parseInt(id));
		String avatar = emp.getAvatar();
		if (null != avatar && !"null".equals(avatar)) {
			// 设置下载的响应类型
			response.setContentType("application/x-msdownload");
			String basePath = "g:/java/project/manageWeb/";
			String fileName = basePath + avatar;
			String downloadName = emp.getName() + avatar.substring(avatar.lastIndexOf("."), avatar.length());
			downloadName = new String(downloadName.getBytes("UTF-8"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attchment;filename=\"" + downloadName + "\"");
			File file = new File(fileName);
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			ServletOutputStream sos = response.getOutputStream();
			int len = -1;
			byte[] b = new byte[4096];
			while ((len = is.read(b)) != -1) {
				sos.write(b, 0, len);
			}
			is.close();
			sos.close();
		}
	}
}
