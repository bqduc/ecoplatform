/**
 * 
 */
package net.brilliance.repository.dmx;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface EnterpriseRepository extends BaseRepository<Enterprise, Long> {
	Page<Enterprise> findAll(Pageable pageable);
	Page<Enterprise> findAllByOrderByIdAsc(Pageable pageable);
	Optional<Enterprise> findByName(String name);

	Optional<Enterprise> findByCode(String code);
	Long countByCode(String code);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<Enterprise> search(@Param("keyword") String keyword, Pageable pageable);
}
