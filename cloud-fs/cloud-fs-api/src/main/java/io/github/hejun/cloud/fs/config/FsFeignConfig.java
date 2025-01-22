package io.github.hejun.cloud.fs.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign配置
 *
 * @author HeJun
 */
@EnableFeignClients(basePackages = "io.github.hejun.cloud.fs.feign")
public class FsFeignConfig {
}
