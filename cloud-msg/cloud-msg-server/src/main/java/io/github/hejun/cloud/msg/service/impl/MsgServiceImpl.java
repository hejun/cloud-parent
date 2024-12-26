package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.common.exception.StatefulRuntimeException;
import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.service.MsgSendHandler;
import io.github.hejun.cloud.msg.service.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息服务
 *
 * @author HeJun
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MsgServiceImpl implements MsgService {

	private Map<MsgType, MsgSendHandler> msgSendHandleStrategy;

	@Override
	public String send(MsgDto msg) throws Exception {
		MsgSendHandler handler = msgSendHandleStrategy.get(msg.getType());
		if (handler == null) {
			throw new StatefulRuntimeException(500, "无法处理的消息类型: [ " + msg.getType() + " ]");
		}
		return handler.send(msg);
	}

	@Autowired
	public void setMsgHandleStrategy(List<MsgSendHandler> msgSendHandlers) {
		if (msgSendHandleStrategy == null) {
			msgSendHandleStrategy = new HashMap<>();
		}
		for (MsgSendHandler handler : msgSendHandlers) {
			this.msgSendHandleStrategy.put(handler.supportType(), handler);
		}
	}

}
