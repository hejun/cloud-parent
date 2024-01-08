package io.github.hejun.cloud.gateway.configuration;

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
public class WebFluxSecurityConfiguration {

	@Bean
	public SecurityWebFilterChain webSecurityFilterChain(ServerHttpSecurity http) {
		http
			.authorizeExchange(exchange -> exchange.anyExchange().authenticated())
			.oauth2Login(Customizer.withDefaults());
		return http.build();
	}
}
