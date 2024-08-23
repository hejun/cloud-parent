package io.github.hejun.cloud.msg.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hejun.cloud.common.exception.StatefulRuntimeException;
import io.github.hejun.cloud.msg.common.dto.Msg;
import io.github.hejun.cloud.msg.config.WsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * WebSocket消息推送到Redis监听器
 *
 * @author HeJun
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WsMsgRedisMessageListener implements MessageListener {

	private final ObjectMapper objectMapper;
	private final SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		log.debug("接收到消息：{}", message);
		Msg msg;
		try {
			msg = objectMapper.readValue(message.getBody(), Msg.class);
		} catch (IOException e) {
			throw new StatefulRuntimeException(500, e);
		}
		if ("*".equals(msg.getReceiver())) {
			simpMessagingTemplate.convertAndSend(WsConfig.KEY_DESTINATION_ALL, msg.getContent());
		} else {
			// 这里固定死 destination 为 notice， 前端订阅destination为：/user/{receiver}/notice
			simpMessagingTemplate.convertAndSendToUser(msg.getReceiver(), "/notice", msg.getContent());
		}
	}

}
