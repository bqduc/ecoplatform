/**
 * 
 */
package net.brilliance.manager.security;

import javax.inject.Inject;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import net.brilliance.common.CommonUtility;
import net.brilliance.domain.model.CryptoAlgorithm;
import net.brilliance.exceptions.EcosysException;

/**
 * @author ducbq
 *
 */
@Service
public class SimpleEncryptionManager {
	private final static String SALT = "裴达克·奎-裴达克·奎";

	@Inject
	private BrillianceEncoder passwordEncoder;
	
	/*@Inject
	private BCryptPasswordEncoder bcryptEncoder;*/

	private String performEncode(String plainText){
		boolean encodeHashAsBase64 = true;
		int strength = 256;
    Md5PasswordEncoder md5PwdEncoder = new Md5PasswordEncoder();
	  md5PwdEncoder.setEncodeHashAsBase64(encodeHashAsBase64);

	  ShaPasswordEncoder shaPwdEncoder = new ShaPasswordEncoder(strength);
	  shaPwdEncoder.setEncodeHashAsBase64(encodeHashAsBase64);
	  String md5Encoded = md5PwdEncoder.encodePassword(plainText, SALT);
	  return shaPwdEncoder.encodePassword(md5Encoded, SALT);
	}

	public String basicEncode(String plainText) throws EcosysException{
		return encode(plainText, CryptoAlgorithm.BASIC);
	}

	public String encode(String plainText, CryptoAlgorithm cryptoAlgorithm) throws EcosysException{
		if (CryptoAlgorithm.PLAIN_TEXT.equals(cryptoAlgorithm))
			return plainText;

		return performEncode(plainText);
	}

	public String vpxEncode(String plainText){
		return passwordEncoder.encode(plainText);
	}

	public boolean comparePassword(String userPassword, String repositoryPassword, String algorithm) {
		if (CryptoAlgorithm.PLAIN_TEXT.getAlgorithm().equalsIgnoreCase(algorithm) || CommonUtility.isEmpty(algorithm)){
			return userPassword.equals(repositoryPassword);
		}

		//String encodedPwd = this.performEncode(userPassword);
		return repositoryPassword.equals(passwordEncoder.encode(userPassword));
	}
}
