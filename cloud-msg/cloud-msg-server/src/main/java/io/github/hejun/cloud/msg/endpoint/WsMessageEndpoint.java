package io.github.hejun.cloud.msg.endpoint;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket连接端点
 *
 * @author HeJun
 */
@Slf4j
@Component
@ServerEndpoint(value = "/ws")
public class WsMessageEndpoint {

	private final Map<String, Session> sessions = new ConcurrentHashMap<>();

	@OnOpen
	public void onOpen(Session session) {
		log.debug("Session Open: {}", session.getId());
		sessions.put(session.getId(), session);
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		log.debug("Session Close: {}, Reason: {}", session.getId(), closeReason);
		sessions.remove(session.getId());
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		log.error("Session Error: {}", session.getId(), throwable);
	}

}
