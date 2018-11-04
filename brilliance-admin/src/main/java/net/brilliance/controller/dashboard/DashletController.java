package net.brilliance.controller.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.GUUISequenceGenerator;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.admin.Authority;
import net.brilliance.domain.entity.system.DigitalDashboard;
import net.brilliance.domain.entity.system.DigitalDashlet;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.SequenceType;
import net.brilliance.manager.auth.AuthorityManager;
import net.brilliance.model.SelectItem;
import net.brilliance.service.api.dashboard.DashboardService;
import net.brilliance.service.api.dashboard.DashletService;

@Controller
@RequestMapping("/" + ControllerConstants.REQUEST_ADMIN_DASHLET)
public class DashletController extends BaseController { 
	private static final String PAGE_CONTEXT_PREFIX = ControllerConstants.CONTEXT_WEB_PAGES + "admin/dashboard/dashlet";

	@Inject
	private DashletService businessManager;

	@Inject
	private DashboardService dashboardManager;

	@Inject
	private AuthorityManager serviceManager;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		return PAGE_CONTEXT_PREFIX + "Browse";
	}

	@Override
	protected String performListing(Model model, HttpServletRequest request) {
		System.out.println("catalogue subtypes performListing");
		return PAGE_CONTEXT_PREFIX + "Browse";
	}

	@Override
	protected void onPostConstruct() {
		super.postConstructData("Setup catalogue subtypes");
	}

	/**
	 * Export catalogs.
   */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String exports(Model model, HttpServletRequest request) {
		cLog.info("Exporting catalogue subtypes .....");
		return PAGE_CONTEXT_PREFIX + "Browse";
	}

	@RequestMapping(value = "/suggestDashboard", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggestDashboard(@RequestParam("term") String keyword, HttpServletRequest request) {
		cLog.info("Dashboard keyword: " + keyword);
		List<SelectItem> suggestedItems = new ArrayList<>();
		Page<DigitalDashboard> fetchedObjects = this.dashboardManager.searchObjects(keyword, null);
		for (DigitalDashboard entity : fetchedObjects.getContent()) {
			suggestedItems.add(new SelectItem(entity.getId(), entity.getName()));
		}
		return suggestedItems;
	}
	
	/**
	 * Create a new department and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		String guuId = GUUISequenceGenerator.getInstance().nextGUUIdString(SequenceType.DASHLET.getType());

		DigitalDashlet newDashlet = DigitalDashlet
		.builder()
		.name(guuId)
		.build();
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, newDashlet);
		
		this.prepareAuthorityList(model);
		return PAGE_CONTEXT_PREFIX + "Edit";
	}

	/**
	 * Create/update a contact.
	*/
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@Valid DigitalDashlet uiBizObject, BindingResult bindingResult, 
			Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, uiBizObject);
			return PAGE_CONTEXT_PREFIX + "Edit";
		}

		if (!CommonUtility.isNull(uiBizObject.getParent()) && CommonUtility.isNull(uiBizObject.getParent().getId())){
			uiBizObject.setParent(null);
		}

		cLog.info("Creating/updating catalogue subtype");
		
		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("general_save_success", new Object[] {}, locale)));

		businessManager.saveOrUpdate(uiBizObject);
		//systemSequenceManager.registerSequence(uiBizObject.getCode());

		//TODO: Pay attention please
		String ret = "redirect:" + ControllerConstants.REQUEST_ADMIN_DASHLET +"/" + uiBizObject.getId().toString();
		return ret;//"redirect:/department/" + UrlUtil.encodeUrlPathSegment(department.getId().toString(), httpServletRequest);
	}

	/**
	 * Create a new department and place in Model attribute.
	 */
	@RequestMapping(value = "/createDashlet/{dashboardId}", method = RequestMethod.GET)
	public String createDashletForm(@PathVariable("dashboardId") Long dashboardId, Model model) {
		String guuId = GUUISequenceGenerator.getInstance().nextGUUIdString(SequenceType.DASHLET.getType());

		//Pick up the dash board object and put in dashlet
		DigitalDashboard dashboard = dashboardManager.getObject(dashboardId);

		DigitalDashlet newDashlet = DigitalDashlet
		.builder()
		.serial(guuId)
		.dashboard(dashboard)
		.build();
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, newDashlet);

		this.prepareAuthorityList(model);
		return PAGE_CONTEXT_PREFIX + "Edit";
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessManager.getObject(id));
			this.prepareAuthorityList(model);
			return PAGE_CONTEXT_PREFIX + "Edit";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch business object of catalogue subtype with id: " + id);

		model.addAttribute(ControllerConstants.FETCHED_OBJECT, businessManager.getObject(id));
		
		return PAGE_CONTEXT_PREFIX + "Show";
	}

	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		List<SelectItem> suggestedItems = new ArrayList<>();
		Page<DigitalDashlet> fetchedObjects = this.businessManager.searchObjects(keyword, null);
		for (DigitalDashlet dept : fetchedObjects.getContent()) {
			suggestedItems.add(new SelectItem(dept.getId().intValue(), dept.getName(), dept.getAccessURI()));
		}
		return suggestedItems;
	}

	@Override
	protected String performSearch(SearchParameter params) {
		Map<String, Object> parameters = new HashMap<>();
		Page<DigitalDashlet> pageContentData = businessManager.search(parameters);
		params.getModel().addAttribute(ControllerConstants.FETCHED_OBJECT, pageContentData);
		/*HttpSession session = super.getSession();
		session.setAttribute(CommonConstants.CACHED_PAGE_MODEL, params.getModel());*/
		Gson gson = new Gson();
		//return gson.toJson(pageContentData.getContent());
		return PAGE_CONTEXT_PREFIX + "Browse :: result-teable " + gson.toJson(pageContentData.getContent());
	}

	private void prepareAuthorityList(Model model){
		DigitalDashlet digitalDashlet = (DigitalDashlet)model.asMap().get(ControllerConstants.FETCHED_OBJECT);
		List<Authority> authorityList = this.serviceManager.getAll();
		if (null != digitalDashlet){
			for (Authority au :digitalDashlet.getAccessedAuthorities()){
				authorityList.remove(au);
			}
		}
		model.addAttribute(net.brilliance.common.CommonConstants.AUTHORITY_LIST, authorityList);
	}
}
