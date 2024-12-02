package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.service.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息服务
 *
 * @author HeJun
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MsgServiceImpl implements MsgService {

	@Override
	public String send(MsgDto msg) throws Exception {
		return "Success";
	}

}
