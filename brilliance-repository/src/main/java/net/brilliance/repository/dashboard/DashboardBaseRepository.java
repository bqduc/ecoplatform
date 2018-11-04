/**
 * 
 */
package net.brilliance.repository.dashboard;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@NoRepositoryBean
public interface DashboardBaseRepository<T, PK extends Serializable> extends BaseRepository<T, PK> {
	Page<T> findAll(Pageable pageable);
	Page<T> findAllByOrderByIdAsc(Pageable pageable);
	Optional<T> findByName(String name);

	Long countByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.serial) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.notes) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<T> search(@Param("keyword") String keyword, Pageable pageable);
}
