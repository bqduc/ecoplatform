/*package net.powertrain.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.powertrain.common.logging.GlobalLoggerFactory;
import net.powertrain.controller.controller.constants.ControllerConstants;
import net.powertrain.domain.entity.admin.Office;
import net.powertrain.framework.model.SearchParameter;
import net.powertrain.service.api.admin.OfficeService;

@RestController
@RequestMapping(ControllerConstants.REQUEST_URI_OFFICE)
public class OfficeRestController {
	protected Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Autowired
	private OfficeService businessService;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Office> onListBusinessObjects() {
		return businessService.getObjects();
	}

	@RequestMapping(path="/listExt", method=RequestMethod.POST)
	public List<Office> getEmployees(SearchParameter searchParams){
		List<Office> results = businessService.getObjects();
		List<Office> expectedResults = results.subList(0, 1550);
		System.out.println(results);
		return expectedResults;
	}
}
*/