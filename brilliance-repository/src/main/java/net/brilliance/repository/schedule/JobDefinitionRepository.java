/**
 * 
 */
package net.brilliance.repository.schedule;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.schedule.JobDefinition;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface JobDefinitionRepository extends BaseRepository<JobDefinition, Long> {
	Optional<JobDefinition> findByCode(String code);
	Long countByCode(String code);
	Page<JobDefinition> findAll(Pageable pageable);
	Page<JobDefinition> findAllByOrderByIdAsc(Pageable pageable);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.triggerName) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.jobExecutionClass) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.info) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<JobDefinition> search(@Param("keyword") String keyword, Pageable pageable);
}
