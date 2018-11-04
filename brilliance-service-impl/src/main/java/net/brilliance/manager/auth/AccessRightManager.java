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

import net.brilliance.domain.entity.security.AccessRight;
import net.brilliance.domain.model.DefaultAccessRightAction;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.repository.auth.AccessRightRepository;

/**
 * Access right service implementation. Provides implementation of the department
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class AccessRightManager extends AbstractServiceManager<AccessRight, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7295321971712879286L;
	@Inject
	private AccessRightRepository repository;

	@Override
	protected JBaseRepository<AccessRight, Long> getRepository() {
		return this.repository;
	}

	public List<AccessRight> initializeMasterData() {
		List<AccessRight> fetchedObjects = new ArrayList<>();
		for (DefaultAccessRightAction defaultAccessRightAction :DefaultAccessRightAction.values()){
			if (null==repository.findByName(defaultAccessRightAction.getAction())){
				fetchedObjects.add(AccessRight.createInstance(defaultAccessRightAction.getAction(), defaultAccessRightAction.getDescription()));
			}
		}

		/*if (!repository.findByName(DefaultAccessRightAction.View.getAction()).isPresent()){
			fetchedObjects.add(AccessRight.createInstance(DefaultAccessRightAction.View.getAction(), "View objects. "));
		}

		if (!repository.findByName(DefaultAccessRightAction.Create.getAction()).isPresent()){
			fetchedObjects.add(AccessRight.createInstance(DefaultAccessRightAction.Create.getAction(), "Create new object. "));
		}

		if (!repository.findByName(DefaultAccessRightAction.Modify.getAction()).isPresent()){
			fetchedObjects.add(AccessRight.createInstance(DefaultAccessRightAction.Modify.getAction(), "Modify the existing object. "));
		}

		if (!repository.findByName(DefaultAccessRightAction.Delete.getAction()).isPresent()){
			fetchedObjects.add(AccessRight.createInstance(DefaultAccessRightAction.Delete.getAction(), "Delete the existing object. "));
		}*/

		for (AccessRight entity :fetchedObjects){
			this.save(entity);
		}
		return fetchedObjects;
	}

	public AccessRight getByName(String name) {
		return repository.findByName(name);
	}
}
