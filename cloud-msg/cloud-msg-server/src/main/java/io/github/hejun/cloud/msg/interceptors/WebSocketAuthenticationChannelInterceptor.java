package io.github.hejun.cloud.msg.interceptors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WebSocket认证Filter
 *
 * @author HeJun
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSocketAuthenticationChannelInterceptor implements ChannelInterceptor {

	private static final Pattern authorizationPattern = Pattern
		.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);

	private final ApplicationContext context;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (accessor != null) {
			String authorizationHeader = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
			if (StringUtils.hasText(authorizationHeader)) {
				Matcher authorizationMatcher = authorizationPattern.matcher(authorizationHeader);
				if (authorizationMatcher.matches()) {
					String token = authorizationMatcher.group("token");
					OpaqueTokenIntrospector introspector = this.context.getBean(OpaqueTokenIntrospector.class);
					OAuth2AuthenticatedPrincipal authenticatedPrincipal = introspector.introspect(token);
					Instant iat = authenticatedPrincipal.getAttribute(OAuth2TokenIntrospectionClaimNames.IAT);
					Instant exp = authenticatedPrincipal.getAttribute(OAuth2TokenIntrospectionClaimNames.EXP);
					OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, token,
						iat, exp);
					Authentication authentication = new BearerTokenAuthentication(authenticatedPrincipal, accessToken,
						authenticatedPrincipal.getAuthorities());
					accessor.setUser(authentication);
				}
			}
		}
		return message;
	}
}
