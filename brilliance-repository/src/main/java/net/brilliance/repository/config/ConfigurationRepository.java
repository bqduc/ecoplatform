/**
 * 
 */
package net.brilliance.repository.config;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.config.Configuration;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface ConfigurationRepository extends BaseRepository<Configuration, Long> {
	Configuration findByName(String name);

	@Query("SELECT count(entity.id)>0 FROM #{#entityName} entity "
			+ "WHERE ("
			+ " LOWER(entity.name) = LOWER(:name)"
			+ ")"
	)
	boolean isExists(String name);

	@Query("SELECT entity FROM #{#entityName} entity "
			+ "WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<Configuration> search(@Param("keyword") String keyword, Pageable pageable);

	List<Configuration> findByGroup(String group);
}
