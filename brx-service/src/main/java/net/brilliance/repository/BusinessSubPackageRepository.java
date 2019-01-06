/**
 * 
 */
package net.brilliance.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.brilliance.domain.BusinessSubPackage;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
public interface BusinessSubPackageRepository extends BaseRepository<BusinessSubPackage, Long> {
	Optional<BusinessSubPackage> findByCode(String code);
	Optional<BusinessSubPackage> findByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<BusinessSubPackage> search(@Param("keyword") String keyword, Pageable pageable);
}
