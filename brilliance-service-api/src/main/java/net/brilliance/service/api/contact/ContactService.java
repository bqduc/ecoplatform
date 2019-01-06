package net.brilliance.service.api.contact;

import java.util.List;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface ContactService extends GenericService<Contact, Long>{
  /**
   * Get one contact with the provided code.
   * 
   * @param code The contact code
   * @return The contact
   * @throws ObjectNotFoundException If no such account exists.
   */
	Contact getOne(String code) throws ObjectNotFoundException;

	String deployContacts(List<List<String>> dataStrings);

	/**
   * Get one contacts with the provided search parameters.
   * 
   * @param searchParameter The search parameter
   * @return The pageable contacts
   */
	Page<Contact> getObjects(SearchParameter searchParameter);
}
