package io.github.hejun.cloud.msg.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hejun.cloud.common.vo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;

/**
 * WebSocket消息认证拦截器
 *
 * @author HeJun
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WsMsgAuthChannelInterceptor implements ChannelInterceptor {

	private final ObjectMapper objectMapper;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
			String authorizationHeader = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
			log.debug("WebSocket开始连接,AuthorizationHeader:{}", authorizationHeader);
			if (StringUtils.hasText(authorizationHeader)) {
				// TODO 认证实现
				Principal principal = () -> authorizationHeader;
				accessor.setUser(principal);
			} else {
				try {
					String errorMsg = objectMapper
						.writeValueAsString(Result.ERROR(400, "Authorization is missing"));
					throw new MessagingException(errorMsg);
				} catch (JsonProcessingException e) {
					log.error("Write Json Error", e);
					throw new MessagingException(e.getMessage());
				}
			}
		}
		return message;
	}

}
