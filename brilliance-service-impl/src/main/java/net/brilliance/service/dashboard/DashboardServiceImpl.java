package net.brilliance.service.dashboard;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.system.DigitalDashboard;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.dashboard.DashboardRepository;
import net.brilliance.repository.specification.system.DashboardSpecifications;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.dashboard.DashboardService;

@Service
public class DashboardServiceImpl extends GenericServiceImpl<DigitalDashboard, Long> implements DashboardService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3368687940764598994L;
	@Inject 
	private DashboardRepository repository;
	
	protected BaseRepository<DigitalDashboard, Long> getRepository() {
		return this.repository;
	}

	@Override
	public DigitalDashboard getOne(String code) throws ObjectNotFoundException {
		return (DigitalDashboard)super.getOptionalObject(repository.findByName(code));
	}

	@Override
	protected Page<DigitalDashboard> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<DigitalDashboard> getObjects(SearchParameter searchParameter) {
		Page<DigitalDashboard> pagedProducts = this.repository.findAll(DashboardSpecifications.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}
}
