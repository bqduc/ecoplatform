/**
 * 
 */
package net.brilliance.framework.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

/**
 * @author ducbq
 *
 */
@NoRepositoryBean
public interface SearchableRepository<T, PK extends Serializable> extends JBaseRepository<T, PK> {
  @Query(nativeQuery = true)
	Page<T> searchObjects(@Param("keyword") String keyword, Pageable pageable);
}
