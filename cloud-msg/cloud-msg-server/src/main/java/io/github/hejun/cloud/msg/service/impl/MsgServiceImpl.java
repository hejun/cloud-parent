package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.common.exception.StatefulRuntimeException;
import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.common.enums.SendStatus;
import io.github.hejun.cloud.msg.entity.Msg;
import io.github.hejun.cloud.msg.handler.MsgHandler;
import io.github.hejun.cloud.msg.mapper.MsgMapper;
import io.github.hejun.cloud.msg.mapstruct.MsgStructMapper;
import io.github.hejun.cloud.msg.service.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息服务
 *
 * @author HeJun
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MsgServiceImpl implements MsgService {

	private Map<MsgType, MsgHandler> msgHandleStrategy;
	private final MsgStructMapper msgStructMapper;
	private final MsgMapper msgMapper;

	@Override
	public String send(MsgDto msg) throws Exception {
		MsgHandler handler = msgHandleStrategy.get(msg.getType());
		if (handler == null) {
			throw new StatefulRuntimeException(500, "无法处理的消息类型: [ " + msg.getType() + " ]");
		}
		Msg entity = msgStructMapper.convert(msg);
		msgMapper.insert(entity);
		try {
			String result = handler.send(msg);
			entity.setSendTime(new Date());
			entity.setStatus(SendStatus.SEND);
			msgMapper.updateById(entity);
			return result;
		} catch (Exception e) {
			entity.setSendTime(new Date());
			entity.setFailReason(e.getMessage());
			entity.setStatus(SendStatus.FAIL);
			msgMapper.updateById(entity);
			return e.getMessage();
		}
	}

	@Autowired
	public void setMsgHandleStrategy(List<MsgHandler> msgServiceList) {
		if (msgHandleStrategy == null) {
			msgHandleStrategy = new HashMap<>();
		}
		for (MsgHandler handler : msgServiceList) {
			this.msgHandleStrategy.put(handler.supportType(), handler);
		}
	}

}
