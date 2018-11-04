/**
 * 
 */
package net.brilliance.config.secured;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author ducbq
 *
 */
@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setCharacterEncoding("UTF-8");

		String message = "";

		if (exception.getClass() == UsernameNotFoundException.class) {
			message = "cannot find a user";
		} else if (exception.getClass() == BadCredentialsException.class) {
			message = "check your password";
		}

		request.getRequestDispatcher(String.format("/errorPage?message=%s", message)).forward(request, response);
	}

}
