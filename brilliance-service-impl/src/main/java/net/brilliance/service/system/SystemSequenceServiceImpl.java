package net.brilliance.service.system;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.system.SystemSequence;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.specification.system.SystemSequenceSpecifications;
import net.brilliance.repository.system.SystemSequenceRepository;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.system.SystemSequenceService;

@Service
public class SystemSequenceServiceImpl extends GenericServiceImpl<SystemSequence, Long> implements SystemSequenceService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4213057902203249734L;

	@Inject 
	private SystemSequenceRepository repository;
	
	protected BaseRepository<SystemSequence, Long> getRepository() {
		return this.repository;
	}

	@Override
	public SystemSequence getOne(String code) throws ObjectNotFoundException {
		return (SystemSequence)super.getOptionalObject(repository.findByName(code));
	}

	@Override
	protected Page<SystemSequence> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<SystemSequence> getObjects(SearchParameter searchParameter) {
		Page<SystemSequence> pagedProducts = this.repository.findAll(SystemSequenceSpecifications.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}
}
