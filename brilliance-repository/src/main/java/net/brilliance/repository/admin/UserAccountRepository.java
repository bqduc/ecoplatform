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
package net.brilliance.repository.admin;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.framework.repository.BrillianceRepository;

/**
 * Repository interface for the {@link UserAccount} entity. It contains methods for
 * regular <code>CRUD</code> operations
 * 
 * @author ducbq
 *
 */
@Repository
public interface UserAccountRepository extends BrillianceRepository<UserAccount, Long> {
	/**
	 * Retrieves a {@link UserAccount} entity from the underlying data store by its
	 * ResetKey
	 * 
	 * @param ssoId
	 *            the login
	 * @return a User entity
	 * @see UserAccount#getResetKey()
	 */
	UserAccount findBySsoId(String ssoId);

	/**
	 * Retrieves a {@link UserAccount} entity from the underlying datastore by its
	 * Email
	 * 
	 * @param email
	 *            the User's email
	 * @return a User entity
	 * @see UserAccount#getEmail()
	 */
	UserAccount findByEmail(String email);

	/**
	 * Retrieves the number of entities from the underlying data store by its
	 * login
	 * 
	 * @param login
	 *            the user name
	 * @return a User entity
	 * @see UserAccount#getLogin()
	 */
	Long countBySsoId(String login);
}
