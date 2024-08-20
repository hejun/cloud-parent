package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.msg.common.dto.Msg;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.service.MsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

	public static final String KEY_DESTINATION_ALL = "/topic";
	public static final String KEY_DESTINATION_USER = "/user/";

	private final SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public String send(Msg msg) throws Exception {
		if ("*".equals(msg.getReceiver())) {
			simpMessagingTemplate.convertAndSend(KEY_DESTINATION_ALL, msg.getContent());
		} else {
			// 这里固定死 destination 为 notice， 前端订阅destination为：/user/{receiver}/notice
			simpMessagingTemplate.convertAndSendToUser(msg.getReceiver(), "/notice", msg.getContent());
		}
		return null;
	}

	@Override
	public MsgType supportType() {
		return MsgType.WS;
	}

}
