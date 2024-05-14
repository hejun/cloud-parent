package io.github.hejun.cloud.common.exception;

import lombok.Getter;

/**
 * 系统自定义异常
 *
 * @author HeJun
 */
@Getter
public class StatefulException extends Exception {

	private final Integer code;

	public StatefulException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public StatefulException(Integer code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public StatefulException(Integer code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

}
