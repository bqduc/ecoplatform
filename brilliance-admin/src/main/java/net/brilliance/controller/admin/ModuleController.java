package net.brilliance.controller.admin;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.brilliance.common.CommonConstants;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.security.Module;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.manager.auth.ModuleManager;
import net.brilliance.model.SelectItem;

@RequestMapping("/" + CommonConstants.CONTROLLER_MODULE)
@Controller
public class ModuleController extends BaseController {
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "admin/" + CommonConstants.CONTROLLER_MODULE;
	private static final String DEFAULT_PAGED_REDIRECT = REDIRECT + CommonConstants.CONTROLLER_MODULE + "/list/1";

	@Inject
	private ModuleManager serviceManager;

	/**
	 * List all.
	 */
	@RequestMapping(value = { "/list", "" }, method = RequestMethod.GET)
	public String list(Model model) {
		cLog.info("Listing all objects");

		if (serviceManager.count() < 1) {
			// serviceManager.createDummyObjects();
		}
		return DEFAULT_PAGED_REDIRECT;
	}

	@RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
	public String listByPage(@PathVariable Integer pageNumber, Model model) {
		cLog.info("Listing objects at: " + Calendar.getInstance().getTime());

		Page<Module> pageContent = serviceManager.getList(pageNumber);
		int current = pageContent.getNumber() + 1;
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE);
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, pageContent.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, pageContent);
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		return PAGE_CONTEXT + "Browse";
	}

	/**
	 * Retrieve the object with the specified id.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Getting object with id: " + id);

		Module fetchedObject = serviceManager.get(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);

		return PAGE_CONTEXT + "Show";
	}

	/**
	 * Retrieve the module with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		cLog.info("Edit object with id: " + id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, serviceManager.get(id));
		loadDependencies(model);
		return PAGE_CONTEXT + "Edit";
	}

	/**
	 * Create a new module and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, new Module());
		loadDependencies(model);
		return PAGE_CONTEXT + "Edit";
	}

	/**
	 * Create/update a module.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid Module userObject, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale, @RequestParam(value = "file", required = false) MultipartFile file) {

		if (bindingResult.hasErrors()) {
			model.addAttribute(ControllerConstants.FETCHED_OBJECT, userObject);
			return PAGE_CONTEXT + "Edit";
		}

		cLog.info("Creating/updating module");

		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("product_save_success", new Object[] {}, locale)));

		if (null != userObject.getParent() && userObject.getParent().getId()==null){
			userObject.setParent(null);
		}

		serviceManager.save(userObject);

		return REDIRECT + CommonConstants.CONTROLLER_MODULE + "/" + userObject.getId().toString();
	}

	/**
	 * Deletes the module with the specified id.
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Long id, Model model, Locale locale) {
		cLog.info("Deleting module with id: " + id);
		Module module = serviceManager.get(id);

		if (module != null) {
			serviceManager.delete(module);
			cLog.info("Module deleted successfully");

			//model.addAttribute("message", new Message("success", messageSource.getMessage("product_delete_success", new Object[] {}, locale)));
		}

		return DEFAULT_PAGED_REDIRECT;
	}

	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		List<Module> suggestedCategories = serviceManager.search(keyword);
		return buildSelectedItems(suggestedCategories);
	}

	@Override
	protected SelectItem buildSelectableObject(Object beanObject) {
		if (null==beanObject || !(beanObject instanceof Module))
			return null;

		Module module = (Module)beanObject;
		return new SelectItem()
				.setId(module.getId())
				.setCode(module.getName())
				.setName(module.getName());
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}
