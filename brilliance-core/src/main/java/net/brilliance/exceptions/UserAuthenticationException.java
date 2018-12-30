/**
 * 
 */
package net.brilliance.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author ducbq
 *
 */
public class UserAuthenticationException extends AuthenticationException {
	public enum AuthenticationCode {
		INVALID_USER,
		INVALID_PASSWORD,
		ACCOUNT_INACTIVE,
		ACCOUNT_LOCKED,
		INVALID_PERMISSION
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -2145491649369081533L;

	private AuthenticationCode errorId;

	public UserAuthenticationException(String msg) {
		super(msg);
	}

	public UserAuthenticationException(AuthenticationCode errorId, String msg) {
		super(msg);
		this.errorId = errorId;
	}

	public AuthenticationCode getErrorId() {
		return errorId;
	}

	public void setErrorId(AuthenticationCode errorId) {
		this.errorId = errorId;
	}
}
