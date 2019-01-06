package net.brilliance.service.api.inventory;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.general.CatalogueSubtype;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface CatalogueSubtypeService extends GenericService<CatalogueSubtype, Long> {

	/**
	 * Get one CatalogSubtype with the provided code.
	 * 
	 * @param code
	 *            The CatalogSubtype code
	 * @return The CatalogSubtype
	 * @throws ObjectNotFoundException
	 *             If no such CatalogSubtype exists.
	 */
	CatalogueSubtype getOne(String code) throws ObjectNotFoundException;

	/**
	 * Get one CatalogSubtypes with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable CatalogSubtypes
	 */
	Page<CatalogueSubtype> getObjects(SearchParameter searchParameter);
}
