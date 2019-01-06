package net.brilliance.service.api.dmx;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface EnterpriseService extends GenericService<Enterprise, Long> {

	/**
	 * Get one Enterprise with the provided code.
	 * 
	 * @param code
	 *            The Enterprise code
	 * @return The Enterprise
	 * @throws ObjectNotFoundException
	 *             If no such Enterprise exists.
	 */
	Enterprise getOne(String code) throws ObjectNotFoundException;

	/**
	 * Get one Enterprises with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Enterprises
	 */
	Page<Enterprise> getObjects(SearchParameter searchParameter);
}
