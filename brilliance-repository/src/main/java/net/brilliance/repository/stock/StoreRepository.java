/**
 * 
 */
package net.brilliance.repository.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.brilliance.domain.entity.stock.Store;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
//@Repository
public interface StoreRepository extends BaseRepository<Store, Long> {
	Store findByCode(String code);
	Store findByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity "
			+ "WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.address) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<Store> search(@Param("keyword") String keyword, Pageable pageable);
}
