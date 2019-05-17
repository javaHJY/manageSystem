package web.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import util.MyWebSocket;

public class UserListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		int count = 0;
		if (application.getAttribute("online_session_count") != null) {
			count = (Integer) application.getAttribute("online_session_count");
		}
		application.setAttribute("online_session_count", ++count);
		MyWebSocket.sendMessageAll(String.valueOf(count));
		System.out.println("session建立");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		int count = 0;
		if (application.getAttribute("online_session_count") != null) {
			count = (Integer) application.getAttribute("online_session_count");
		}
		application.setAttribute("online_session_count", --count);
		MyWebSocket.sendMessageAll(String.valueOf(count));
		System.out.println("session失效");
	}

}
