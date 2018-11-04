package net.brilliance.manager.security;
/**
 * 
 *//*
package net.vpx.manager.security;

import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

*//**
 * @author ducbq
 *
 *//*
@Component
public class VpxPasswordEncoder implements PasswordEncoder {

	@Inject
	private SimpleEncryptionManager simpleEncryptor;

	@Override
	public String encode(CharSequence rawPassword) {
		return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(16));
		//return simpleEncryptor.encode(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return BCrypt.checkpw(rawPassword.toString(), encodedPassword); 
	}

}
*/