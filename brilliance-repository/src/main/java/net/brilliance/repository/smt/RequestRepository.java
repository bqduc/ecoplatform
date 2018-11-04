/**
 * 
 */
package net.brilliance.repository.smt;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.smt.Request;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface RequestRepository extends BaseRepository<Request, Long> {
	Page<Request> findAll(Pageable pageable);

	Optional<Request> findByNumber(String code);
	Long countByNumber(String code);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.number) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.notes) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.summary) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<Request> search(@Param("keyword") String keyword, Pageable pageable);
}
