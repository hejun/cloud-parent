package io.github.hejun.cloud.msg.handler.impl;

import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.handler.MsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 通知消息处理
 *
 * @author HeJun
 */
@Slf4j
@Service
public class NoticeMsgHandlerImpl implements MsgHandler {

	@Override
	public String send(MsgDto msg) throws Exception {
		log.debug("NoticeHandler接收到消息：{}", msg);
		return "";
	}

	@Override
	public MsgType supportType() {
		return MsgType.NOTICE;
	}

}
