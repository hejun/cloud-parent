CREATE TABLE `t_msg`
(
    `id`          bigint       NOT NULL COMMENT 'ID',
    `type`        varchar(20)  NOT NULL COMMENT '消息类型',
    `content`     text         NOT NULL COMMENT '消息内容',
    `sender`      varchar(255) NOT NULL COMMENT '发送人',
    `receiver`    varchar(255) NOT NULL COMMENT '接收人, * 为全员发布',
    `status`      int(1) NOT NULL COMMENT '发送状态',
    `fail_reason` text NULL COMMENT '失败原因',
    `save_time`   datetime     NOT NULL COMMENT '保存时间',
    `send_time`   datetime NULL COMMENT '推送时间',
    PRIMARY KEY (`id`),
    INDEX         `index_msg_type`(`type`) COMMENT '消息类型索引',
    INDEX         `index_msg_status`(`status`) COMMENT '消息状态索引'
);
