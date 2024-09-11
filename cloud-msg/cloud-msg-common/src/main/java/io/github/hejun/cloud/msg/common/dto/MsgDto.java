package io.github.hejun.cloud.msg.common.dto;

import io.github.hejun.cloud.msg.common.enums.MsgType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息Dto
 *
 * @author HeJun
 */
@Getter
@Setter
@ToString
public class MsgDto {

	/**
	 * 消息类型
	 */
	@NotNull(message = "消息类型不可为空")
	private MsgType type;
	/**
	 * 消息内容
	 */
	@NotBlank(message = "消息内容不可为空")
	private String content;
	/**
	 * 发送人
	 */
	@NotBlank(message = "发送人不可为空")
	private String sender;
	/**
	 * 接收人, * 为全员发布
	 */
	@NotBlank(message = "接收人不可为空")
	private String receiver;

}
