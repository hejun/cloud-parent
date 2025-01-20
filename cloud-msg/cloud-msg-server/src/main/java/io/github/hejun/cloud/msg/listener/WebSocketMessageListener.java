package io.github.hejun.cloud.msg.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hejun.cloud.common.exception.StatefulRuntimeException;
import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.enums.WebSocketConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * WebSocket 消息 Listener
 * <p>
 * 使用 RedisChannel 监听发送消息实现分布式
 *
 * @author HeJun
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSocketMessageListener implements MessageListener {

	private final SimpMessagingTemplate simpMessagingTemplate;
	private final SimpUserRegistry userRegistry;
	private final ObjectMapper objectMapper;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		if (pattern == null) {
			return;
		}
		log.debug("Channel: [ {} ] 接收到消息：{}", new String(pattern), message);
		try {
			MsgDto msg = objectMapper.readValue(message.getBody(), MsgDto.class);
			if ("*".equals(msg.getReceiver())) {
				simpMessagingTemplate.convertAndSend(WebSocketConstants.DESTINATION_ALL, msg.getContent());
			} else if (userRegistry.getUser(msg.getReceiver()) != null) {
				// 这里固定死 destination 为 / ， 前端订阅destination为：/user/{receiver}/
				simpMessagingTemplate.convertAndSendToUser(msg.getReceiver(), "/", msg.getContent());
			} else {
				log.info("未匹配到User: {},忽略推送", msg.getReceiver());
			}
		} catch (IOException e) {
			throw new StatefulRuntimeException(500, e);
		}
	}

}
