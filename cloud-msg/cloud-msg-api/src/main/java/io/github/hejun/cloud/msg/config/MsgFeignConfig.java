package io.github.hejun.cloud.msg.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign配置
 *
 * @author HeJun
 */
@EnableFeignClients(basePackages = "io.github.hejun.cloud.msg.feign")
public class MsgFeignConfig {
}
