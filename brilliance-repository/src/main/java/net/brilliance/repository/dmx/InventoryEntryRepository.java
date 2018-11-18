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

import net.brilliance.domain.entity.dmx.InventoryEntry;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface InventoryEntryRepository extends BaseRepository<InventoryEntry, Long> {
	Page<InventoryEntry> findAll(Pageable pageable);
	Page<InventoryEntry> findAllByOrderByIdAsc(Pageable pageable);
	Optional<InventoryEntry> findByName(String name);

	Optional<InventoryEntry> findByIsbn(String isbn);
	Long countByIsbn(String isbn);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.isbn) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.description) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<InventoryEntry> search(@Param("keyword") String keyword, Pageable pageable);
}
