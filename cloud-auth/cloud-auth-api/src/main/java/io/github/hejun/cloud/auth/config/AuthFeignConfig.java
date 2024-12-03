package io.github.hejun.cloud.auth.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign配置
 *
 * @author HeJun
 */
@EnableFeignClients(basePackages = "io.github.hejun.cloud.auth.feign")
public class AuthFeignConfig {
}
