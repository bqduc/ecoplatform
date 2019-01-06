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

import net.brilliance.domain.Station;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface StationRepository extends BaseRepository<Station, Long> {
	Optional<Station> findByName(String name);
	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.description) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.city.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<Station> search(@Param("keyword") String keyword, Pageable pageable);
}
