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

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.domain.entity.security.Module;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.repository.auth.ModuleRepository;

/**
 * Module service implementation. 
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class ModuleManager extends AbstractServiceManager<Module, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5410554151559729557L;
	@Inject
	private ModuleRepository repository;

	@Override
	protected JBaseRepository<Module, Long> getRepository() {
		return this.repository;
	}

	public void initializeMasterData() {
		String[] moduleNames = new String[]
				{"Administration", "General", "Department", "Category", "Product", "Client", "Agricultural"};

		for (int idx = 0; idx < moduleNames.length; idx++){
			if (repository.findByName(moduleNames[idx]).isPresent())
				continue;

			repository.save(Module.createInstance(null, moduleNames[idx], moduleNames[idx] + " momdule. "));
		}
	}

	public Optional<Module> getByName(String name) {
		return repository.findByName(name);
	}

	protected void setupModulesByExample(){
		
	}
}
