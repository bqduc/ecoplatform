/**
 * 
 */
package net.brilliance.repository.dmx;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.dmx.Inventory;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface InventoryRepository extends BaseRepository<Inventory, Long> {
	Page<Inventory> findAll(Pageable pageable);
	Page<Inventory> findAllByOrderByIdAsc(Pageable pageable);
	Optional<Inventory> findByName(String name);

	Optional<Inventory> findByCode(String code);
	Long countByCode(String code);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.description) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<Inventory> search(@Param("keyword") String keyword, Pageable pageable);
}
