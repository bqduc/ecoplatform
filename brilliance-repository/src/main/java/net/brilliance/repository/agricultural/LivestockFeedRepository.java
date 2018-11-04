/**
 * 
 */
package net.brilliance.repository.agricultural;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.aquacultural.LivestockFeed;
import net.brilliance.framework.repository.CodeBaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface LivestockFeedRepository extends CodeBaseRepository<LivestockFeed, Long> {
	Optional<LivestockFeed> findByName(String name);
}
