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
package net.brilliance.manager.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonConstants;
import net.brilliance.domain.entity.general.Category;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.repository.general.catalog.CategoryRepository;

/**
 * Category service implementation. Provides implementation of the category
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class CategoryManager extends AbstractServiceManager<Category, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4339969445985060347L;

	@Inject
	private CategoryRepository repository;

	@Override
	protected JBaseRepository<Category, Long> getRepository() {
		return this.repository;
	}

	public List<Category> createDummyObjects() {
		Category fetchObject = null;
		List<Category> fetchedObjects = new ArrayList<>();
		for (int i = 1; i <= CommonConstants.MAX_DUMMY_OBJECTS; ++i){
			fetchObject = Category.getInstance("STKCATE-" + i, "Thể loại hàng hóa "+i, null);
			this.save(fetchObject);
			fetchedObjects.add(fetchObject);
		}
		return fetchedObjects;
	}

	public Optional<Category> getByName(String name) {
		return repository.findByName(name);
	}

	public Optional<Category> getByCode(String code) {
		return repository.findByCode(code);
	}
}
