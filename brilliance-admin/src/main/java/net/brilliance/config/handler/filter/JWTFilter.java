/**
 * 
 */
package net.brilliance.config.handler.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import net.brilliance.config.security.TokenProvider;
import net.brilliance.manager.auth.AuthenticationServiceManager;

/**
 * @author ducbq
 *
 */
@Component
public class JWTFilter extends GenericFilterBean {
	@Inject 
	private AuthenticationServiceManager authenticationServiceManager;

	private TokenProvider tokenProvider;

	public JWTFilter(TokenProvider tokenProvider) {		
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*HttpServletRequest httpRequest = null;
		if (request instanceof HttpServletRequest){
			httpRequest = (HttpServletRequest) request;
		}

		if (!httpRequest.getServletPath().startsWith(CommonConstants.REST_API)) {
			chain.doFilter(request, response);
			return;
		}

		String authorizationHeader = httpRequest.getHeader(CommonConstants.HEADER_AUTHORIZED);
		if (CommonUtility.isEmpty(authorizationHeader)){
			((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			//chain.doFilter(request, response);
			return;
		}

		String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
    String credentials = new String(Base64.decodeBase64(base64Credentials), Charset.forName("UTF-8"));
    // credentials = username:password
    final String[] values = credentials.split(":",2);
    User authenticated = null;
    try {
    	authenticated = authenticationServiceManager.authenticate(values[0], values[1]);
    } catch (AuthException e) {
			e.printStackTrace();
		}

    if (null==authenticated){
			((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }*/

		chain.doFilter(request, response);
	}
}
