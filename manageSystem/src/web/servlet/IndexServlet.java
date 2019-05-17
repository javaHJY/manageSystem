package web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
/*
 * 此版本实现ajax修改单个数据，局部刷新
 * */
public class IndexServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		show(request, response);
	}

	public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(Constant.PATH_NAME+"index.jsp").forward(request, response);
//		return "f:/" + Constant.PATH_NAME + "index.jsp";
	}

}
