package io.github.hejun.cloud.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * CORS 跨域配置
 *
 * @author HeJun
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.auth.cors")
public class CorsProperties {

	private List<String> allowedOrigins;
	private List<String> allowedMethods;
	private List<String> allowedHeaders;
	private Boolean allowCredentials;
	private Long maxAge;

	public CorsConfigurationSource build() {
		if (CollectionUtils.isEmpty(allowedOrigins)
			&& CollectionUtils.isEmpty(allowedMethods)
			&& CollectionUtils.isEmpty(allowedHeaders)) {
			return null;
		}
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		if (!CollectionUtils.isEmpty(allowedOrigins)) {
			corsConfiguration.setAllowedOrigins(allowedOrigins);
		}
		if (!CollectionUtils.isEmpty(allowedMethods)) {
			corsConfiguration.setAllowedMethods(allowedMethods);
		}
		if (!CollectionUtils.isEmpty(allowedHeaders)) {
			corsConfiguration.setAllowedHeaders(allowedHeaders);
		}
		if (allowCredentials != null) {
			corsConfiguration.setAllowCredentials(allowCredentials);
		}
		if (maxAge != null) {
			corsConfiguration.setMaxAge(maxAge);
		} else {
			corsConfiguration.setMaxAge(1800L);
		}
		UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
		corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return corsConfigurationSource;
	}
}
