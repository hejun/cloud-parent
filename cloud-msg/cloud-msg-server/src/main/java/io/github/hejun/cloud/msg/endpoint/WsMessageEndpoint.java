package io.github.hejun.cloud.msg.endpoint;

import io.github.hejun.cloud.msg.util.WsPoolManager;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * WebSocket连接端点
 *
 * @author HeJun
 */
@Slf4j
@Component
@ServerEndpoint(value = "/ws")
public class WsMessageEndpoint {

	private final String KEY_TOKEN = "token";

	@OnOpen
	public void onOpen(Session session) throws IOException {
		String token = CollectionUtils.firstElement(session.getRequestParameterMap().get(KEY_TOKEN));
		if (StringUtils.hasText(token)) {
			log.debug("Session Open: {}, token: {}", session.getId(), token);
			WsPoolManager.put(token, session);
		} else {
			log.debug("Session [ {} ] miss token, refuse connect.", session.getId());
			session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Missing token"));
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		String token = CollectionUtils.firstElement(session.getRequestParameterMap().get(KEY_TOKEN));
		if (StringUtils.hasText(token)) {
			WsPoolManager.remove(token);
		}
		log.debug("Session Close: {}, Reason: {}", session.getId(), closeReason);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		log.error("Session Error: {}", session.getId(), throwable);
	}

}
