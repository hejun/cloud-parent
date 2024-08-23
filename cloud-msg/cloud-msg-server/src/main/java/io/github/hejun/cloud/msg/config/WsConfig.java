package io.github.hejun.cloud.msg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket配置
 *
 * @author HeJun
 */
@Configuration
@EnableWebSocketMessageBroker
public class WsConfig implements WebSocketMessageBrokerConfigurer {

	public static final String KEY_DESTINATION_ALL = "/topic";
	public static final String KEY_DESTINATION_USER = "/user/";

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.initialize();
		registry
			.setUserDestinationPrefix(KEY_DESTINATION_USER)
			.enableSimpleBroker(KEY_DESTINATION_ALL, KEY_DESTINATION_USER)
			.setTaskScheduler(taskScheduler)
			.setHeartbeatValue(new long[]{10000, 10000});
	}
}
