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

	private final SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public String send(Msg msg) throws Exception {
		simpMessagingTemplate.convertAndSend("/notice", msg);
		return null;
	}

	@Override
	public MsgType supportType() {
		return MsgType.WS;
	}

}
