
package websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * HandShake
 * 
 * @author Yuqi Li date: Dec 2, 2017 1:09:23 AM
 */
public class HandShake implements HandshakeInterceptor {

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			// as
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			// Mark Users
			String uid = (String)session.getAttribute("uid");
			if (uid != null) {
				String objectId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("objectId");
				attributes.put("uid", uid);
				if (objectId != null) {
					attributes.put("objectId", objectId);
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// System.out.println("after hand");

	}

}
