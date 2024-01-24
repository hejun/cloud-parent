package io.github.hejun.cloud.auth.controller;

import io.github.hejun.cloud.common.exception.StatefulException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.*;

/**
 * 自定义页面Controller
 *
 * @author HeJun
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomPageController {

	private final RegisteredClientRepository registeredClientRepository;
	private final OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService;

	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object attribute = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			if (attribute instanceof AuthenticationException ex) {
				request.setAttribute("error", HtmlUtils.htmlEscape(ex.getMessage()));
			}
		}
		return "login";
	}

	@GetMapping("/oauth2/consent")
	public String authorize(Model model, Principal principal,
							@RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
							@RequestParam(OAuth2ParameterNames.SCOPE) String scope,
							@RequestParam(OAuth2ParameterNames.STATE) String state) {
		RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
		if (registeredClient == null) {
			throw new StatefulException(400, "客户端不存在");
		}
		OAuth2AuthorizationConsent authorizationConsent = this.oAuth2AuthorizationConsentService
			.findById(registeredClient.getId(), principal.getName());

		Set<String> previouslyApprovedScopes = Optional.ofNullable(authorizationConsent)
			.map(OAuth2AuthorizationConsent::getScopes)
			.orElse(new HashSet<>(List.of(OidcScopes.OPENID)));

		Set<String> requestedScopes = new HashSet<>();
		Set<String> authorizedScopes = new HashSet<>();
		for (String requestedScope : StringUtils.delimitedListToStringArray(scope, " ")) {
			if (previouslyApprovedScopes.contains(requestedScope)) {
				authorizedScopes.add(requestedScope);
			} else {
				requestedScopes.add(requestedScope);
			}
		}

		model.addAttribute("client", registeredClient);
		model.addAttribute("principal", principal);
		model.addAttribute("requestedScopes", requestedScopes);
		model.addAttribute("authorizedScopes", authorizedScopes);
		model.addAttribute("state", state);
		return "consent";
	}
}
