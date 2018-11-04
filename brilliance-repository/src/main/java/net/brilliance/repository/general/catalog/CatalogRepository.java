/**
 * 
 */
package net.brilliance.repository.general.catalog;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.framework.repository.AdvancedSearchRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface CatalogRepository extends AdvancedSearchRepository<Catalogue, Long> {
	Page<Catalogue> findAll(Pageable pageable);
	Page<Catalogue> findAllByOrderByIdAsc(Pageable pageable);
	Optional<Catalogue> findByCode(String code);
	Optional<Catalogue> findByName(String name);
}
