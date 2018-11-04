package net.brilliance.controller.filter;
/**
 * 
 *//*
package net.vpx.web.filter;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import net.vpx.common.CommonUtility;
import net.vpx.config.Constants;
import net.vpx.domain.entity.security.User;
import net.vpx.exceptions.AuthException;
import net.vpx.service.auth.AuthenticationServiceManager;

*//**
 * @author ducbq
 *
 *//*
@Component
public class CustomFilter extends GenericFilterBean {
	@Inject 
	private AuthenticationServiceManager authenticationServiceManager;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = null;
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
    }

		chain.doFilter(request, response);
	}
}
*/