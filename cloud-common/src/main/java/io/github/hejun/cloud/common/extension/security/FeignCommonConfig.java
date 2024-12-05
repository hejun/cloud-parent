package io.github.hejun.cloud.common.extension.security;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Feign 自动配置
 *
 * @author HeJun
 */
@Slf4j
@Configuration
@ConfigurationPropertiesScan
@ConditionalOnClass(Feign.class)
public class FeignCommonConfig {

    @Bean
    @ConditionalOnClass({HttpServletRequest.class, RequestInterceptor.class, RequestTemplate.class})
    public RequestInterceptor requestInterceptor() {
        return template -> {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(authorizationHeader)) {
                template.header(HttpHeaders.AUTHORIZATION, authorizationHeader);
            }
        };
    }

}
