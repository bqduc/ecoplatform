/**
 * 
 */
package net.brilliance.repository.general;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.brilliance.domain.entity.general.BusinessService;
import net.brilliance.framework.repository.IBaseRepository;

/**
 * @author ducbq
 *
 */
public interface BusinessServiceRepository extends IBaseRepository<BusinessService, Long> {
	Page<BusinessService> findAll(Pageable pageable);
}
