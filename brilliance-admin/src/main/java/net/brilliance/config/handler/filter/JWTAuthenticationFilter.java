/**
 * 
 */
package net.brilliance.config.handler.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
/**
 * @author bdq1hc
 * https://auth0.com/blog/securing-spring-boot-with-jwts/
 *
 */
public class JWTAuthenticationFilter extends GenericFilterBean {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest)request);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request,response);
  }
}
