/**
 * 
 */
package net.brilliance.repository.general.catalog;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.Project;
import net.brilliance.framework.repository.AdvancedSearchRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface ProjectRepository extends AdvancedSearchRepository<Project, Long> {
	Optional<Project> findByCode(String license);
	Optional<Project> findByName(String name);
}
