package net.brilliance.controller.dashboard;

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
import net.brilliance.domain.entity.system.DigitalDashlet;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.dashboard.DashletService;

@RestController
@RequestMapping("/" + ControllerConstants.REQUEST_ADMIN_DASHLET)
public class DashletRestController extends BaseRestController{
	@Inject 
	private DashletService businessManager;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<DigitalDashlet> listBizObjects(HttpServletRequest request, HttpServletResponse response, Model model) {
		cLog.info("DashletRestController::listBizObjects enter...>>>>>>");
		PageRequest pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
		SearchParameter searchParameter = SearchParameter.getInstance()
				.setPageable(pageRequest);
		Page<DigitalDashlet> objects = businessManager.getObjects(searchParameter);
		cLog.info("DashletRestController::listBizObjects leave...>>>>>>");
		return objects.getContent();
	}
}
