/**
 * 
 */
package net.brilliance.config.secured;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import net.brilliance.config.base.ConfigurationConstants;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.service.api.admin.UserAuthenticationService;
import net.brilliance.util.UrlUtil;

/**
 * @author ducbq
 *
 */
@Component
public class CustomLoginSuccessfulHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Inject
	private UserAuthenticationService userAuthenticationService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		User authUser = (User) authentication.getPrincipal();
		UserAccount userProfile = userAuthenticationService.getOne(authUser.getUsername());
		request.getSession().setAttribute("userProfile", userProfile);
		////////////////////
		response.setStatus(HttpStatus.OK.value());
		response.setCharacterEncoding("UTF-8");
		////////////////////
		String ctoken = (String) request.getSession().getAttribute(ConfigurationConstants.CSRF_TOKEN);
		DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
		if (defaultSavedRequest != null && ctoken != null) {
			String requestUrl = defaultSavedRequest.getRequestURL() + "?" + defaultSavedRequest.getQueryString();
			requestUrl = UrlUtil.addParamToURL(requestUrl, ConfigurationConstants.CSRF_TOKEN, ctoken, true);
			getRedirectStrategy().sendRedirect(request, response, requestUrl);
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
			try {
				handle(request, response, authentication);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			redirectStrategy.sendRedirect(request, response, targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(Authentication authentication) {
		boolean isUser = false;
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
				isUser = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}

		if (isUser) {
			return "/homepage.html";
		} else if (isAdmin) {
			return "/";//"/console.html";
		} else {
			throw new IllegalStateException();
		}
	}

}
