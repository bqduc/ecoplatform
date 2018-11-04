package net.brilliance.repository.admin;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.admin.Office;
import net.brilliance.framework.repository.BaseRepository;

@Repository
public interface OfficeRepository extends BaseRepository<Office, Long>{
	Office findByPhones(String phones);
	Optional<Office> findByCode(String code);
	Long countByCode(String code);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.manager.firstName) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.manager.lastName) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.spoc.firstName) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.spoc.lastName) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.phones) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<Office> search(@Param("keyword") String keyword, Pageable pageable);
}
