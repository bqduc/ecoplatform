package net.brilliance.service.impl.admin.quartz;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.schedule.JobCategory;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.framework.specifications.predicator.BrilliancePredicator;
import net.brilliance.repository.schedule.JobCategoryRepository;
import net.brilliance.repository.specification.quartz.JobCategoryPredicator;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.admin.quartz.JobCategoryService;

@Service
public class JobCategoryServiceImpl extends GenericServiceImpl<JobCategory, Long> implements JobCategoryService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4779262410553662634L;

	@Inject 
	private JobCategoryRepository repository;
	
	protected BaseRepository<JobCategory, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected BrilliancePredicator<JobCategory> getRepositoryPredicator() {
		return JobCategoryPredicator.builder().build();
	}

	@Override
	public Optional<JobCategory> getOne(String name) throws ObjectNotFoundException {
		return repository.findByName(name);
	}

	@Override
	protected Page<JobCategory> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}
}
