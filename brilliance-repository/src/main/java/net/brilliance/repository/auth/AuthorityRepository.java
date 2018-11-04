/**
 * 
 */
package net.brilliance.repository.auth;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.admin.Authority;
import net.brilliance.framework.repository.JBaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface AuthorityRepository extends JBaseRepository<Authority, Long> {
	Authority findByName(String name);
}
