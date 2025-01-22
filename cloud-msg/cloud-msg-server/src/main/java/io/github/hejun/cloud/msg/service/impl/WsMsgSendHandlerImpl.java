package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.enums.WebSocketConstants;
import io.github.hejun.cloud.msg.service.MsgSendHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * WebSocket消息处理
 *
 * @author HeJun
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WsMsgSendHandlerImpl implements MsgSendHandler {

	private final RedisTemplate<String, MsgDto> redisTemplate;

	@Override
	public String send(MsgDto msg) throws Exception {
		log.debug("WsMsgSendHandler 接收到消息：{}", msg);
		Long receivedCount = redisTemplate.convertAndSend(WebSocketConstants.WS_MSG_DISTRIBUTE_CHANNEL, msg);
		return receivedCount != null && receivedCount > 0 ? "Success" : "Fail";
	}

	@Override
	public MsgType supportType() {
		return MsgType.WS;
	}

}
