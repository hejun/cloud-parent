package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.service.MsgSendHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public String send(MsgDto msg) throws Exception {
		log.debug("WsMsgSendHandler 接收到消息：{}", msg);
		return "Success";
	}

	@Override
	public MsgType supportType() {
		return MsgType.WS;
	}

}
