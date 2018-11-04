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
package net.brilliance.repository.auth;

import java.util.Optional;

import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.framework.repository.IBaseRepository;

/**
 * Repository interface for the {@link UserAccount} entity. It contains methods for
 * regular <code>CRUD</code> operations
 * 
 * @author ducbq
 *
 */
public interface UserRepository extends IBaseRepository<UserAccount, Long> {

	/**
	 * Retrieves a {@link UserAccount} entity from the underlying datastore by its
	 * ResetKey
	 * 
	 * @param resetKey
	 *            the resetKey
	 * @return a User entity
	 * @see UserAccount#getResetKey()
	 */
	public Optional<UserAccount> findOneByResetKey(String resetKey);

	/**
	 * Retrieves a {@link UserAccount} entity from the underlying datastore by its
	 * Email
	 * 
	 * @param email
	 *            the User's email
	 * @return a User entity
	 * @see UserAccount#getEmail()
	 */
	public Optional<UserAccount> findOneByEmail(String email);

	/**
	 * Retrieves a {@link UserAccount} entity from the underlying datastore by its
	 * login
	 * 
	 * @param login
	 *            the username
	 * @return a User entity
	 * @see UserAccount#getLogin()
	 */
	public Optional<UserAccount> findOneByLogin(String login);

}
