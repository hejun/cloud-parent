package io.github.hejun.cloud.msg.util;

import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket线程池
 *
 * @author HeJun
 */
@Slf4j
public class WsPoolManager {

	private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

	public static Session get(String key) {
		return sessions.get(key);
	}

	public static Collection<Session> getAll() {
		return sessions.values();
	}

	public static void put(String key, Session session) {
		sessions.put(key, session);
	}

	public static void remove(String key) {
		sessions.remove(key);
	}

}
