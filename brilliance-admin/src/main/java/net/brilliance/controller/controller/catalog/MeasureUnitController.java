package net.brilliance.controller.controller.catalog;

import java.util.ArrayList;
import java.util.Calendar;
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
import net.brilliance.domain.entity.general.MeasureUnit;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.helper.WebServicingHelper;
import net.brilliance.manager.catalog.MeasureUnitManager;
import net.brilliance.model.SelectItem;
import net.brilliance.model.Tag;

@RequestMapping("/" + ControllerConstants.REQUEST_MAPPING_MEASURE_UNIT)
@Controller
public class MeasureUnitController extends BaseController { 
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "general/catalog/";

	@Autowired
	private MeasureUnitManager businessService;

	/**
	 * List all measure units.
   */
	@RequestMapping(value={"/searchUnits/{searchPattern}", "/searchUnits"}, method = RequestMethod.GET)
	public String search(@PathVariable Map<String, String> pathVariables, Model model) {
		cLog.info("Searching all measure units");
		Page<MeasureUnit> pageContentData = null;
		if (pathVariables.containsKey("searchPattern")){
			cLog.info("Searching measure units with keyword: " + pathVariables.containsKey("searchPattern"));
			Short pageNumber = pathVariables.containsKey("pageNumber")?Short.valueOf(pathVariables.get("pageNumber")):(short)1;
			pageContentData = businessService.search(WebServicingHelper.createSearchParameters(pathVariables.get("searchPattern"), pageNumber, null));
		}else{
			pageContentData = businessService.getList(1);
		}
		int current = pageContentData.getNumber() + 1; 
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE); 
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, pageContentData.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, pageContentData); 
		model.addAttribute("beginIndex", begin); 
		model.addAttribute("endIndex", end); 
		model.addAttribute("currentIndex", current);

		return PAGE_CONTEXT + "measureUnitBrowse :: resultsList";
	}

	/**
	 * List all measure units.
   */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		cLog.info("Listing measure units .....");

		if (businessService.count() < 1) {
			businessService.setupMasterData();;
		}
		return PAGE_CONTEXT + "measureUnitBrowse";
		//return "redirect:/measureUnit/list/1";
	}

	@RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
	public String list(@PathVariable Integer pageNumber, Model model) {
		cLog.info("Listing measure units at: " + Calendar.getInstance().getTime());
		
		Page<MeasureUnit> page = businessService.getList(pageNumber);
		int current = page.getNumber() + 1; 
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE); 
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, page.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, page); 
		model.addAttribute("beginIndex", begin); 
		model.addAttribute("endIndex", end); 
		model.addAttribute("currentIndex", current);
		return PAGE_CONTEXT + "measureUnitBrowse";
	}

	/**
	 * Create a new measure unit and place in Model attribute.
	 */
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, new MeasureUnit());
  	return PAGE_CONTEXT + "measureUnitEdit";
  }

	/**
	 * Create/update a measure unit.
	*/
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@Valid MeasureUnit measureUnit, BindingResult bindingResult,
			Model model, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		if (bindingResult.hasErrors()) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, measureUnit);
			return PAGE_CONTEXT + "measureUnitEdit";
		}

		if (!CommonUtility.isNull(measureUnit.getParent()) && CommonUtility.isNull(measureUnit.getParent().getId())){
			measureUnit.setParent(null);
		}

		cLog.info("Creating/updating measure unit");
		
		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("general_save_success", new Object[] {}, locale)));

		businessService.save(measureUnit);
		String ret = "redirect:/measureUnit/"+measureUnit.getId().toString();
		return ret;//"redirect:/measureUnit/" + UrlUtil.encodeUrlPathSegment(measureUnit.getId().toString(), httpServletRequest);
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
  public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessService.get(id));
		return PAGE_CONTEXT + "measureUnitEdit";
  }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch measureUnit with id: " + id);

		MeasureUnit fetchedObject = businessService.get(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);
		
		return PAGE_CONTEXT + "measureUnitShow";
	}

	@RequestMapping(value = "/attachment/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] downloadAttachment(@PathVariable("id") Long id) {
		MeasureUnit fetchedObject = businessService.get(id);
		cLog.info("Downloading attachment for id: {} with size: {}", fetchedObject.getId(), 0);

		// Convert String image into byte[]
		byte[] imageBytes = new byte[]{};
		return imageBytes;
	}

	@RequestMapping(value="/refresh", method=RequestMethod.GET)
	public String refreshDashboard(Model model) {
		cLog.info("Refresh measureUnits to get the latest measureUnit objects. ");
		return "redirect:/measureUnit/list/1";
	}

	@RequestMapping(value = "/getParents", method = RequestMethod.GET)
	@ResponseBody
	public List<MeasureUnit> getParents(@RequestParam("term") String contextSearch, HttpServletRequest request) {
		cLog.info("Enter the search !!!");
		return simulateSearchResult(request, contextSearch);
	}

	private List<MeasureUnit> simulateSearchResult(HttpServletRequest request, String tagName) {
		List<MeasureUnit> result = new ArrayList<>();
		List<MeasureUnit> fetchedObjects = (List<MeasureUnit>)request.getSession().getAttribute(ControllerConstants.FETCHED_OBJECTS);
		// iterate a list and filter by tagName
		for (MeasureUnit dept : fetchedObjects) {
			if (dept.getName().contains(tagName)) {
				result.add(dept);
			}
		}
		cLog.info("Result: " + result);
		return result;
	}

	@RequestMapping(value = "/get_tag_list", method = RequestMethod.GET)
	public @ResponseBody List<Tag> getTagList(@RequestParam("term") String keyword, HttpServletRequest request) {
		cLog.info("Enter: " + keyword);
		List<Tag> tagList = new ArrayList<>();
		List<MeasureUnit> fetchedObjects = (List<MeasureUnit>)request.getSession().getAttribute(ControllerConstants.FETCHED_OBJECTS);
		// iterate a list and filter by tagName
		for (MeasureUnit dept : fetchedObjects) {
			if (dept.getName().contains(keyword)||dept.getCode().contains(keyword)) {
				tagList.add(new Tag(dept.getId().intValue(), dept.getCode(), dept.getName()));
			}
		}

		cLog.info("Leave: " + tagList.size());
		return tagList;
	}

	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		List<SelectItem> suggestedItems = new ArrayList<>();
		List<MeasureUnit> fetchedObjects = this.businessService.search(keyword);
		//List<MeasureUnit> fetchedObjects = (List<MeasureUnit>)request.getSession().getAttribute(ControllerConstants.FETCHED_OBJECTS);
		// iterate a list and filter by tagName
		for (MeasureUnit dept : fetchedObjects) {
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
