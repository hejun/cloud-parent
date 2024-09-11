package io.github.hejun.cloud.msg.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * MyBatisPlus配置
 *
 * @author HeJun
 */
@Configuration
public class MybatisPlusConfig {

	@Bean
	public PaginationInnerInterceptor paginationInnerInterceptor() {
		return new PaginationInnerInterceptor();
	}

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(List<InnerInterceptor> interceptors) {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.setInterceptors(interceptors);
		return interceptor;
	}

}
