package net.brilliance.repository.contact;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.contact.ContactTeam;
import net.brilliance.domain.entity.crm.contact.Contact;
import net.brilliance.framework.repository.RepositoryBase;

@Repository
public interface ContactTeamRepository extends RepositoryBase<ContactTeam, Long> {
	List<ContactTeam> findByContact(Contact contact);
}
