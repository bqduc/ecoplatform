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

import javax.inject.Inject;
import javax.inject.Provider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.framework.repository.IBaseRepository;

/**
 * GenericsService implementation. Provides implementation of the generic entity
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public abstract class GenericsServiceImpl<E, K> implements GenericsService<E, K>{
	@Inject
	private Provider<IBaseRepository<E, K>> repository;

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(E entity) {
		this.repository.get().delete(entity);
	}

	@Override
	public List<E> getAll() {
		return this.repository.get().findAll();
	}

	@Override
	public void save(E entity) {
		this.repository.get().save(entity);
	}

	@Override
	public List<E> createDummyEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<E> getById(K key) {
		return this.repository.get().findOneById(key);
	}

	@Override
	public Page<E> getAll(Pageable pageable) {
		return this.repository.get().findAll(pageable);
	}

	protected abstract IBaseRepository<E, K> getRepository();
}
