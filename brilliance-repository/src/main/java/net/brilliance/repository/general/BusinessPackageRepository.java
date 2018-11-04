/**
 * 
 */
package net.brilliance.repository.general;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.BusinessPackage;
import net.brilliance.framework.repository.IBaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface BusinessPackageRepository extends IBaseRepository<BusinessPackage, Long> {
	Page<BusinessPackage> findAll(Pageable pageable);
}
