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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.domain.entity.admin.Authority;
import net.brilliance.domain.model.AuthorityGroup;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.repository.auth.AuthorityRepository;

/**
 * Department service implementation. Provides implementation of the department
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class AuthorityManager extends AbstractServiceManager<Authority, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7519340001108353483L;
	@Inject
	private AuthorityRepository repository;

	@Override
	protected JBaseRepository<Authority, Long> getRepository() {
		return this.repository;
	}

	public List<Authority> createDummyObjects() {
		List<Authority> fetchedObjects = new ArrayList<>();
		fetchedObjects.add(Authority.builder().name(AuthorityGroup.RoleAdmin.getCode()).displayName("Administrator authority").build());
		fetchedObjects.add(Authority.builder().name(AuthorityGroup.RoleUser.getCode()).displayName("Common user authority").build());
		fetchedObjects.add(Authority.builder().name(AuthorityGroup.RoleClient.getCode()).displayName("Client authority").build());
		fetchedObjects.add(Authority.builder().name(AuthorityGroup.RoleAnonymous.getCode()).displayName("Anonymous authority").build());
		for (Authority authority :fetchedObjects){
			this.save(authority);
		}
		return fetchedObjects;
	}

	public Authority getByName(String name) {
		return repository.findByName(name);
	}
}
