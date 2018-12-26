package net.brilliance.service.api.general;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.general.Attachment;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;

public interface AttachmentService extends GenericService<Attachment, Long> {
	/**
	 * Get one Attachments with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Attachments
	 */
	Page<Attachment> getObjects(SearchParameter searchParameter);
}
