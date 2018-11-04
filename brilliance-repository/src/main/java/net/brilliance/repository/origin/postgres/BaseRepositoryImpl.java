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
package net.brilliance.repository.origin.postgres;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.framework.repository.IBaseRepository;

/**
 * PostgreSQL datastore is the underlyig db
 * 
 * @author Julius Krah
 *
 * @param <T>
 *            The entity type
 * @param <ID>
 *            The entity identifier
 */
public class BaseRepositoryImpl<T, ID> implements IBaseRepository<T, ID> {
	private Logger log = GlobalLoggerFactory.getLogger(this.getClass());

	private Class<T> persistentClass;

	@PersistenceContext
	protected EntityManager em;

	//@formatter:off
	public BaseRepositoryImpl() {}
	//@formatter:on

	public BaseRepositoryImpl(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Override
	public void save(T entity) {
		if (this.em.contains(entity))
			this.em.merge(entity);
		else
			this.em.persist(entity);
	}

	@Override
	public void delete(T entity) {
		this.em.remove(this.em.contains(entity) ? entity : em.merge(entity));
	}

	@Override
	public void deleteAll() {
		Query query = this.em.createQuery(String.format("DELETE FROM %s e", persistentClass.getSimpleName()));
		log.debug("Query executed: {}", String.format("DELETE FROM %s e", persistentClass.getSimpleName()));
		query.executeUpdate();
	}

	@Override
	public Optional<T> findOneById(ID id) {
		return Optional.ofNullable(this.em.find(persistentClass, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		Query query = this.em.createQuery(String.format("SELECT e FROM %s e", persistentClass.getSimpleName()));
		log.debug("Query executed: {}", String.format("SELECT e FROM %s e", persistentClass.getSimpleName()));
		return query.getResultList();
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return null;
	}

}
