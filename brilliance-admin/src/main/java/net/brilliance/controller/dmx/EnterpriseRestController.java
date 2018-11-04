package net.brilliance.controller.dmx;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brilliance.controller.base.BaseRestController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.dmx.EnterpriseService;

@RestController
@RequestMapping("/" + ControllerConstants.REQ_MAPPING_ENTERPRISE)
public class EnterpriseRestController extends BaseRestController{
	@Inject 
	private EnterpriseService businessManager;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Enterprise> onListEnterprises(HttpServletRequest request, HttpServletResponse response, Model model) {
		cLog.info("Rest::Come to catalogue subtype data listing ...>>>>>>");
		PageRequest pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
		SearchParameter searchParameter = SearchParameter.getInstance()
				.setPageable(pageRequest);
		Page<Enterprise> objects = businessManager.getObjects(searchParameter);
		cLog.info("Catalogue subtype data is loaded. >>>>>>");
		return objects.getContent();
	}
}
