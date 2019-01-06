package net.brilliance.service.api;

import org.springframework.data.domain.Page;

import net.brilliance.domain.Schedule;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;

public interface ScheduleService extends GenericService<Schedule, Long> {
	/**
	 * Get one Regions with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Regions
	 */
	Page<Schedule> getObjects(SearchParameter searchParameter);
}
