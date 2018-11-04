/**
 * 
 */
package net.brilliance.framework.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

/**
 * @author ducbq
 *
 */
@NoRepositoryBean
public interface SearchRepository<T, PK extends Serializable> extends JBaseRepository<T, PK> {
	@Query("SELECT entity FROM #{#entityName} entity WHERE LOWER(entity.name) like LOWER(:keyword) ")
	List<T> search(@Param("keyword") String keyword);
}
