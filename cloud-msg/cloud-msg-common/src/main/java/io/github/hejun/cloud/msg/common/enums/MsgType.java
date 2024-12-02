package io.github.hejun.cloud.msg.common.enums;

import lombok.Getter;

/**
 * 消息类型
 *
 * @author HeJun
 */
@Getter
public enum MsgType {

    /**
     * 通知(站内信)
     */
    NOTICE,
    /**
     * 邮件
     */
    EMAIL,
    /**
     * WebSocket
     */
    WS,

}
