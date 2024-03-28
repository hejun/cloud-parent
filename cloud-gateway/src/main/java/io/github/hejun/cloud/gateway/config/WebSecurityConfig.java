package io.github.hejun.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Web安全配置
 *
 * @author HeJun
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityWebFilterChain chain(ServerHttpSecurity http) {
		http
			.authorizeExchange(exchange -> exchange.anyExchange().authenticated())
			.oauth2Client(Customizer.withDefaults());
		return http.build();
	}
}
