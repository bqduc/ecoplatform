/**
 * 
 */
package net.brilliance.repository.system;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.system.SystemSequence;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface SystemSequenceRepository extends BaseRepository<SystemSequence, Long> {
	Page<SystemSequence> findAll(Pageable pageable);
	Page<SystemSequence> findAllByOrderByIdAsc(Pageable pageable);
	Optional<SystemSequence> findByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<SystemSequence> search(@Param("keyword") String keyword, Pageable pageable);
}
