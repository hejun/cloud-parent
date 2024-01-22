package io.github.hejun.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * OAuth2授权服务
 *
 * @author HeJun
 */
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = {"io.github.hejun.cloud.auth.properties"})
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
}
