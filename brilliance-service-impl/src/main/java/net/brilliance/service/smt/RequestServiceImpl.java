package net.brilliance.service.smt;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.smt.Request;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.smt.RequestRepository;
import net.brilliance.repository.specification.smt.RequestRepoSpec;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.smt.RequestService;

@Service
public class RequestServiceImpl extends GenericServiceImpl<Request, Long> implements RequestService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2369656282587595766L;

	@Inject 
	private RequestRepository repository;
	
	protected BaseRepository<Request, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Request getOne(String code) throws ObjectNotFoundException {
		return (Request)super.getOptionalObject(repository.findByNumber(code));
	}

	@Override
	protected Page<Request> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<Request> getObjects(SearchParameter searchParameter) {
		Page<Request> pagedProducts = this.repository.findAll(RequestRepoSpec.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}
}
