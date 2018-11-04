package net.brilliance.repository.contact;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.contact.ContactProfile;
import net.brilliance.framework.repository.BaseRepository;

@Repository
public interface ContactProfileRepository extends BaseRepository<ContactProfile, Long> {
	Optional<ContactProfile> findOneById(Long id);

	ContactProfile findByFullName(String fullName);

	ContactProfile findByCode(String code);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.fullName) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<ContactProfile> search(@Param("keyword") String keyword, Pageable pageable);
}
