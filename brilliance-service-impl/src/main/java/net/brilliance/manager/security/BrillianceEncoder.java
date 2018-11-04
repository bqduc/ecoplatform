/**
 * 
 */
package net.brilliance.manager.security;

import javax.inject.Inject;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ducbq
 *
 */
//@Named("vpxEncoder")
@Component
public class BrillianceEncoder implements PasswordEncoder {
	private final static String PASSWORD_ENCODER_SALT = "裴达克·奎-裴达克·奎";

	@Inject
	private PasswordEncoder passwordEncoder;

	@Override
	public String encode(CharSequence rawPassword) {
		return new Md5PasswordEncoder().encodePassword(rawPassword.toString(), PASSWORD_ENCODER_SALT);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return new Md5PasswordEncoder().encodePassword(rawPassword.toString(), PASSWORD_ENCODER_SALT).equals(encodedPassword);
	}
}
