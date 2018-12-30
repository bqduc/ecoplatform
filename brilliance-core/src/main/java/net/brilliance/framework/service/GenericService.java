package net.brilliance.framework.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.framework.entity.ObjectBase;
import net.brilliance.framework.model.ExecutionContext;
import net.brilliance.framework.model.SearchParameter;

public interface GenericService<T extends ObjectBase, K extends Serializable>{
	T saveOrUpdate(T entity);

	void remove(K id);
	void remove(T entity);
	void removeAll();

	long count();
  /**
   * Get object with the provided key.
   * 
   * @param id The object key
   * @return The Object
   */
	T getObject(K id);
	List<T> getObjects();

	/**
	 * Get objects with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable objects
	 */
	Page<T> getObjects(SearchParameter searchParameter);

	Page<T> getObjects(Integer pageNumber);
	Page<T> getObjects(Integer pageNumber, Integer size);
	Page<T> searchObjects(String keyword, Pageable pageable);
	Page<T> search(Map<String, Object> parameters);

	List<T> imports(Map<Object, Object> parameters);

	ExecutionContext deploy(ExecutionContext executionContext) throws ExecutionContextException;
}