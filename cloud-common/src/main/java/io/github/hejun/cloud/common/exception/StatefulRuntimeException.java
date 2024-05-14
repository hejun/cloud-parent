package io.github.hejun.cloud.common.exception;

import lombok.Getter;

/**
 * 系统自定义Runtime异常
 *
 * @author HeJun
 */
@Getter
public class StatefulRuntimeException extends RuntimeException {

	private final Integer code;

	public StatefulRuntimeException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public StatefulRuntimeException(Integer code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public StatefulRuntimeException(Integer code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

}
