package io.github.hejun.cloud.common.exception;

import lombok.Getter;

/**
 * 系统自定义异常
 *
 * @author HeJun
 */
@Getter
public class StatefulException extends RuntimeException {

	private final Integer status;

	public StatefulException(Integer status, String message) {
		super(message);
		this.status = status;
	}

	public StatefulException(Integer status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}

	public StatefulException(Integer status, Throwable cause) {
		super(cause);
		this.status = status;
	}
}
