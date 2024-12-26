package io.github.hejun.cloud.msg.service;

import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.common.enums.MsgType;

/**
 * 消息处理 Handler
 *
 * @author HeJun
 */
public interface MsgSendHandler {

	/**
	 * 发送消息
	 *
	 * @param msg 消息
	 * @return 发送结果
	 * @throws Exception 发送消息异常
	 */
	String send(MsgDto msg) throws Exception;

	/**
	 * 返回实现类支持的类型
	 *
	 * @return 支持的类型
	 */
	MsgType supportType();

}
