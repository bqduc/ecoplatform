/**
 * 
 */
package net.brilliance.config.secured;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import net.brilliance.exceptions.UserAuthenticationException;
import net.brilliance.exceptions.UserAuthenticationException.AuthenticationCode;

/**
 * @author ducbq
 *
 */
@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setCharacterEncoding("UTF-8");

		UserAuthenticationException userAuthenticationException = null;
		String messageLabel = "login.error";

		if (exception.getCause() instanceof UserAuthenticationException){
			userAuthenticationException = (UserAuthenticationException)exception.getCause();
			if (AuthenticationCode.INVALID_USER.equals(userAuthenticationException.getErrorId())){
				messageLabel = "login.invalid.ssoId";
			} else if (AuthenticationCode.INVALID_PASSWORD.equals(userAuthenticationException.getErrorId())){
				messageLabel = "login.invalid.secretKey";				
			} else if (AuthenticationCode.ACCOUNT_LOCKED.equals(userAuthenticationException.getErrorId())){
				messageLabel = "login.invalid.accountLocked";				
			} else if (AuthenticationCode.ACCOUNT_INACTIVE.equals(userAuthenticationException.getErrorId())){
				messageLabel = "login.invalid.accountInactive";				
			}
		}
		this.getRedirectStrategy().sendRedirect(request, response, response.encodeRedirectURL(String.format("/login?error=true&message=%s", messageLabel)));
	}

}
