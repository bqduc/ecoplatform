/*package net.brilliance.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.powertrain.common.logging.GlobalLoggerFactory;
import net.powertrain.controller.controller.constants.ControllerConstants;
import net.powertrain.domain.entity.admin.BusinessUnit;
import net.powertrain.framework.model.SearchParameter;
import net.powertrain.service.api.admin.BusinessUnitService;

@RestController
@RequestMapping(ControllerConstants.REQUEST_URI_BUSINESS_UNIT)
public class BusinessUnitRestController {
	protected Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Autowired
	private BusinessUnitService businessService;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<BusinessUnit> onListBusinessObjects() {
		return businessService.getObjects();
	}

	@RequestMapping(path="/listExt", method=RequestMethod.POST)
	public List<BusinessUnit> getEmployees(SearchParameter searchParams){
		List<BusinessUnit> results = businessService.getObjects();
		List<BusinessUnit> expectedResults = results.subList(0, 1550);
		System.out.println(results);
		return expectedResults;
	}
}
*/