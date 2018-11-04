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
package net.brilliance.framework.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Contains service methods for the management of RepositoryEntity accounts
 * 
 * @author ducbq
 *
 */
public interface GenericsService<E, K> {
	/**
	 * This is a utility method to drop all accounts in the database
	 */
	public void deleteAll();

	/**
	 * Detaches a RepositoryEntity entity from the current persistence context
	 * 
	 * @param entity
	 *            the RepositoryEntity entity to detach
	 */
	public void delete(E entity);

	/**
	 * This method gets all the entitys from sql database
	 * 
	 * @return List<RepositoryEntity> the list of all entitys in the database
	 */
	public List<E> getAll();

	/**
	 * Facade method to create or update accounts
	 * 
	 * @param entity
	 *            the Account to be created or updated
	 */
	public void save(E entity);

	/**
	 * This method creates the dummy entitys
	 * 
	 * @return List<RepositoryEntity> the list of all entities in the database
	 */
	public List<E> createDummyEntities();
	
	/**
	 * This brings one unique entity from the database
	 * 
	 * @param id
	 *            the product identifier
	 * @return {@code Optional<RepositoryEntity>} the optional product object. This is never
	 *         null
	 */
	public Optional<E> getById(K key);

  Page<E> getAll(Pageable pageable);

}
