package web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDaoImpl;
import pojo.User;
import util.Constant;
import util.Encript;
import util.VerifyCode;

public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String methodName = request.getParameter("method");
		if (methodName == null) {
			showLogin(request, response);
		} else {
			Method method = null;
			try {
				// 获取子类中指定方法名的自定义方法
				method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
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
						String path = result.substring(index + 1);
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
	}

	public void createVerify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		VerifyCode vc=VerifyCode.getInstance();
		BufferedImage image=vc.getImage();
		vc.output(image, response.getOutputStream());
		request.getSession().setAttribute("verifyCode", vc.getText());
	}
	
	public void showLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			String username_error = (String) session.getAttribute("username_error");
			String password_error = (String) session.getAttribute("password_error");
			String noName_error = (String) session.getAttribute("noName_error");
			String noPsw_error = (String) session.getAttribute("noPsw_error");
			String noVC_error = (String) session.getAttribute("noVC_error");
			String verifyCode_error = (String) session.getAttribute("verifyCode_error");
			if (noName_error != null) {
				request.setAttribute("noName_error", noName_error);
				session.removeAttribute("noName_error");
				request.getRequestDispatcher(Constant.USER_NAME + "login.jsp").forward(request, response);
				return;
			}
			if (noPsw_error != null) {
				request.setAttribute("noPsw_error", noPsw_error);
				session.removeAttribute("noPsw_error");
				request.getRequestDispatcher(Constant.USER_NAME + "login.jsp").forward(request, response);
				return;
			}
			if (username_error != null) {
				request.setAttribute("username_error", username_error);
				session.removeAttribute("username_error");
				request.getRequestDispatcher(Constant.USER_NAME + "login.jsp").forward(request, response);
				return;
			}
			if (password_error != null) {
				request.setAttribute("password_error", password_error);
				session.removeAttribute("password_error");
				request.getRequestDispatcher(Constant.USER_NAME + "login.jsp").forward(request, response);
				return;
			}
			if(null!=noVC_error) {
				request.setAttribute("noVC_error", noVC_error);
				session.removeAttribute("noVC_error");
				request.getRequestDispatcher(Constant.USER_NAME + "login.jsp").forward(request, response);
				return;
			}
			if(null!=verifyCode_error) {
				request.setAttribute("verifyCode_error", verifyCode_error);
				session.removeAttribute("verifyCode_error");
				request.getRequestDispatcher(Constant.USER_NAME + "login.jsp").forward(request, response);
				return;
			}
			String username = "";
			Cookie[] cookies = request.getCookies();
			for (int i = 0; null != cookies && i < cookies.length; i++) {
				if ("username".equals(cookies[i].getName())) {
					username = cookies[i].getValue();
				}
			}
			request.setAttribute("username", username);
			request.getRequestDispatcher(Constant.USER_NAME + "login.jsp").forward(request, response);
		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		;
	}

	public void login(HttpServletRequest request, HttpServletResponse response) {
		try {
			UserDaoImpl udi = new UserDaoImpl();
			HttpSession session = request.getSession();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String verifyCode=request.getParameter("verifyCode");
			if (null == username || "".equals(username)) {
				session.setAttribute("noName_error", "用户名不能为空");
				response.sendRedirect("user.do?method=showLogin");
				return;
			}
			if (null == password || "".equals(password)) {
				session.setAttribute("noPsw_error", "密码不能为空");
				response.sendRedirect("user.do?method=showLogin");
				return;
			}
			if(null==verifyCode||"".equals(verifyCode)) {
				session.setAttribute("noVC_error", "验证码不能为空");
				response.sendRedirect("user.do?method=showLogin");
				return;
			}else {
				if(!verifyCode.equalsIgnoreCase((String) request.getSession().getAttribute("verifyCode"))) {
					session.removeAttribute("verifyCode");
					session.setAttribute("verifyCode_error", "验证码不正确");
					response.sendRedirect("user.do?method=showLogin");
					return;
				}
			}
			User user = udi.search(username);
			if (null != user) {
				if (!user.getPassword().equals(Encript.getMd5(username + password))) {
					session.setAttribute("password_error", "密码不正确");
					response.sendRedirect("user.do?method=showLogin");
					return;
				}
				Cookie cookie = new Cookie("username", username);
				cookie.setMaxAge(60);
				response.addCookie(cookie);
				session.setAttribute("user", user);
				response.sendRedirect("index");
			} else {
				session.setAttribute("username_error", "该用户不存在");
				response.sendRedirect("user.do?method=showLogin");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("user");
			response.sendRedirect("user.do?method=showLogin");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showRegist(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			String noVC_error = (String) session.getAttribute("noVC_error");
			String verifyCode_error = (String) session.getAttribute("verifyCode_error");
			if (null != session.getAttribute("nullName_error")) {
				String nullName_error = (String) session.getAttribute("nullName_error");
				request.setAttribute("nullName_error", nullName_error);
				session.removeAttribute("nullName_error");
				request.getRequestDispatcher(Constant.USER_NAME + "regist.jsp").forward(request, response);
				return;
			}
			if (null != session.getAttribute("nullPassword_error")) {
				String nullPassword_error = (String) session.getAttribute("nullPassword_error");
				request.setAttribute("nullPassword_error", nullPassword_error);
				session.removeAttribute("nullPassword_error");
				request.getRequestDispatcher(Constant.USER_NAME + "regist.jsp").forward(request, response);
				return;
			}
			if(null!=noVC_error) {
				request.setAttribute("noVC_error", noVC_error);
				session.removeAttribute("noVC_error");
				request.getRequestDispatcher(Constant.USER_NAME + "regist.jsp").forward(request, response);
				return;
			}
			if(null!=verifyCode_error) {
				request.setAttribute("verifyCode_error", verifyCode_error);
				session.removeAttribute("verifyCode_error");
				request.getRequestDispatcher(Constant.USER_NAME + "regist.jsp").forward(request, response);
				return;
			}
			if (null != session.getAttribute("regist_error")) {
				String regist_error = (String) session.getAttribute("regist_error");
				request.setAttribute("regist_error", regist_error);
				session.removeAttribute("regist_error");
				request.getRequestDispatcher(Constant.USER_NAME + "regist.jsp").forward(request, response);
				return;
			}
			if (null != session.getAttribute("regist_fail")) {
				String regist_fail = (String) session.getAttribute("regist_fail");
				request.setAttribute("regist_fail", regist_fail);
				session.removeAttribute("regist_fail");
				request.getRequestDispatcher(Constant.USER_NAME + "regist.jsp").forward(request, response);
				return;
			}
			request.getRequestDispatcher(Constant.USER_NAME + "regist.jsp").forward(request, response);
		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		;
	}

	public void regist(HttpServletRequest request, HttpServletResponse response) {
		try {
			UserDaoImpl udi = new UserDaoImpl();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String verifyCode = request.getParameter("verifyCode");
			HttpSession session = request.getSession();
			if (null == username || username.length() < 3) {
				session.setAttribute("nullName_error", "该用户名不能为空且不能小于3个字符");
				response.sendRedirect("user.do?method=showRegist");
				return;
			}
			if (null == password || password.length() < 6) {
				session.setAttribute("nullPassword_error", "密码不能为空且不能小于6个字符");
				response.sendRedirect("user.do?method=showRegist");
				return;
			}
			if(null==verifyCode||"".equals(verifyCode)) {
				session.setAttribute("noVC_error", "验证码不能为空");
				response.sendRedirect("user.do?method=showRegist");
				return;
			}else {
				if(!verifyCode.equalsIgnoreCase((String) request.getSession().getAttribute("verifyCode"))) {
					session.removeAttribute("verifyCode");
					session.setAttribute("verifyCode_error", "验证码不正确");
					response.sendRedirect("user.do?method=showRegist");
					return;
				}
			}
			User user = udi.search(username);
			if (null != user) {
				session.setAttribute("regist_error", "该用户名已存在");
				response.sendRedirect("user.do?method=showRegist");
			} else {
				user = new User();
				user.setUsername(username);
				user.setPassword(Encript.getMd5(username + password));
				boolean flag = udi.add(user);
				if (flag) {
					response.sendRedirect("user.do?method=showLogin");
				} else {
					session.setAttribute("regist_fail", "注册失败，请稍后再试");
					response.sendRedirect("user.do?method=showRegist");
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
