package net.brilliance.service.api.crx.orders;

import org.springframework.data.domain.Page;

import net.brilliance.domain.entity.crx.BizOrder;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface BizOrderService extends GenericService<BizOrder, Long> {

	/**
	 * Get one Order with the provided code.
	 * 
	 * @param code
	 *            The Order code
	 * @return The Order
	 * @throws ObjectNotFoundException
	 *             If no such Order exists.
	 */
	BizOrder getOne(String code) throws ObjectNotFoundException;

	/**
	 * Get one Enterprises with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Enterprises
	 */
	Page<BizOrder> getObjects(SearchParameter searchParameter);
}
