package net.brilliance.controller.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.GUUISequenceGenerator;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.system.DigitalDashboard;
import net.brilliance.domain.entity.system.DigitalDashlet;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.SequenceType;
import net.brilliance.model.SelectItem;
import net.brilliance.service.api.dashboard.DashboardService;

@Controller
@RequestMapping("/" + ControllerConstants.REQUEST_ADMIN_DASHBOARD)
public class DashboardController extends BaseController { 
	private static final String PAGE_CONTEXT_PREFIX = ControllerConstants.CONTEXT_WEB_PAGES + "admin/dashboard/dashboard";

	@Inject
	private DashboardService businessManager;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		return PAGE_CONTEXT_PREFIX + "Browse";
	}

	/**
	 * Create a new dash-board and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		String guuId = GUUISequenceGenerator.getInstance().nextGUUIdString(SequenceType.DASHBOARD.getType());
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		model.addAttribute(ControllerConstants.PARAM_CREATE_OTHER, session.getAttribute(ControllerConstants.PARAM_CREATE_OTHER));

		session.removeAttribute(ControllerConstants.PARAM_CREATE_OTHER);

		DigitalDashboard newDashboard = DigitalDashboard
		.builder()
		.serial(guuId)
		.build();
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, newDashboard);
		return PAGE_CONTEXT_PREFIX + "Edit";
	}

	/**
	 * Create/update a contact.
	*/
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@Valid DigitalDashboard uiBizObject, BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, Locale locale) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, uiBizObject);
			return PAGE_CONTEXT_PREFIX + "Edit";
		}

		if (!CommonUtility.isNull(uiBizObject.getParent()) && CommonUtility.isNull(uiBizObject.getParent().getId())){
			uiBizObject.setParent(null);
		}

		cLog.info("Creating/updating dashboard");
		
		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("general_save_success", new Object[] {}, locale)));

		businessManager.saveOrUpdate(uiBizObject);

		//TODO: Pay attention please
		if (super.isContinueOther(model, request)){
			return ControllerConstants.REDIRECT_PREFIX + ControllerConstants.REQUEST_ADMIN_DASHBOARD + "/create";
		}

		return PAGE_CONTEXT_PREFIX + "Browse";
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessManager.getObject(id));
			return PAGE_CONTEXT_PREFIX + "Edit";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch business object of catalogue subtype with id: " + id);

		model.addAttribute(ControllerConstants.FETCHED_OBJECT, businessManager.getObject(id));
		
		return PAGE_CONTEXT_PREFIX + "Show";
	}

	/**
	 * Create a new department and place in Model attribute.
	 */
	@RequestMapping(value = "/createDashlet/{dashboardId}", method = RequestMethod.GET)
	public String createDashletForm(@PathVariable("dashboardId") Long dashboardId, Model model) {
		String guuId = GUUISequenceGenerator.getInstance().nextGUUIdString(SequenceType.DASHLET.getType());

		//Pick up the dash board object and put in dashlet
		DigitalDashboard dashboard = businessManager.getObject(CommonUtility.toLong(dashboardId));

		DigitalDashlet newDashlet = DigitalDashlet
		.builder()
		.name(guuId)
		.dashboard(dashboard)
		.build();
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, newDashlet);

		return PAGE_CONTEXT_PREFIX + "Edit";
	}

	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		List<SelectItem> suggestedItems = new ArrayList<>();
		Page<DigitalDashboard> fetchedObjects = this.businessManager.searchObjects(keyword, null);
		for (DigitalDashboard dept : fetchedObjects.getContent()) {
			suggestedItems.add(new SelectItem(dept.getId().intValue(), dept.getSerial(), dept.getName()));
		}
		return suggestedItems;
	}

	@Override
	protected String performSearch(SearchParameter params) {
		Map<String, Object> parameters = new HashMap<>();
		Page<DigitalDashboard> pageContentData = businessManager.search(parameters);
		params.getModel().addAttribute(ControllerConstants.FETCHED_OBJECT, pageContentData);
		/*HttpSession session = super.getSession();
		session.setAttribute(CommonConstants.CACHED_PAGE_MODEL, params.getModel());*/
		//return gson.toJson(pageContentData.getContent());
		return PAGE_CONTEXT_PREFIX + "Browse :: result-teable " + new Gson().toJson(pageContentData.getContent());
	}
}
