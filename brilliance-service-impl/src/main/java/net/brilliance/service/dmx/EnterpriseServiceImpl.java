package net.brilliance.service.dmx;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.dmx.EnterpriseRepository;
import net.brilliance.repository.specification.dmx.EnterpriseSpecification;
import net.brilliance.service.api.ObjectNotFoundException;
import net.brilliance.service.api.dmx.EnterpriseService;

@Service
public class EnterpriseServiceImpl extends GenericServiceImpl<Enterprise, Long> implements EnterpriseService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761477574156308888L;

	@Inject 
	private EnterpriseRepository repository;
	
	protected BaseRepository<Enterprise, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Enterprise getOne(String code) throws ObjectNotFoundException {
		return (Enterprise)super.getOptionalObject(repository.findByCode(code));
	}

	@Override
	protected Page<Enterprise> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<Enterprise> getObjects(SearchParameter searchParameter) {
		Page<Enterprise> pagedProducts = this.repository.findAll(EnterpriseSpecification.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}
}
