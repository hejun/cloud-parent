package io.github.hejun.cloud.msg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.common.enums.SendStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

/**
 * @author HeJun
 */
@Getter
@Setter
@TableName(value = "t_msg")
public class Msg {

	/**
	 * ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 消息类型
	 */
	private MsgType type;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 发送人
	 */
	private String sender;

	/**
	 * 接收人, * 为全员发布
	 */
	private String receiver;

	/**
	 * 发送状态
	 */
	private SendStatus status;

	/**
	 * 失败原因
	 */
	private String failReason;

	/**
	 * 保存时间
	 */
	@CreatedDate
	private Date saveTime;

	/**
	 * 推送时间
	 */
	private Date sendTime;

}
