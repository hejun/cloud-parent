package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.service.MsgService;
import org.springframework.stereotype.Service;

/**
 * 通知消息处理
 *
 * @author HeJun
 */
@Service
public class NoticeMsgServiceImpl implements MsgService {

	@Override
	public String send(MsgDto msg) throws Exception {
		return "";
	}

	@Override
	public MsgType supportType() {
		return MsgType.NOTICE;
	}

}
