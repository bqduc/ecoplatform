package net.brilliance.service.api.crx;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.crx.Opportunity;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface OpportunityService extends GenericService<Opportunity, Long> {

	/**
	 * Get one Order with the provided code.
	 * 
	 * @param name
	 *            The Order name
	 * @return The Order
	 * @throws ObjectNotFoundException
	 *             If no such Order exists.
	 */
	Opportunity getOne(String name) throws ObjectNotFoundException;

	/**
	 * Get one Enterprises with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Enterprises
	 */
	Page<Opportunity> getObjects(SearchParameter searchParameter);
}
