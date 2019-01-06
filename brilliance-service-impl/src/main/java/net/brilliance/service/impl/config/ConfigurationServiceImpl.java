package net.brilliance.service.impl.config;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.common.ListUtility;
import net.brilliance.domain.entity.config.Configuration;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.config.ConfigurationRepository;
import net.brilliance.repository.specification.dmx.ConfigurationRepoSpecification;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.config.ConfigurationService;

@Service
public class ConfigurationServiceImpl extends GenericServiceImpl<Configuration, Long> implements ConfigurationService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6599759831205872515L;

	@Inject 
	private ConfigurationRepository repository;
	
	protected BaseRepository<Configuration, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Configuration getOne(String name) throws ObjectNotFoundException {
		return repository.findByName(name);
	}

	@Override
	protected Page<Configuration> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<Configuration> getObjects(SearchParameter searchParameter) {
		return this.repository.findAll(ConfigurationRepoSpecification.buildSpecification(searchParameter), searchParameter.getPageable());
	}

	@Override
	public List<Configuration> getByGroup(String group) {
		return this.repository.findByGroup(group);
	}
}
