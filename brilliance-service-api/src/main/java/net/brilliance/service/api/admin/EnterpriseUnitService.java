package net.brilliance.service.api.admin;

import java.util.List;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.admin.EnterpriseUnit;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface EnterpriseUnitService extends GenericService<EnterpriseUnit, Long>{

  /**
   * Get one EnterpriseUnit with the provided code.
   * 
   * @param code The EnterpriseUnit code
   * @return The EnterpriseUnit
   * @throws ObjectNotFoundException If no such EnterpriseUnit exists.
   */
	EnterpriseUnit getOne(String code) throws ObjectNotFoundException;

	String deployObjects(List<List<String>> dataStrings);

  /**
   * Get one EnterpriseUnits with the provided search parameters.
   * 
   * @param searchParameter The search parameter
   * @return The pageable EnterpriseUnits
   */
	Page<EnterpriseUnit> getObjects(SearchParameter searchParameter);
}
