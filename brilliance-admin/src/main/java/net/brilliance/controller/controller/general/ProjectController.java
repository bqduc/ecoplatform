package net.brilliance.controller.controller.general;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.brilliance.common.CommonConstants;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.general.Project;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.manager.catalog.ProjectServiceManager;
import net.brilliance.model.SelectItem;
import net.brilliance.util.Message;

@RequestMapping("/" + ControllerConstants.REQUEST_MAPPING_PROJECT)
@Controller
public class ProjectController extends BaseController { 
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "general/project/";

	@Autowired
	private ProjectServiceManager businessServiceManager;

	/**
	 * List all projects.
     */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		cLog.info("Listing projects .....");

		if (businessServiceManager.count() < 1) {
			businessServiceManager.createDummyObjects();
		}
		/*List<Project> fetchedObjects = null;
		if (businessServiceManager.count() < 1) {
			fetchedObjects = businessServiceManager.createDummyObjects();
		}else {
			fetchedObjects = businessServiceManager.getAll();
		}
		request.getSession().setAttribute(ControllerConstants.FETCHED_OBJECTS, fetchedObjects);*/
		return "redirect:/project/listProject/1";
	}

	@RequestMapping(value = "/listProject/{pageNumber}", method = RequestMethod.GET)
	public String list(@PathVariable Integer pageNumber, Model model) {
		System.out.println("Listing projects at: " + Calendar.getInstance().getTime());
		
		Page<Project> page = businessServiceManager.getList(pageNumber);
		int current = page.getNumber() + 1; 
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE); 
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, page.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, page); 
		model.addAttribute("beginIndex", begin); 
		model.addAttribute("endIndex", end); 
		model.addAttribute("currentIndex", current);
		System.out.println("The list of clients is prepared. "); 
		System.out.println("Total clients: " + businessServiceManager.count());
		return PAGE_CONTEXT + "projectBrowse";

	}

	/**
	 * Create a new project and place in Model attribute.
	 */
	@RequestMapping(value="/create", method=RequestMethod.GET)
    public String createForm(Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, new Project());
        return PAGE_CONTEXT + "projectCreate";
    }

	/**
	 * Create/update a contact.
	*/
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Project project, BindingResult bindingResult,
			Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		
		System.out.println(project.getIssueDate() + ". " + project.getDateOfLicence());
		if (bindingResult.hasErrors()) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, project);
			return PAGE_CONTEXT + "projectCreate";
		}
		
		cLog.info("Creating/updating project");
		String object = messageSource.getMessage("label.object.project", new Object[] {}, locale);
		model.asMap().clear();
		redirectAttributes.addFlashAttribute("message", new Message(
				"success", messageSource.getMessage("label.general.saveSuccess", new Object[] {object}, locale)));

		businessServiceManager.save(project);
		String ret = "redirect:/project/";
		return ret;//"redirect:/project/" + UrlUtil.encodeUrlPathSegment(project.getId().toString(), httpServletRequest);
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessServiceManager.get(id));
			return PAGE_CONTEXT + "projectCreate";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch project with id: " + id);

		Project fetchedObject = businessServiceManager.get(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);
		
		return PAGE_CONTEXT + "projectShow";
	}

	@RequestMapping(value = "/attachment/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] downloadAttachment(@PathVariable("id") Long id) {
		Project fetchedObject = businessServiceManager.get(id);
		cLog.info("Downloading attachment for id: {} with size: {}", fetchedObject.getId(), 0);

		// Convert String image into byte[]
		byte[] imageBytes = new byte[]{};
		return imageBytes;
	}

	@RequestMapping(value="/refresh", method=RequestMethod.GET)
	public String refreshDashboard(Model model) {
		cLog.info("Refresh projects to get the latest project objects. ");
		return "redirect:/project/listProject/1";
	}

	@RequestMapping(value = "/getParents", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> getParents(@RequestParam("term") String contextSearch, HttpServletRequest request) {
		System.out.println("Enter the search !!!");
		return simulateSearchResult(request, contextSearch);
	}

	@SuppressWarnings("unchecked")
	private List<Project> simulateSearchResult(HttpServletRequest request, String tagName) {
		List<Project> result = new ArrayList<>();
		List<Project> fetchedObjects = (List<Project>)request.getSession().getAttribute(ControllerConstants.FETCHED_OBJECTS);
		// iterate a list and filter by tagName
		for (Project dept : fetchedObjects) {
			if (dept.getName().contains(tagName)) {
				result.add(dept);
			}
		}
		System.out.println("Result: " + result);
		return result;
	}

	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		List<Project> searchProjects = this.businessServiceManager.search(keyword);
		List<SelectItem> suggestedSelectItems = super.buildSelectItems(searchProjects);
		return suggestedSelectItems;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}
