package net.brilliance.service.api.dmx;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.general.Project;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface ProjectService extends GenericService<Project, Long> {

	/**
	 * Get one Project with the provided code.
	 * 
	 * @param code
	 *            The Project code
	 * @return The Project
	 * @throws ObjectNotFoundException
	 *             If no such Project exists.
	 */
	Project getOne(String code) throws ObjectNotFoundException;

	/**
	 * Get one Enterprises with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Enterprises
	 */
	Page<Project> getObjects(SearchParameter searchParameter);
}
