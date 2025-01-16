package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.enums.WebSocketConstants;
import io.github.hejun.cloud.msg.service.MsgSendHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

/**
 * WebSocket消息处理
 *
 * @author HeJun
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WsMsgSendHandlerImpl implements MsgSendHandler {

	private final SimpMessagingTemplate simpMessagingTemplate;
	private final SimpUserRegistry userRegistry;

	@Override
	public String send(MsgDto msg) throws Exception {
		log.debug("WsMsgSendHandler 接收到消息：{}", msg);
		if ("*".equals(msg.getReceiver())) {
			simpMessagingTemplate.convertAndSend(WebSocketConstants.DESTINATION_ALL, msg.getContent());
		} else if (userRegistry.getUser(msg.getReceiver()) != null) {
			// 这里固定死 destination 为 / ， 前端订阅destination为：/user/{receiver}/
			simpMessagingTemplate.convertAndSendToUser(msg.getReceiver(), "/", msg.getContent());
		} else {
			log.info("未匹配到User: {},忽略推送", msg.getReceiver());
		}
		return "Success";
	}

	@Override
	public MsgType supportType() {
		return MsgType.WS;
	}

}
