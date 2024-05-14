package io.github.hejun.cloud.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.hejun.cloud.common.exception.StatefulException;
import io.github.hejun.cloud.common.exception.StatefulRuntimeException;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * 返回结果封装类
 *
 * @author HeJun
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class Result<T> implements Serializable {

	private Integer code;
	private String msg;
	private T data;

	/**
	 * 成功
	 *
	 * @param data 返回数据
	 * @return Result封装类
	 */
	public static <T> Result<T> SUCCESS(T data) {
		Result<T> result = new Result<>();
		result.code = 200;
		result.msg = "成功";
		result.data = data;
		return result;
	}

	/**
	 * 成功
	 *
	 * @return Result封装类
	 */
	public static <T> Result<T> SUCCESS() {
		return Result.SUCCESS(null);
	}

	/**
	 * 失败
	 *
	 * @param code 错误码
	 * @param msg  错误消息
	 * @param data 返回数据
	 * @return Result封装类
	 */
	public static <T> Result<T> ERROR(Integer code, String msg, T data) {
		Result<T> result = new Result<>();
		result.code = code;
		result.msg = msg;
		result.data = data;
		return result;
	}

	/**
	 * 失败
	 *
	 * @param code 错误码
	 * @param msg  错误消息
	 * @return Result封装类
	 */
	public static <T> Result<T> ERROR(Integer code, String msg) {
		return Result.ERROR(code, msg, null);
	}

	/**
	 * 失败
	 *
	 * @param statefulException 系统自定义异常
	 * @param data              返回数据
	 * @return Result封装类
	 */
	public static <T> Result<T> ERROR(StatefulException statefulException, T data) {
		if (Objects.nonNull(statefulException)) {
			return Result.ERROR(statefulException.getCode(), statefulException.getMessage(), data);
		} else {
			return Result.ERROR(500, "服务器异常", data);
		}
	}

	/**
	 * 失败
	 *
	 * @param statefulException 系统自定义异常
	 * @return Result封装类
	 */
	public static <T> Result<T> ERROR(StatefulException statefulException) {
		return Result.ERROR(statefulException, null);
	}

	/**
	 * 失败
	 *
	 * @param statefulRuntimeException 系统自定义Runtime异常
	 * @param data                     返回数据
	 * @return Result封装类
	 */
	public static <T> Result<T> ERROR(StatefulRuntimeException statefulRuntimeException, T data) {
		if (Objects.nonNull(statefulRuntimeException)) {
			return Result.ERROR(statefulRuntimeException.getCode(), statefulRuntimeException.getMessage(), data);
		} else {
			return Result.ERROR(500, "服务器异常", data);
		}
	}

	/**
	 * 失败
	 *
	 * @param statefulRuntimeException 系统自定义Runtime异常
	 * @return Result封装类
	 */
	public static <T> Result<T> ERROR(StatefulRuntimeException statefulRuntimeException) {
		return Result.ERROR(statefulRuntimeException, null);
	}

}
