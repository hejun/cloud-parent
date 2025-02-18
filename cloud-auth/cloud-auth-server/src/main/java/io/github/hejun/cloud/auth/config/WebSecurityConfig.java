package io.github.hejun.cloud.auth.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Web安全配置
 *
 * @author HeJun
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize ->
				authorize.anyRequest().authenticated()
			)
			.cors(Customizer.withDefaults())
			.csrf(CsrfConfigurer::disable)
			.formLogin(formLogin ->
				formLogin.loginPage("/login").permitAll()
			)
			.oauth2ResourceServer(resourceServer ->
				resourceServer.opaqueToken(Customizer.withDefaults())
			);
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/style/**", "/script/**", "/favicon.ico", "/robots.txt");
	}

}
