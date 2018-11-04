package net.brilliance.controller.controller.catalog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.general.Category;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.manager.catalog.CategoryManager;
import net.brilliance.model.SelectItem;

@RequestMapping("/" + ControllerConstants.REQUEST_MAPPING_CATEGORY)
@Controller
public class CategoryController extends BaseController {
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "general/catalog/";

	@Autowired
	private CategoryManager businessManager;

	/**
	 * List all categories.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		cLog.info("Listing categories ....");
		if (businessManager.count() < 1) {
			businessManager.createDummyObjects();
		}

		return REDIRECT + "category/list/1";
	}

	@RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
	public String listByPage(@PathVariable Integer pageNumber, Model model) {
		System.out.println("Listing clients at: " + Calendar.getInstance().getTime());

		Page<Category> page = businessManager.getList(pageNumber);
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE);
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, page.getTotalPages());
		if (page.hasContent()) {
			model.addAttribute(ControllerConstants.FETCHED_OBJECTS, page);
		} else {
			model.addAttribute(ControllerConstants.FETCHED_OBJECTS, new ArrayList<>());
		}
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		return PAGE_CONTEXT + "categoryBrowse";

	}

	/**
	 * Create a new category and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, new Category());
		loadDependencies(model);
		return PAGE_CONTEXT + "categoryEdit";
	}

	/**
	 * Create/update a contact.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid Category category, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale, @RequestParam(value = "file", required = false) MultipartFile file) {

		if (bindingResult.hasErrors()) {
			model.addAttribute(ControllerConstants.FETCHED_OBJECT, category);
			return PAGE_CONTEXT + "categoryEdit";
		}

		cLog.info("Creating/updating category");

		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("general_save_success", new Object[] {}, locale)));

		businessManager.save(category);
		return "redirect:/category/"+category.getId().toString();
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, businessManager.getById(id));
		loadDependencies(model);
		return PAGE_CONTEXT + "categoryEdit";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch project with id: " + id);

		Category fetchedObject = businessManager.getById(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);

		return PAGE_CONTEXT + "categoryShow";
	}

	@RequestMapping(value = "/attachment/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] downloadAttachment(@PathVariable("id") Long id) {
		Category currentEntity = businessManager.getById(id);
		cLog.info("Downloading attachment for id: {} with size: {}", currentEntity.getId(), 0);

		// Convert String image into byte[]
		byte[] imageBytes = new byte[] {};
		return imageBytes;
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public String refreshDashboard(Model model) {
		cLog.info("Refresh category to get the latest category objects. ");

		List<Category> fetchedObject = businessManager.getAll();
		model.addAttribute("fetchedObjects", fetchedObject);

		return PAGE_CONTEXT + "categoryBrowse";
	}

	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		List<SelectItem> suggestedItems = new ArrayList<>();
		List<Category> fetchedObjects = this.businessManager.search(keyword);
		// List<Department> fetchedObjects = (List<Department>)request.getSession().getAttribute(ControllerConstants.FETCHED_OBJECTS);
		// iterate a list and filter by tagName
		for (Category category : fetchedObjects) {
			if (category.getName().toLowerCase().contains(keyword.toLowerCase()) || category.getCode().toLowerCase().contains(keyword.toLowerCase())) {
				suggestedItems.add(new SelectItem(category.getId().intValue(), category.getCode(), category.getName()));
			}
		}
		return suggestedItems;
	}

	protected void loadDependencies(Model model) {
		// Load departments
		Category workingBizObject = (Category) model.asMap().get(ControllerConstants.FETCHED_OBJECT);
		if (!CommonUtility.isNull(workingBizObject) && !CommonUtility.isNull(workingBizObject.getDepartment())) {
			model.addAttribute("selectedDepartment", workingBizObject.getDepartment().getId());
		}
		model.addAttribute(ControllerConstants.LIST_DEPARTMENT, super.departmentServiceManager.getAll());
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}
