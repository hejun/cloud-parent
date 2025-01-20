package io.github.hejun.cloud.msg.config;

import io.github.hejun.cloud.msg.enums.WebSocketConstants;
import io.github.hejun.cloud.msg.interceptors.WebSocketAuthenticationChannelInterceptor;
import io.github.hejun.cloud.msg.listener.WebSocketMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 配置
 *
 * @author HeJun
 */
@Slf4j
@Configuration
@EnableWebSocketSecurity
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final WebSocketAuthenticationChannelInterceptor authenticationChannelInterceptor;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
			.addEndpoint(WebSocketConstants.WEBSOCKET_ENDPOINT)
			.setAllowedOriginPatterns("*");
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry
			.setUserDestinationPrefix(WebSocketConstants.DESTINATION_USER)
			.enableSimpleBroker(WebSocketConstants.DESTINATION_ALL, WebSocketConstants.DESTINATION_USER);
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(authenticationChannelInterceptor);
	}

	@Bean
	public AuthorizationManager<Message<?>> authorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
		messages
			.simpTypeMatchers(SimpMessageType.DISCONNECT).permitAll()
			.anyMessage().authenticated();
		return messages.build();
	}

	/**
	 * 关闭 Csrf
	 *
	 * @return CsrfInterceptor
	 */
	@Bean
	public ChannelInterceptor csrfChannelInterceptor() {
		return new ChannelInterceptor() {
		};
	}

	@Bean
	public RedisMessageListenerContainer wsRedisMessageListenerContainer(RedisConnectionFactory connectionFactory,
																		 WebSocketMessageListener webSocketMessageListener) {
		RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
		listenerContainer.setConnectionFactory(connectionFactory);
		listenerContainer
			.addMessageListener(webSocketMessageListener, new ChannelTopic(WebSocketConstants.WS_MSG_DISTRIBUTE_CHANNEL));
		return listenerContainer;
	}

}
