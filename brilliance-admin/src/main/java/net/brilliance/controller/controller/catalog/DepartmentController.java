package net.brilliance.controller.controller.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.general.Department;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.helper.WebServicingHelper;
import net.brilliance.manager.catalog.impl.DepartmentManager;
import net.brilliance.model.SelectItem;
import net.brilliance.model.Tag;

@RequestMapping("/" + ControllerConstants.REQUEST_MAPPING_DEPARTMENT)
@Controller
public class DepartmentController extends BaseController { 
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "general/catalog/";

	@Autowired
	private DepartmentManager businessServiceManager;

	/**
	 * List all departments.
     */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		cLog.info("Listing departments .....");

		//List<Department> fetchedObjects = null;
		if (businessServiceManager.count() < 1) {
			businessServiceManager.createDummyObjects();
		}/*else {
			fetchedObjects = businessServiceManager.getAll();
		}
		request.getSession().setAttribute(ControllerConstants.FETCHED_OBJECTS, fetchedObjects);*/
		//return "redirect:/department/listDepartment/1";
		return "pages/general/catalog/departmentBrowse";
	}

	@RequestMapping(value = "/listDepartment/{pageNumber}", method = RequestMethod.GET)
	public String list(@PathVariable Integer pageNumber, Model model) {
		Page<Department> page = businessServiceManager.getList(pageNumber);
		int current = page.getNumber() + 1; 
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE); 
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, page.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, page); 
		model.addAttribute("beginIndex", begin); 
		model.addAttribute("endIndex", end); 
		model.addAttribute("currentIndex", current);
		System.out.println("The list of clients is prepared. "); 
		System.out.println("Total clients: " + businessServiceManager.count());
		return PAGE_CONTEXT + "departmentBrowse";

	}

	/**
	 * Search departments.
   */
	@RequestMapping(value={"/search/{searchPattern}", "/search"}, method = RequestMethod.GET)
	public String search(@PathVariable Map<String, String> pathVariables, Model model) {
		cLog.info("Searching all measure units");
		Page<Department> pageContentData = null;
		if (pathVariables.containsKey("searchPattern")){
			cLog.info("Searching measure units with keyword: " + pathVariables.containsKey("searchPattern"));
			Short pageNumber = pathVariables.containsKey("pageNumber")?Short.valueOf(pathVariables.get("pageNumber")):(short)1;
			pageContentData = businessServiceManager.search(WebServicingHelper.createSearchParameters(pathVariables.get("searchPattern"), pageNumber, null));
		}else{
			pageContentData = businessServiceManager.getList(1);
		}
		int current = pageContentData.getNumber() + 1; 
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE); 
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, pageContentData.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, pageContentData); 
		model.addAttribute("beginIndex", begin); 
		model.addAttribute("endIndex", end); 
		model.addAttribute("currentIndex", current);

		return PAGE_CONTEXT + "departmentBrowse :: resultsList";
	}

	/**
	 * Create a new department and place in Model attribute.
	 */
	@RequestMapping(value="/create", method=RequestMethod.GET)
    public String createForm(Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, new Department());
        return PAGE_CONTEXT + "departmentEdit";
    }

	/**
	 * Create/update a contact.
	*/
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@Valid Department department, BindingResult bindingResult,
			Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, department);
			return PAGE_CONTEXT + "departmentEdit";
		}

		if (!CommonUtility.isNull(department.getParent()) && CommonUtility.isNull(department.getParent().getId())){
			department.setParent(null);
		}

		cLog.info("Creating/updating department");
		
		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("general_save_success", new Object[] {}, locale)));

		businessServiceManager.save(department);
		String ret = "redirect:/department/"+department.getId().toString();
		return ret;//"redirect:/department/" + UrlUtil.encodeUrlPathSegment(department.getId().toString(), httpServletRequest);
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessServiceManager.get(id));
			return PAGE_CONTEXT + "departmentEdit";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch department with id: " + id);

		Department fetchedObject = businessServiceManager.get(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);
		
		return PAGE_CONTEXT + "departmentShow";
	}

	@RequestMapping(value = "/attachment/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] downloadAttachment(@PathVariable("id") Long id) {
		Department fetchedObject = businessServiceManager.get(id);
		cLog.info("Downloading attachment for id: {} with size: {}", fetchedObject.getId(), 0);

		// Convert String image into byte[]
		byte[] imageBytes = new byte[]{};
		return imageBytes;
	}

	@RequestMapping(value="/refresh", method=RequestMethod.GET)
	public String refreshDashboard(Model model) {
		cLog.info("Refresh departments to get the latest department objects. ");
		return "redirect:/department/listDepartment/1";
	}

	@RequestMapping(value = "/getParents", method = RequestMethod.GET)
	@ResponseBody
	public List<Department> getParents(@RequestParam("term") String contextSearch, HttpServletRequest request) {
		System.out.println("Enter the search !!!");
		return simulateSearchResult(request, contextSearch);
	}

	private List<Department> simulateSearchResult(HttpServletRequest request, String tagName) {
		List<Department> result = new ArrayList<>();
		List<Department> fetchedObjects = (List<Department>)request.getSession().getAttribute(ControllerConstants.FETCHED_OBJECTS);
		// iterate a list and filter by tagName
		for (Department dept : fetchedObjects) {
			if (dept.getName().contains(tagName)) {
				result.add(dept);
			}
		}
		System.out.println("Result: " + result);
		return result;
	}

	@RequestMapping(value = "/get_tag_list", method = RequestMethod.GET)
	public @ResponseBody List<Tag> getTagList(@RequestParam("term") String keyword, HttpServletRequest request) {
		System.out.println("Enter: " + keyword);
		List<Tag> tagList = new ArrayList<>();
		List<Department> fetchedObjects = (List<Department>)request.getSession().getAttribute(ControllerConstants.FETCHED_OBJECTS);
		// iterate a list and filter by tagName
		for (Department dept : fetchedObjects) {
			if (dept.getName().contains(keyword)||dept.getCode().contains(keyword)) {
				tagList.add(new Tag(dept.getId().intValue(), dept.getCode(), dept.getName()));
			}
		}

		System.out.println("Leave: " + tagList.size());
		return tagList;
	}

	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		List<SelectItem> suggestedItems = new ArrayList<>();
		List<Department> fetchedObjects = this.businessServiceManager.search(keyword);
		//List<Department> fetchedObjects = (List<Department>)request.getSession().getAttribute(ControllerConstants.FETCHED_OBJECTS);
		// iterate a list and filter by tagName
		for (Department dept : fetchedObjects) {
			if (dept.getName().toLowerCase().contains(keyword.toLowerCase())||dept.getCode().toLowerCase().contains(keyword.toLowerCase())) {
				suggestedItems.add(new SelectItem(dept.getId().intValue(), dept.getCode(), dept.getName()));
			}
		}
		return suggestedItems;
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}

}
