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
import net.brilliance.domain.entity.system.DigitalDashboard;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.dashboard.DashboardService;

@RestController
@RequestMapping("/" + ControllerConstants.REQUEST_ADMIN_DASHBOARD)
public class DashboardRestController extends BaseRestController{
	@Inject 
	private DashboardService businessManager;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<DigitalDashboard> onListBizObjects(HttpServletRequest request, HttpServletResponse response, Model model) {
		cLog.info("DashboardRestController::onListBizObjects: Loading data ...>>>>>>");
		PageRequest pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
		SearchParameter searchParameter = SearchParameter.getInstance()
				.setPageable(pageRequest);
		Page<DigitalDashboard> objects = businessManager.getObjects(searchParameter);
		cLog.info("DashboardRestController::onListBizObjects: Data is loaded >>>>>>");
		return objects.getContent();
	}
}
