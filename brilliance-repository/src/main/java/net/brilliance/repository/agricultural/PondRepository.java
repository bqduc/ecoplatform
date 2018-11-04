/**
 * 
 */
package net.brilliance.repository.agricultural;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.aquacultural.Pond;
import net.brilliance.framework.repository.CodeBaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface PondRepository extends CodeBaseRepository<Pond, Long> {
}
