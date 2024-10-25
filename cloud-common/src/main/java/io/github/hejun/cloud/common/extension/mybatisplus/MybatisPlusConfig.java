package io.github.hejun.cloud.common.extension.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * MybatisPlus 自动配置
 *
 * @author HeJun
 */
@Slf4j
@Configuration
@ConfigurationPropertiesScan
@ConditionalOnClass(MybatisPlusInterceptor.class)
public class MybatisPlusConfig {

	@Bean
	@ConditionalOnMissingBean(MybatisPlusInterceptor.class)
	public MybatisPlusInterceptor mybatisPlusInterceptor(List<InnerInterceptor> interceptors) {
		String enablePlugins = Optional.ofNullable(interceptors).stream()
			.flatMap(List::stream).map(Object::getClass).map(Class::getSimpleName)
			.collect(Collectors.joining(", "));
		log.info("Enable MybatisPlus Interceptors: {}", enablePlugins);
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.setInterceptors(interceptors);
		return interceptor;
	}

	@Bean
	@ConditionalOnMissingBean(PaginationInnerInterceptor.class)
	public PaginationInnerInterceptor paginationInnerInterceptor(MybatisPlusConfigProperties properties) {
		PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor();
		if (Objects.nonNull(properties) && Objects.nonNull(properties.getDbType())) {
			interceptor.setDbType(properties.getDbType());
		}
		return interceptor;
	}

}
