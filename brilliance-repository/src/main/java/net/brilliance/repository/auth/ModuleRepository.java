/**
 * 
 */
package net.brilliance.repository.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.security.Module;
import net.brilliance.framework.repository.SearchRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface ModuleRepository extends SearchRepository<Module, Long> {
	Optional<Module> findByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity WHERE LOWER(entity.name) like LOWER(:keyword) ")
	List<Module> search(@Param("keyword") String keyword);
}
