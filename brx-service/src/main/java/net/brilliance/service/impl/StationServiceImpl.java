package net.brilliance.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.brilliance.domain.Station;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.StationRepository;
import net.brilliance.service.api.StationService;

@Service
public class StationServiceImpl extends GenericServiceImpl<Station, Long> implements StationService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4800075642286878168L;

	@Inject 
	private StationRepository repository;
	
	protected BaseRepository<Station, Long> getRepository() {
		return this.repository;
	}
}
