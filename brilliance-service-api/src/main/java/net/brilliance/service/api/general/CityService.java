package net.brilliance.service.api.general;

import net.brilliance.domain.entity.general.City;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.framework.service.GenericService;

public interface CityService extends GenericService<City, Long> {

	/**
	 * Get one City with the provided name.
	 * 
	 * @param name
	 *            The City name
	 * @return The City
	 * @throws ObjectNotFoundException
	 *             If no such City exists.
	 */
	City getOne(String name) throws ObjectNotFoundException;
}
