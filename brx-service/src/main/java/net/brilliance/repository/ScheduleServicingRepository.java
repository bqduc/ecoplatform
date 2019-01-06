/**
 * 
 */
package net.brilliance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.ScheduleServicing;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface ScheduleServicingRepository extends BaseRepository<ScheduleServicing, Long> {
	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.serviceContact.firstName) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.businessPackage.code) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.businessPackage.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.comments) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<ScheduleServicing> search(@Param("keyword") String keyword, Pageable pageable);
}
