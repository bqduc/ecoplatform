package net.brilliance.service.api.dmx;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.dmx.Inventory;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface InventoryService extends GenericService<Inventory, Long> {

	/**
	 * Get one InventoryEntry with the provided code.
	 * 
	 * @param isbn
	 *            The InventoryEntry isbn
	 * @return The InventoryEntry
	 * @throws ObjectNotFoundException
	 *             If no such InventoryEntry exists.
	 */
	Inventory getOne(String isbn) throws ObjectNotFoundException;

	/**
	 * Get one Enterprises with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Enterprises
	 */
	Page<Inventory> getObjects(SearchParameter searchParameter);
}
