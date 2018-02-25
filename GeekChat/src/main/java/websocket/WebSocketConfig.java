package websocket;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Socket Configuration
 * @author Yuqi Li
 * date: Dec 2, 2017 1:09:50 AM
 */
@Component
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Resource
	MyWebSocketHandler handler;
	@Resource
	PublicChatSocketHandler pHandler;

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(handler, "/ws.do").addInterceptors(new HandShake());

		registry.addHandler(handler, "/ws/sockjs.do").addInterceptors(new HandShake()).withSockJS();
		
		registry.addHandler(pHandler, "/pws.do").addInterceptors(new PublicChatHandShake());

		registry.addHandler(pHandler, "/pws/sockjs.do").addInterceptors(new PublicChatHandShake()).withSockJS();
	}

}
