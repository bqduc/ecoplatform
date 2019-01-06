package net.brilliance.service.dmx;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.domain.entity.dmx.EnterpriseDetail;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.dmx.EnterpriseDetailsRepository;
import net.brilliance.repository.specification.dmx.EnterpriseDetailsSpecification;
import net.brilliance.service.api.dmx.EnterpriseDetailsService;

@Service
public class EnterpriseServiceDetailsImpl extends GenericServiceImpl<EnterpriseDetail, Long> implements EnterpriseDetailsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1416639642245331648L;

	@Inject 
	private EnterpriseDetailsRepository repository;
	
	protected BaseRepository<EnterpriseDetail, Long> getRepository() {
		return this.repository;
	}

	@Override
	public List<EnterpriseDetail> getByEnterprise(Enterprise enterprise) {
		return repository.findByEnterprise(enterprise);
	}

	@Override
	protected Page<EnterpriseDetail> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<EnterpriseDetail> getObjects(SearchParameter searchParameter) {
		Page<EnterpriseDetail> pagedProducts = this.repository.findAll(EnterpriseDetailsSpecification.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}
}
