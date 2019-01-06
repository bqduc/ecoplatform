package net.brilliance.service.general;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.general.City;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.general.CityRepository;
import net.brilliance.service.api.general.CityService;

@Service
public class CityServiceImpl extends GenericServiceImpl<City, Long> implements CityService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4761950125978671850L;
	@Inject 
	private CityRepository repository;
	
	protected BaseRepository<City, Long> getRepository() {
		return this.repository;
	}

	@Override
	public City getOne(String name) throws ObjectNotFoundException {
		return (City)super.getOptionalObject(repository.findByName(name));
	}
}
