package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.config.RedisConfig;
import io.github.hejun.cloud.msg.service.MsgService;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WsMsgServiceImpl implements MsgService {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public String send(MsgDto msg) throws Exception {
		log.debug("接收到消息：{}", msg);
		// TODO persist to database
		redisTemplate.convertAndSend(RedisConfig.KEY_REDIS_TOPIC_MSG_WS, msg);
		return null;
	}

	@Override
	public MsgType supportType() {
		return MsgType.WS;
	}

}
