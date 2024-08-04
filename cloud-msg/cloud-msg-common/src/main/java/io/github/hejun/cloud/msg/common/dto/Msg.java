package io.github.hejun.cloud.msg.common.dto;

import io.github.hejun.cloud.msg.common.enums.MsgType;
import lombok.Getter;
import lombok.Setter;

/**
 * 消息Dto
 *
 * @author HeJun
 */
@Getter
@Setter
public class Msg {

	/**
	 * 消息类型
	 */
	private MsgType msgType;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 发送人
	 */
	private String sender;
	/**
	 * 接收人
	 */
	private String receiver;

}
