package net.brilliance.repository.contact;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.framework.repository.BaseRepository;

@Repository
public interface ContactRepository extends BaseRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
	Optional<Contact> findOneById(Long id);

	Contact findByEmail(String email);

	Contact findByCode(String code);
	Long countByCode(String code);
	
  @Query(value = "SELECT entity.code FROM #{#entityName} entity ", nativeQuery = true)
  List<String> findCode();

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.firstName) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.lastName) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.email) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<Contact> search(@Param("keyword") String keyword, Pageable pageable);
}
