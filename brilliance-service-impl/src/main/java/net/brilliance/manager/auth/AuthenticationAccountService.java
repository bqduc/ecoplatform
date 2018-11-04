/*
* Copyright 2017, Bui Quy Duc
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.brilliance.manager.auth;

import java.util.List;
import java.util.Optional;

import net.brilliance.domain.entity.admin.UserAccount;

/**
 * Contains service methods for the management of User accounts
 * 
 * @author ducbq
 *
 */
public interface AuthenticationAccountService {
	/**
	 * This is a utility method to drop all accounts in the database
	 */
	public void deleteAllAccounts();

	/**
	 * Detaches a User entity from the current persistence context
	 * 
	 * @param user
	 *            the User entity to detach
	 */
	public void deleteAccount(UserAccount user);

	/**
	 * This method gets all the users from sql database
	 * 
	 * @return List<User> the list of all users in the database
	 */
	public List<UserAccount> findAll();

	/**
	 * This brings one unique user from the database
	 * 
	 * @param id
	 *            the user identfier
	 * @return {@code Optional<User>} the optional user object. This is never
	 *         null
	 */
	public Optional<UserAccount> findAccountById(Long id);

	/**
	 * This brings one unique user from the database
	 * 
	 * @param resetKey
	 *            the reset key used to search
	 * @return {@code Optional<User>} the optional user object. this is never
	 *         null
	 */
	public Optional<UserAccount> findAccountByResetKey(String resetKey);

	/**
	 * This brings one unique user from the database by email
	 * 
	 * @param email
	 *            the email of the user to search
	 * @return {@code Optional<User>} the optional user object. this is never
	 *         null
	 */
	public Optional<UserAccount> findAccountByEmail(String email);

	/**
	 * This returns one unique user from the database by username
	 * 
	 * @param login
	 *            the username of the user to search by
	 * @return {@code Optional<User>} the optional user object. this is never
	 *         null
	 */
	public Optional<UserAccount> findAccountByLogin(String login);

	/**
	 * Facade method to create or update accounts
	 * 
	 * @param user
	 *            the Account to be created or updated
	 */
	public void save(UserAccount user);
}
