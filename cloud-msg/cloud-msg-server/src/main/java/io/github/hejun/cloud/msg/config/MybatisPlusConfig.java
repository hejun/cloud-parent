package io.github.hejun.cloud.msg.config;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.ReflectionUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * MyBatisPlus配置
 *
 * @author HeJun
 */
@Configuration
public class MybatisPlusConfig {

	private static final Logger log = LoggerFactory.getLogger(MybatisPlusConfig.class);

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

	@Bean
	public InnerInterceptor customAnnotationInterceptor() {
		return new InnerInterceptor() {
			@Override
			public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
				if (SqlCommandType.INSERT != ms.getSqlCommandType() && SqlCommandType.UPDATE != ms.getSqlCommandType()) {
					return;
				}
				TableInfo tableInfo;
				if (parameter instanceof Map<?, ?> map) {
					tableInfo = TableInfoHelper.getTableInfo(map.get(Constants.ENTITY).getClass());
				} else {
					tableInfo = TableInfoHelper.getTableInfo(parameter.getClass());
				}
				for (TableFieldInfo fieldInfo : tableInfo.getFieldList()) {
					if (SqlCommandType.INSERT == ms.getSqlCommandType()) {
						if (fieldInfo.getField().isAnnotationPresent(CreatedDate.class)) {
							ReflectionUtils.setField(fieldInfo.getField(), parameter, new Date());
						}
					}
					if (SqlCommandType.UPDATE == ms.getSqlCommandType()) {
						if (fieldInfo.getField().isAnnotationPresent(LastModifiedDate.class)) {
							ReflectionUtils.setField(fieldInfo.getField(), parameter, new Date());
						}
					}
				}
			}
		};
	}

}
