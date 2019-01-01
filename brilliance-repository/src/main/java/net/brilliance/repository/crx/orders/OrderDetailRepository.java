/**
 * 
 */
package net.brilliance.repository.crx.orders;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.crx.BizOrder;
import net.brilliance.domain.entity.crx.OrderDetail;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface OrderDetailRepository extends BaseRepository<OrderDetail, Long> {
	List<OrderDetail> findByBizOrder(BizOrder order);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.entry.code) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.entry.name) like LOWER(CONCAT('%',:keyword,'%')) "

			+ "or LOWER(entity.bizOrder.code) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.bizOrder.name) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<OrderDetail> search(@Param("keyword") String keyword, Pageable pageable);
}
