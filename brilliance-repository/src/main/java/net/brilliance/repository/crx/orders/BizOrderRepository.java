/**
 * 
 */
package net.brilliance.repository.crx.orders;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.crx.BizOrder;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface BizOrderRepository extends BaseRepository<BizOrder, Long> {
	Page<BizOrder> findAll(Pageable pageable);
	Page<BizOrder> findAllByOrderByIdAsc(Pageable pageable);
	Optional<BizOrder> findByName(String name);
	Optional<BizOrder> findByCode(String code);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<BizOrder> search(@Param("keyword") String keyword, Pageable pageable);
}
