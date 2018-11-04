package net.brilliance.controller.dmx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.GUUISequenceGenerator;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.SequenceType;
import net.brilliance.model.SelectItem;
import net.brilliance.runnable.UpdateSystemSequenceThread;
import net.brilliance.service.api.dmx.EnterpriseService;

@Controller
@RequestMapping("/" + ControllerConstants.REQ_MAPPING_ENTERPRISE)
public class EnterpriseController extends BaseController { 
	private static final String PAGE_CONTEXT_PREFIX = ControllerConstants.CONTEXT_WEB_PAGES + "dmx/enterprise";

	@Inject
  private TaskExecutor taskExecutor;
	
	@Inject
	private ApplicationContext applicationContext;

	@Autowired
	private EnterpriseService businessManager;


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

	/**
	 * Create a new department and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		String guuId = GUUISequenceGenerator.getInstance().nextGUUIdString(SequenceType.CATALOG_SUBTYPE.getType());

		Enterprise newEnterprise = Enterprise
		.builder()
		.code(guuId)
		.build();
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, newEnterprise);
		return PAGE_CONTEXT_PREFIX + "Edit";
	}

	/**
	 * Create/update a contact.
	*/
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@Valid Enterprise uiBizObject, BindingResult bindingResult,
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

		UpdateSystemSequenceThread updateSystemSequenceThread = applicationContext.getBean(UpdateSystemSequenceThread.class, uiBizObject.getCode());
		taskExecutor.execute(updateSystemSequenceThread);
		//TODO: Pay attention please
		String ret = "redirect:/catalogSubtype/"+uiBizObject.getId().toString();
		return ret;//"redirect:/department/" + UrlUtil.encodeUrlPathSegment(department.getId().toString(), httpServletRequest);
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

	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		List<SelectItem> suggestedItems = new ArrayList<>();
		Page<Enterprise> fetchedObjects = this.businessManager.searchObjects(keyword, null);
		for (Enterprise dept : fetchedObjects.getContent()) {
			suggestedItems.add(new SelectItem(dept.getId().intValue(), dept.getCode(), dept.getName()));
		}
		return suggestedItems;
	}

	@Override
	protected String performSearch(SearchParameter params) {
		Map<String, Object> parameters = new HashMap<>();
		Page<Enterprise> pageContentData = businessManager.search(parameters);
		params.getModel().addAttribute(ControllerConstants.FETCHED_OBJECT, pageContentData);
		/*HttpSession session = super.getSession();
		session.setAttribute(CommonConstants.CACHED_PAGE_MODEL, params.getModel());*/
		Gson gson = new Gson();
		//return gson.toJson(pageContentData.getContent());
		return PAGE_CONTEXT_PREFIX + "Browse :: result-teable " + gson.toJson(pageContentData.getContent());
	}

	/*protected List performSearchObjects(SearchParameter params){
		Map<String, Object> parameters = new HashMap<>();
		Page<Catalog> pageContentData = businessManager.search(parameters);
		params.getModel().addAttribute("catalogues", pageContentData);
		return pageContentData.getContent();
	}*/
}
