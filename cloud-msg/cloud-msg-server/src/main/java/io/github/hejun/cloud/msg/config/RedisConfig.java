package io.github.hejun.cloud.msg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis 连接配置
 *
 * @author HeJun
 */
@Slf4j
@Configuration
public class RedisConfig {

	@Bean
	public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(RedisSerializer.string());
		redisTemplate.setHashKeySerializer(RedisSerializer.string());
		redisTemplate.setValueSerializer(RedisSerializer.json());
		redisTemplate.setHashValueSerializer(RedisSerializer.json());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}
