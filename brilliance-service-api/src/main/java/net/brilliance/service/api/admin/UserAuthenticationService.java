package net.brilliance.service.api.admin;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.exceptions.AuthenticationException;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface UserAuthenticationService extends GenericService<UserAccount, Long>, UserDetailsService{
    /**
     * Finds the user with the provided name.
     * 
     * @param username The user name
     * @return The user
     * @throws ObjectNotFoundException If no such user exists.
     */
	UserAccount getOne(String username) throws ObjectNotFoundException;

	/**
	 * Create a new user with the supplied details.
	 */
	void createUser(UserAccount user);

	/**
	 * Update the specified user.
	 */
	void updateUser(UserAccount user);

	/**
	 * Remove the user with the given login name from the system.
	 */
	void deleteUser(String username);

	/**
	 * Modify the current user's password. This should change the user's password in the
	 * persistent user repository (database, LDAP etc).
	 *
	 * @param oldPassword current password (for re-authentication if required)
	 * @param newPassword the password to change to
	 */
	void changePassword(String oldPassword, String newPassword);

	/**
	 * Check if a user with the supplied login name exists in the system.
	 */
	boolean userExists(String username);

	int countByLogin(String login);

	UserDetails loadUserByEmail(final String email);
	UserAccount save(UserAccount user);
	UserAccount authenticate(String loginToken, String password) throws AuthenticationException;
	UserAccount getUser(String userToken) throws AuthenticationException;
	UserAccount confirm(String confirmedEmail) throws AuthenticationException;
	void initializeMasterData() throws AuthenticationException;

}
