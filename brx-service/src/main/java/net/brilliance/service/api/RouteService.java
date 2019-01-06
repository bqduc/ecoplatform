package net.brilliance.service.api;

import org.springframework.data.domain.Page;

import net.brilliance.domain.Route;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;

public interface RouteService extends GenericService<Route, Long> {
	/**
	 * Get one Regions with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Regions
	 */
	Page<Route> getObjects(SearchParameter searchParameter);
}
