package net.brilliance.service.api;

import org.springframework.data.domain.Page;

import net.brilliance.domain.Station;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;

public interface StationService extends GenericService<Station, Long> {
	/**
	 * Get one Regions with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Regions
	 */
	Page<Station> getObjects(SearchParameter searchParameter);
}
