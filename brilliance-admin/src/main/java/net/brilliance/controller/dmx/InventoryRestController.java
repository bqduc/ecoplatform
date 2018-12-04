package net.brilliance.controller.dmx;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.controller.base.BaseRestController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.dmx.Inventory;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.dmx.InventoryService;

@RestController
@RequestMapping(CommonConstants.REST_API + ControllerConstants.URI_INVENTORY)
public class InventoryRestController extends BaseRestController {
	private final static String CACHED_BUSINESS_OBJECTS = "cachedInventoryEntries";

	@Inject
	private InventoryService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Inventory> onListEmployees() {
		List<Inventory> businessObjects = (List<Inventory>)this.httpSession.getAttribute(CACHED_BUSINESS_OBJECTS);
		if (CommonUtility.isEmpty(businessObjects)){
			businessObjects = businessService.getObjects();
			this.httpSession.setAttribute(CACHED_BUSINESS_OBJECTS, businessObjects);
		}

		return businessObjects;
	}

	@RequestMapping(path="/searchEmployees", method=RequestMethod.POST)
	public List<Inventory> getEmployees(SearchParameter searchParams){
		Page<Inventory> results = businessService.getObjects(searchParams);
		List<Inventory> expectedResults = results.getContent().subList(0, 150);
		return expectedResults;
	}

	/*@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public InventoryEntry getEmployeeById(@PathVariable("id") long id) {
		return employeeService.getEmployeeById(id);
	}*/
}
