package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ScoreDaoImpl;
import pojo.Employee;
import pojo.Project;
import pojo.Score;
import util.Constant;
import util.Pagination;

public class ScoreServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	public String show(HttpServletRequest request,HttpServletResponse response) {
		ScoreDaoImpl sdi=new ScoreDaoImpl();
		int pageNow=1;
		if(null!=request.getParameter("pageNow")) {
			pageNow=Integer.parseInt(request.getParameter("pageNow"));
		}
		int dataCount=sdi.getDataCount();
		Pagination p=new Pagination(pageNow, dataCount);
		List<Score> sc=sdi.find(p.getBegin(),p.getDataNums());
		request.setAttribute("p", p);
		request.setAttribute("sc", sc);
		return "f:"+Constant.SC_NAME+"show.jsp";
	}
	
	public String updateView(HttpServletRequest request,HttpServletResponse response) {
		ScoreDaoImpl sdi=new ScoreDaoImpl();
		int pageNow=1;
		if(null!=request.getParameter("pageNow")) {
			pageNow=Integer.parseInt(request.getParameter("pageNow"));
		}
		int dataCount=sdi.getDataCount();
		Pagination p=new Pagination(pageNow, dataCount);
		List<Score> sc=sdi.find(p.getBegin(),p.getDataNums());
		request.setAttribute("p", p);
		request.setAttribute("sc", sc);
		return "f:"+Constant.SC_NAME+"update.jsp";
	}
	
	public void save(HttpServletRequest request,HttpServletResponse response) {
		String scId=request.getParameter("scId");
		String empId=request.getParameter("empId");
		String proId=request.getParameter("proId");
		String value=request.getParameter("value");
		if(null!=scId&&null!=empId&&null!=proId&&null!=value) {
			ScoreDaoImpl sdi=new ScoreDaoImpl();
			Score sc=new Score();
			sc.setId(Integer.parseInt(scId));
			Employee emp=new Employee();
			emp.setId(Integer.parseInt(empId));
			sc.setEmp(emp);
			Project pro=new Project();
			pro.setId(Integer.parseInt(proId));
			sc.setPro(pro);
			sc.setValue(Double.parseDouble(value));
			String str=sdi.save(sc);
			int id=Integer.parseInt(str.split(",")[0]);
			if(id==0) {
				id=Integer.parseInt(scId);
			}
			String grade=str.split(",")[1];
			PrintWriter pw;
			try {
				pw = response.getWriter();
				pw.print(id+","+grade);
				pw.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
}
