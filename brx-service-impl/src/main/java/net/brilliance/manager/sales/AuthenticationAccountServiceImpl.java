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
package net.brilliance.manager.sales;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.manager.auth.AuthenticationAccountService;
import net.brilliance.repository.auth.UserRepository;

/**
 * AccountService implementation. Provides implementation of the Account Facade
 * 
 * @author Julius Krah
 *
 */
@Service
@Transactional
public class AuthenticationAccountServiceImpl implements AuthenticationAccountService {
	private Logger log = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private Provider<UserRepository> userRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAllAccounts() {
		log.info("Deleting all account information from database..");
		userRepository.get().deleteAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(UserAccount user) {
		log.info("Saving user: {} into the database...", user);
		userRepository.get().save(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<UserAccount> findAll() {
		log.debug("Retrieving all user information from the database...");
		return userRepository.get().findAll();
	}

	@Override
	public void deleteAccount(UserAccount user) {
		log.info("Deleting user: {} from the database...", user);
		userRepository.get().delete(user);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserAccount> findAccountById(Long id) {
		log.debug("Retrieving user with id {} from the database...", id);
		return userRepository.get().findOneById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserAccount> findAccountByResetKey(String resetKey) {
		log.debug("Retrieving user with reset key {} from database...", resetKey);
		return userRepository.get().findOneByResetKey(resetKey);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserAccount> findAccountByEmail(String email) {
		log.debug("Retrieving user with email {} from database...", email);
		return userRepository.get().findOneByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserAccount> findAccountByLogin(String login) {
		log.debug("Retrieving user with username {} from database...", login);
		return userRepository.get().findOneByLogin(login);
	}

}
