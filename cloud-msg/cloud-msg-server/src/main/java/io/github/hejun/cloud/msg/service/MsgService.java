package io.github.hejun.cloud.msg.service;

import io.github.hejun.cloud.msg.common.dto.Msg;
import io.github.hejun.cloud.msg.common.enums.MsgType;

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
	String send(Msg msg) throws Exception;

	/**
	 * 返回实现类支持的类型
	 *
	 * @return 支持的类型
	 */
	MsgType supportType();

}
