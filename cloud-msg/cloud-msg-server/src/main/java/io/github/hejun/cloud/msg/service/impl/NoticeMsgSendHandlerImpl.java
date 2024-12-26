package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.service.MsgSendHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 通知消息处理
 *
 * @author HeJun
 */
@Slf4j
@Service
public class NoticeMsgSendHandlerImpl implements MsgSendHandler {

	@Override
	public String send(MsgDto msg) throws Exception {
		log.debug("NoticeMsgSendHandler 接收到消息：{}", msg);
		return "Success";
	}

	@Override
	public MsgType supportType() {
		return MsgType.NOTICE;
	}

}
