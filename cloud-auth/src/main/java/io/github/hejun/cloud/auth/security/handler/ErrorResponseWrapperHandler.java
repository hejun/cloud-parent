package io.github.hejun.cloud.auth.security.handler;

import io.github.hejun.cloud.common.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ErrorAuthenticationFailureHandler;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义返回token
 *
 * @author HeJun
 * @see BearerTokenAuthenticationEntryPoint
 * @see OAuth2ErrorAuthenticationFailureHandler
 */
public class ErrorResponseWrapperHandler implements AuthenticationEntryPoint, AuthenticationFailureHandler {

	private final GenericHttpMessageConverter<Object> errorHttpResponseConverter =
		new MappingJackson2HttpMessageConverter();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException exception) throws IOException {
		this.wrapperException(response, exception);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException {
		this.wrapperException(response, exception);
	}

	private void wrapperException(HttpServletResponse response, AuthenticationException exception) throws IOException {
		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
		httpResponse.setStatusCode(HttpStatus.OK);
		Result<Map<String, String>> result = Result.ERROR(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
		errorHttpResponseConverter.write(result, null, httpResponse);
	}
}
