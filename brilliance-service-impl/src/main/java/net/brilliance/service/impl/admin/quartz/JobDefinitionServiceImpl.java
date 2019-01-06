package net.brilliance.service.impl.admin.quartz;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.schedule.JobDefinition;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.framework.specifications.predicator.BrilliancePredicator;
import net.brilliance.repository.schedule.JobDefinitionRepository;
import net.brilliance.repository.specification.quartz.JobDefinitionPredicator;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.admin.quartz.JobDefinitionService;

@Service
public class JobDefinitionServiceImpl extends GenericServiceImpl<JobDefinition, Long> implements JobDefinitionService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4213057902203249734L;

	@Inject 
	private JobDefinitionRepository repository;
	
	protected BaseRepository<JobDefinition, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected BrilliancePredicator<JobDefinition> getRepositoryPredicator() {
		return JobDefinitionPredicator.builder().build();
	}

	@Override
	public JobDefinition getOne(String code) throws ObjectNotFoundException {
		return (JobDefinition)super.getOptionalObject(repository.findByCode(code));
	}

	@Override
	protected Page<JobDefinition> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}
}
