/**
 * 
 */
package net.brilliance.repository.general.catalog;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.Category;
import net.brilliance.framework.repository.AdvancedSearchRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface CategoryRepository extends AdvancedSearchRepository<Category, Long> {
	Optional<Category> findByName(String name);
	Optional<Category> findByCode(String code);
}
