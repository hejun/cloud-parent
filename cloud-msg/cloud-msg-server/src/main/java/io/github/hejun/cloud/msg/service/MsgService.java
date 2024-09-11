package io.github.hejun.cloud.msg.service;

import io.github.hejun.cloud.msg.common.dto.MsgDto;

/**
 * 消息Service
 *
 * @author HeJun
 */
public interface MsgService {

	/**
	 * 发送消息
	 *
	 * @param msg 消息
	 * @return 发送结果
	 * @throws Exception 发送消息异常
	 */
	String send(MsgDto msg) throws Exception;

}
