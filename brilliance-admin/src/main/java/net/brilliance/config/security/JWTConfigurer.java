/**
 * 
 */
package net.brilliance.config.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.brilliance.config.handler.filter.JWTFilter;

/**
 * @author bdq1hc
 *
 */
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	public static final String AUTHORIZATION_HEADER = "Authorization";

  private TokenProvider tokenProvider;

  public JWTConfigurer(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

	@Override
	public void configure(HttpSecurity builder) throws Exception {
    JWTFilter customFilter = new JWTFilter(tokenProvider);
    builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
