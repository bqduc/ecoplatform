package net.brilliance.service.general;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.general.Attachment;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.framework.specifications.DefaultSpecification;
import net.brilliance.repository.general.AttachmentRepository;
import net.brilliance.service.api.general.AttachmentService;

@Service
public class AttachmentServiceImpl extends GenericServiceImpl<Attachment, Long> implements AttachmentService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761477574156308888L;

	@Inject 
	private AttachmentRepository repository;
	
	protected BaseRepository<Attachment, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected Page<Attachment> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<Attachment> getObjects(SearchParameter searchParameter) {
		Page<Attachment> pagedProducts = this.repository.findAll(
				DefaultSpecification.<Attachment, SearchRequest>builder().build().buildRepoSpecification(searchParameter), 
				searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}
}
