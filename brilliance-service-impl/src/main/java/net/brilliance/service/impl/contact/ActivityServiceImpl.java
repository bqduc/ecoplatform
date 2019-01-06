package net.brilliance.service.impl.contact;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.general.Activity;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.contact.ActivityRepository;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.contact.ActivityService;

@Service
public class ActivityServiceImpl extends GenericServiceImpl<Activity, Long> implements ActivityService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4580295852310880409L;

	@Inject 
	private ActivityRepository repository;
	
	protected BaseRepository<Activity, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Page<Activity> getObjects(SearchParameter searchParameter) {
		return null;
	}

	@Override
	public Activity getName(String name) throws ObjectNotFoundException {
		return repository.findByName(name);
	}

	@Override
	protected Page<Activity> performSearch(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
