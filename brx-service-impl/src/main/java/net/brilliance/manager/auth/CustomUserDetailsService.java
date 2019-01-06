package net.brilliance.manager.auth;
/**
 * 
 *//*
package net.powertrain.manager.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import net.powertrain.domain.entity.security.UserProfile;
import net.powertrain.exceptions.AuthenticationException;

*//**
 * @author BDQ1HC
 *
 *//*
public interface CustomUserDetailsService extends UserDetailsService {
	UserDetails loadUserByEmail(final String email);
	UserProfile save(UserProfile user);
	UserProfile authenticate(String loginToken, String password) throws AuthenticationException;
	UserProfile getUser(String userToken) throws AuthenticationException;
	UserProfile confirm(String confirmedEmail) throws AuthenticationException;
	void setupDefaultMasterData() throws AuthenticationException;
}
*/