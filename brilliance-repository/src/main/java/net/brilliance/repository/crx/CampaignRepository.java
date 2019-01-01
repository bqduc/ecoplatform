/**
 * 
 */
package net.brilliance.repository.crx;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.crx.Campaign;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface CampaignRepository extends BaseRepository<Campaign, Long> {
	Optional<Campaign> findByName(String name);
	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.content) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<Campaign> search(@Param("keyword") String keyword, Pageable pageable);
}
