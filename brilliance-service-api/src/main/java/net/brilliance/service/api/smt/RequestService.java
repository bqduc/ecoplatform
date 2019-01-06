package net.brilliance.service.api.smt;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.smt.Request;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface RequestService extends GenericService<Request, Long> {

	/**
	 * Get one Request with the provided code.
	 * 
	 * @param code
	 *            The Request number
	 * @return The Request
	 * @throws ObjectNotFoundException
	 *             If no such Request exists.
	 */
	Request getOne(String code) throws ObjectNotFoundException;

	/**
	 * Get one Requests with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Requests
	 */
	Page<Request> getObjects(SearchParameter searchParameter);
}
