package io.github.hejun.cloud.auth.security.handler;

import io.github.hejun.cloud.common.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.DefaultOAuth2AccessTokenResponseMapConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.oidc.web.OidcUserInfoEndpointFilter;
import org.springframework.security.oauth2.server.authorization.web.OAuth2TokenEndpointFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * 自定义返回token
 *
 * @author HeJun
 * @see OAuth2TokenEndpointFilter
 * @see OidcUserInfoEndpointFilter
 */
public class SuccessResponseWrapperHandler implements AuthenticationSuccessHandler {

	private final GenericHttpMessageConverter<Object> accessTokenHttpResponseConverter =
		new MappingJackson2HttpMessageConverter();
	private final Converter<OAuth2AccessTokenResponse, Map<String, Object>> accessTokenResponseParametersConverter =
		new DefaultOAuth2AccessTokenResponseMapConverter();
	private final Converter<OidcUserInfo, Map<String, Object>> userInfoParametersConverter = OidcUserInfo::getClaims;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException {
		Map<String, Object> data = Map.of();
		if (authentication instanceof OAuth2AccessTokenAuthenticationToken accessTokenAuthentication) {
			OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
			OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
			Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

			OAuth2AccessTokenResponse.Builder builder =
				OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
					.tokenType(accessToken.getTokenType())
					.scopes(accessToken.getScopes());
			if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
				builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
			}
			if (refreshToken != null) {
				builder.refreshToken(refreshToken.getTokenValue());
			}
			if (!CollectionUtils.isEmpty(additionalParameters)) {
				builder.additionalParameters(additionalParameters);
			}
			OAuth2AccessTokenResponse accessTokenResponse = builder.build();
			data = this.accessTokenResponseParametersConverter.convert(accessTokenResponse);
		}
		if (authentication instanceof OidcUserInfoAuthenticationToken userInfoAuthenticationToken) {
			data = this.userInfoParametersConverter.convert(userInfoAuthenticationToken.getUserInfo());
		}
		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
		httpResponse.setStatusCode(HttpStatus.OK);
		this.accessTokenHttpResponseConverter.write(Result.SUCCESS(data), null, httpResponse);
	}
}
