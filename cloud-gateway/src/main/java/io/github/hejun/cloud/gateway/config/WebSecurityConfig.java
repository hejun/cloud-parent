package io.github.hejun.cloud.gateway.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * Gateway安全配置
 *
 * @author HeJun
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

	@Bean
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	public SecurityWebFilterChain webSecurityFilterChain(ServerHttpSecurity http,
														 GlobalCorsProperties globalCorsProperties) {
		http
			.authorizeExchange(authorizeExchange ->
				authorizeExchange
					.pathMatchers("/auth/oauth2/**").permitAll()
					// 需要对 WebSocket 放行,由 Msg 服务握手后自行验证
					.pathMatchers("/msg/ws/**").permitAll()
					.anyExchange().authenticated()
			)
			.cors(cors -> cors.configurationSource(this.buildConfigSource(globalCorsProperties)))
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.oauth2ResourceServer(oauth2ResourceServer ->
				oauth2ResourceServer.opaqueToken(Customizer.withDefaults())
			);
		return http.build();
	}

	private CorsConfigurationSource buildConfigSource(GlobalCorsProperties globalCorsProperties) {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.setCorsConfigurations(globalCorsProperties.getCorsConfigurations());
		return source;
	}

}
