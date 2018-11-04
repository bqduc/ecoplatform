/**
 * 
 */
package net.brilliance.repository.general.catalog;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.CatalogueSubtype;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface CatalogSubtypeRepository extends BaseRepository<CatalogueSubtype, Long> {
	Page<CatalogueSubtype> findAll(Pageable pageable);
	Page<CatalogueSubtype> findAllByOrderByIdAsc(Pageable pageable);
	Optional<CatalogueSubtype> findByName(String name);

	Optional<CatalogueSubtype> findByCode(String code);
	Long countByCode(String code);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.translatedName) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.description) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<CatalogueSubtype> search(@Param("keyword") String keyword, Pageable pageable);
}
