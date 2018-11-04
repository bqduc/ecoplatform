/**
 * 
 */
package net.brilliance.repository.general.catalog;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.MeasureUnit;
import net.brilliance.framework.repository.JBaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface MeasureUnitRepository extends JBaseRepository<MeasureUnit, Long> {
	Optional<MeasureUnit> findByCode(String code);
	Optional<MeasureUnit> findByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity WHERE( LOWER(entity.code) like LOWER(:keyword) or LOWER(entity.name) like LOWER(:keyword) )")
	List<MeasureUnit> search(@Param("keyword") String keyword);
}
