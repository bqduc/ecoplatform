package net.brilliance.service.api.system;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.system.SystemSequence;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;

public interface SystemSequenceService extends GenericService<SystemSequence, Long> {

	/**
	 * Get one CatalogSubtype with the provided code.
	 * 
	 * @param code
	 *            The SystemSequence name
	 * @return The SystemSequence
	 * @throws ObjectNotFoundException
	 *             If no such SystemSequence exists.
	 */
	SystemSequence getOne(String name) throws ObjectNotFoundException;

	/**
	 * Get one SystemSequences with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable SystemSequences
	 */
	Page<SystemSequence> getObjects(SearchParameter searchParameter);
}
