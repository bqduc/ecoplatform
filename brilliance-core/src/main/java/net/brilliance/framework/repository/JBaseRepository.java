/**
 * 
 */
package net.brilliance.framework.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author ducbq
 *
 */
@NoRepositoryBean
public interface JBaseRepository<T, PK extends Serializable> extends BaseRepository<T, PK> {
	Page<T> findAll(Pageable pageable);
}