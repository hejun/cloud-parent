package io.github.hejun.cloud.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWK 配置
 *
 * @author HeJun
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.auth.jwk")
public class JwkProperties {

	private String alias;

	private String password;
}
