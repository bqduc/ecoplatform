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

import javax.inject.Inject;
import javax.inject.Provider;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonConstants;
import net.brilliance.domain.entity.general.BusinessService;
import net.brilliance.framework.manager.GenericsServiceImpl;
import net.brilliance.framework.repository.IBaseRepository;
import net.brilliance.manager.catalog.BusinessServiceManager;
import net.brilliance.repository.general.BusinessServiceRepository;

/**
 * Category service implementation. Provides implementation of the department
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class BusinessServicingServiceImpl extends GenericsServiceImpl<BusinessService, Long> implements BusinessServiceManager {
	@Inject
	private Provider<BusinessServiceRepository> businessServiceRepository;

	@Override
	protected IBaseRepository<BusinessService, Long> getRepository() {
		return (IBaseRepository<BusinessService, Long>) this.businessServiceRepository;
	}

	@Override
	public List<BusinessService> createDummyObjects() {
		BusinessService fetchObject = null;
		List<BusinessService> fetchedObjects = new ArrayList<>();
		for (int i = 1; i <= CommonConstants.MAX_DUMMY_OBJECTS; ++i){
			fetchObject = BusinessService.getInstance("STKCATE-" + i, "Thể loại hàng hóa "+i, null, null);
			this.save(fetchObject);
			fetchedObjects.add(fetchObject);
		}
		return fetchedObjects;
	}
}
