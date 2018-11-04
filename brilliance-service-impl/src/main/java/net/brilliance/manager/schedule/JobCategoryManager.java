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

package net.brilliance.manager.schedule;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.schedule.JobCategory;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.repository.schedule.JobCategoryRepository;

*//**
 * Job category service implementation. Provides implementation of the job category
 * 
 * @author ducbq
 *
 *//*
@Service
@Transactional
public class JobCategoryManager extends AbstractServiceManager<JobCategory, Long> {
	*//**
	 * 
	 *//*
	private static final long serialVersionUID = -1947346989538287565L;

	final Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private JobCategoryRepository repository;

	@Override
	protected JBaseRepository<JobCategory, Long> getRepository() {
		return this.repository;
	}

	public List<JobCategory> createDummyObjects() {
		List<JobCategory> fetchedObjects = null;
		return fetchedObjects;
	}

	public Optional<JobCategory> getByName(String name) {
		return repository.findByName(name);
	}

	public List<JobCategory> getAll(Integer pageNumber){
    PageRequest pageRequest = new PageRequest(pageNumber - 1, CommonConstants.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
    Page<JobCategory> pagedEntities = getRepository().findAll(pageRequest);
    return pagedEntities.getContent();
	}
}
*/