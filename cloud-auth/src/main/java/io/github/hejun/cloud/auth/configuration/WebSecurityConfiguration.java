package io.github.hejun.cloud.auth.configuration;

import io.github.hejun.cloud.auth.properties.CorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Web安全配置
 *
 * @author HeJun
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain webSecurityFilterChain(HttpSecurity http,
													  CorsProperties corsProperties) throws Exception {
		http
			.authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
			.cors(cors -> cors.configurationSource(corsProperties.build()))
			.formLogin(login -> login.loginPage("/login").permitAll())
			.logout(logout -> {
				logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.name()));
				((SimpleUrlLogoutSuccessHandler) logout.getLogoutSuccessHandler()).setTargetUrlParameter("redirect_uri");
			});
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/style/**", "/script/**", "/favicon.ico");
	}
}
