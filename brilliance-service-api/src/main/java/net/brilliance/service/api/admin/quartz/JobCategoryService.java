package net.brilliance.service.api.admin.quartz;

import java.util.Optional;

import net.brilliance.domain.entity.schedule.JobCategory;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface JobCategoryService extends GenericService<JobCategory, Long> {

	/**
	 * Get one JobCategory with the provided name.
	 * 
	 * @param name
	 *            The JobCategory name
	 * @return The JobCategory
	 * @throws ObjectNotFoundException
	 *             If no such JobCategory exists.
	 */
	Optional<JobCategory> getOne(String name) throws ObjectNotFoundException;
}
