package net.brilliance.controller.dmx;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.controller.base.BaseRestController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.dmx.InventoryEntry;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.dmx.InventoryEntryService;

@RestController
@RequestMapping(CommonConstants.REST_API + ControllerConstants.URI_INVENTORY_ENTRY)
public class InventoryEntryRestController extends BaseRestController {
	private final static String CACHED_BUSINESS_OBJECTS = "cached.inventory.entries";

	@Inject
	private InventoryEntryService businessService;

  @Inject 
  private HttpSession httpSession;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<InventoryEntry> onListEmployees() {
		List<InventoryEntry> businessObjects = (List<InventoryEntry>)this.httpSession.getAttribute(CACHED_BUSINESS_OBJECTS);
		if (CommonUtility.isEmpty(businessObjects)){
			businessObjects = businessService.getObjects();
			this.httpSession.setAttribute(CACHED_BUSINESS_OBJECTS, businessObjects);
		}

		return businessObjects;
	}

	@RequestMapping(path="/searchEmployees", method=RequestMethod.POST)
	public List<InventoryEntry> getEmployees(SearchParameter searchParams){
		Page<InventoryEntry> results = businessService.getObjects(searchParams);
		List<InventoryEntry> expectedResults = results.getContent().subList(0, 150);
		return expectedResults;
	}

	/*@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public InventoryEntry getEmployeeById(@PathVariable("id") long id) {
		return employeeService.getEmployeeById(id);
	}*/
}
