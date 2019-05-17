package web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String methodName = request.getParameter("method");
		Method method = null;
		try {
			// 获取子类中指定方法名的自定义方法
			method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		} catch (Exception e) {

			throw new RuntimeException("没有" + methodName + "这个方法");
		}
		try {
			String result = (String) method.invoke(this, request, response);
			// 判断调用的方法返回值是否为null和空字符串
			// 本方法是为了精简代码，避免在子类的自定义方法中写重复的转发重定向等冗余代码
			if (result != null && !result.trim().isEmpty()) {
				int index = result.indexOf(":");
				if (index == -1) {
					request.getRequestDispatcher(result).forward(request, response);
				} else {
					String header = result.substring(0, index);
					String path = result.substring(index+1);
					if (header.equals("f")) {
						request.getRequestDispatcher(path).forward(request, response);
					} else if (header.equals("r")) {
						response.sendRedirect(request.getContextPath() + path);
					}
				}
			}
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
