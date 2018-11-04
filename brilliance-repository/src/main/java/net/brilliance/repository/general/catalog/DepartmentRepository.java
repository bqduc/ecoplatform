/**
 * 
 */
package net.brilliance.repository.general.catalog;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.Department;
import net.brilliance.framework.repository.AdvancedSearchRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface DepartmentRepository extends AdvancedSearchRepository<Department, Long> {
	Page<Department> findAll(Pageable pageable);
	Page<Department> findAllByOrderByIdAsc(Pageable pageable);
	Optional<Department> findByCode(String code);
	Optional<Department> findByName(String name);

	/*@Query("SELECT dept FROM Department dept WHERE LOWER(dept.code) like LOWER(:keyword) "
			+ "OR LOWER(dept.name) like LOWER(:keyword) "
			+ "OR LOWER(dept.translatedName) like LOWER(:keyword)")
	List<Department> search(@Param("keyword") String keyword);*/
}
