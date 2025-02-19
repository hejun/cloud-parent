package io.github.hejun.cloud.auth.controller;

import io.github.hejun.cloud.common.exception.StatefulRuntimeException;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 页面跳转 Controller
 *
 * @author HeJun
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PageController {

	private final RegisteredClientRepository registeredClientRepository;
	private final OAuth2AuthorizationConsentService authorizationConsentService;

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
		Set<String> scopesToApprove = new HashSet<>();
		Set<String> previouslyApprovedScopes = new HashSet<>();

		RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
		if (registeredClient == null) {
			throw new StatefulRuntimeException(404, "Client not exists");
		}

		OAuth2AuthorizationConsent authorizationConsent =
			this.authorizationConsentService.findById(registeredClient.getId(), principal.getName());

		Set<String> authorizedScopes;
		if (authorizationConsent != null) {
			authorizedScopes = authorizationConsent.getScopes();
		} else {
			authorizedScopes = Collections.emptySet();
		}

		for (String requestedScope : StringUtils.delimitedListToStringArray(scope, " ")) {
			if (OidcScopes.OPENID.equals(requestedScope)) {
				continue;
			}
			if (authorizedScopes.contains(requestedScope)) {
				previouslyApprovedScopes.add(requestedScope);
			} else {
				scopesToApprove.add(requestedScope);
			}
		}

		model.addAttribute("clientId", clientId);
		model.addAttribute("clientName", StringUtils.hasText(clientId) ? registeredClient.getClientName() : clientId);
		model.addAttribute("principalName", principal.getName());
		model.addAttribute("previouslyApprovedScopes", previouslyApprovedScopes);
		model.addAttribute("scopes", scopesToApprove);
		model.addAttribute("state", state);
		return "consent";
	}

}
