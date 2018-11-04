package net.brilliance.repository.contact;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.domain.entity.contact.ClientProfile;
import net.brilliance.framework.repository.SearchableRepository;

@Repository
public interface ClientProfileRepository extends SearchableRepository/*JBaseRepository*/<ClientProfile, Long> {
	ClientProfile findByCode(String code);
	ClientProfile findByUser(UserAccount user);

	/*@Query("SELECT entity FROM #{#entityName} entity "
			+ "WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.fullName) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.phones) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<ClientProfile> search(@Param("keyword") String keyword, Pageable pageable);*/
}