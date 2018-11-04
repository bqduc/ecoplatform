/**
 * 
 */
package net.brilliance.repository.vbb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.vbb.VbbPost;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface VbbPostRepository extends BaseRepository<VbbPost, Long> {
	VbbPost findByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity "
			+ "WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<VbbPost> search(@Param("keyword") String keyword, Pageable pageable);

	@Query("SELECT entity FROM #{#entityName} entity "
			+ "WHERE ("
			+ " entity.thread.id = :threadId"
			+ ")"
	)
	Page<VbbPost> findByThreadId(@Param("threadId") Long threadId, Pageable pageable);
}
