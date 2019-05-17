package util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//该注解用来指定一个url
@ServerEndpoint("/websocket")
public class MyWebSocket {

	// Set，用来存放每个客户端对应的Session对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	public static Set<Session> set = new HashSet<Session>();

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		set.add(session); // 加入set中
		System.out.println("有新连接加入！");
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(Session session) {
		set.remove(session); // 从set中删除
		System.out.println("有一连接关闭！");
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	// @OnMessage
	// public void onMessage(String message, Session session) {
	// System.out.println("来自客户端的消息:" + message);
	//
	// // 群发消息
	// for (MyWebSocket item : set) {
	// try {
	// item.sendMessage(message);
	// } catch (IOException e) {
	// e.printStackTrace();
	// continue;
	// }
	// }
	// }

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	// 群发消息
	public static void sendMessageAll(String message) {
		try {
			for (Session session : set) {
				System.out.println(message);
				// 给socket会话发送消息
				session.getBasicRemote().sendText(message);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
