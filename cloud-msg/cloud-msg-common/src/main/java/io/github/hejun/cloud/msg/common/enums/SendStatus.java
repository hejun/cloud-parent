package io.github.hejun.cloud.msg.common.enums;

import lombok.Getter;

/**
 * 发送状态
 *
 * @author HeJun
 */
@Getter
public enum SendStatus {

	/**
	 * 待发送
	 */
	WAIT(0),
	/**
	 * 已发送
	 */
	SEND(1),
	/**
	 * 发送失败
	 */
	FAIL(-1);

	private final Integer status;

	SendStatus(Integer status) {
		this.status = status;
	}

}
