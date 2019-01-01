package net.brilliance.repository.contact;

import java.util.List;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.domain.entity.crx.contact.ContactAddress;
import net.brilliance.framework.repository.RepositoryBase;

@Repository
public interface ContactAddressRepository extends RepositoryBase<ContactAddress, Long> {
	List<ContactAddress> findByContact(Contact contact);
}
