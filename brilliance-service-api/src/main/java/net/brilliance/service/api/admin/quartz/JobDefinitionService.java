package net.brilliance.service.api.admin.quartz;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.schedule.JobDefinition;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface JobDefinitionService extends GenericService<JobDefinition, Long> {

	/**
	 * Get one JobDefinition with the provided name.
	 * 
	 * @param name
	 *            The JobDefinition name
	 * @return The JobDefinition
	 * @throws ObjectNotFoundException
	 *             If no such JobDefinition exists.
	 */
	JobDefinition getOne(String name) throws ObjectNotFoundException;
}
