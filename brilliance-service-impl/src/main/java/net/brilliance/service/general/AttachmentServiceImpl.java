package net.brilliance.service.general;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.general.Attachment;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
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
}
