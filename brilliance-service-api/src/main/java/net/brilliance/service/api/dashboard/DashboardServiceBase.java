package net.brilliance.service.api.dashboard;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface DashboardServiceBase<T extends BizObjectBase, K extends Serializable> extends GenericService<T, K> {

	/**
	 * Get one T with the provided code.
	 * 
	 * @param name
	 *            The T name
	 * @return The T
	 * @throws ObjectNotFoundException
	 *             If no such T exists.
	 */
	T getOne(String name) throws ObjectNotFoundException;

	/**
	 * Get one Ts with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Ts
	 */
	Page<T> getObjects(SearchParameter searchParameter);
}
