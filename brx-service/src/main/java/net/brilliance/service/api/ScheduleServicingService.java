package net.brilliance.service.api;

import org.springframework.data.domain.Page;

import net.brilliance.domain.ScheduleServicing;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;

public interface ScheduleServicingService extends GenericService<ScheduleServicing, Long> {
	/**
	 * Get one Regions with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Regions
	 */
	Page<ScheduleServicing> getObjects(SearchParameter searchParameter);
}
