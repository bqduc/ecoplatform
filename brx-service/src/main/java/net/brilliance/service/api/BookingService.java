package net.brilliance.service.api;

import org.springframework.data.domain.Page;

import net.brilliance.domain.Booking;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;

public interface BookingService extends GenericService<Booking, Long> {
	/**
	 * Get one Regions with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Regions
	 */
	Page<Booking> getObjects(SearchParameter searchParameter);
}
