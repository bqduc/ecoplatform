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

import net.brilliance.domain.entity.schedule.JobCategory;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface JobCategoryRepository extends BaseRepository<JobCategory, Long> {
	Optional<JobCategory> findByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			//+ "or LOWER(entity.info) like LOWER(CONCAT('%',:keyword,'%'))"
			/*
			+ "or LOWER(entity.parent.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.parent.info) like LOWER(CONCAT('%',:keyword,'%'))" */
			+ ")"
	)
	Page<JobCategory> search(@Param("keyword") String keyword, Pageable pageable);
}
