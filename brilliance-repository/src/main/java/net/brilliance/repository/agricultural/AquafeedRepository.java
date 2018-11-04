/**
 * 
 */
package net.brilliance.repository.agricultural;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.aquacultural.Aquafeed;
import net.brilliance.framework.repository.CodeBaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface AquafeedRepository extends CodeBaseRepository<Aquafeed, Long> {
	Optional<Aquafeed> findByName(String name);
}
