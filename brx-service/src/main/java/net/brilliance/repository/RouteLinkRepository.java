/**
 * 
 */
package net.brilliance.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.RouteLink;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface RouteLinkRepository extends BaseRepository<RouteLink, Long> {
	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.route.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.route.description) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.station.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.station.description) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<RouteLink> search(@Param("keyword") String keyword, Pageable pageable);
}
