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
package net.brilliance.manager.catalog.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonConstants;
import net.brilliance.domain.entity.general.Department;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.repository.general.catalog.DepartmentRepository;

/**
 * Department service implementation. Provides implementation of the department
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class DepartmentManager extends AbstractServiceManager<Department, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8828464403482350600L;
	@Inject
	private DepartmentRepository repository;

	@Override
	protected JBaseRepository<Department, Long> getRepository() {
		return this.repository;
	}

	public List<Department> createDummyObjects() {
		Department fetchObject = null;
		List<Department> fetchedObjects = new ArrayList<>();
		for (int i = 1; i <= CommonConstants.MAX_DUMMY_OBJECTS; ++i){
			fetchObject = Department.getInstance("STKDEPT-" + i, "Nhóm hàng hóa "+i);
			this.save(fetchObject);
			fetchedObjects.add(fetchObject);
		}
		return fetchedObjects;
	}

	public Optional<Department> getByName(String name) {
		return repository.findByName(name);
	}

	public Optional<Department> getByCode(String code) {
		return repository.findByCode(code);
	}

}
