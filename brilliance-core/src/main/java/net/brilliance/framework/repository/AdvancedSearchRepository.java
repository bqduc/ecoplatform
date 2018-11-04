/**
 * 
 */
package net.brilliance.framework.repository;

import java.io.Serializable;
import java.util.List;

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
public interface AdvancedSearchRepository<T, PK extends Serializable> extends JBaseRepository<T, PK> {
	Page<T> findAll(Pageable pageable);

	@Query("SELECT entity FROM #{#entityName} entity WHERE LOWER(entity.code) like LOWER(:keyword) OR LOWER(entity.name) like LOWER(:keyword) ")
	Page<T> search(@Param("keyword") String keyword, Pageable pageable);

	@Query("SELECT entity FROM #{#entityName} entity WHERE LOWER(entity.code) like LOWER(:keyword) OR LOWER(entity.name) like LOWER(:keyword) ")
	List<T> search(@Param("keyword") String keyword);
}
