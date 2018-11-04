package net.brilliance.controller.controller.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.manager.catalog.CatalogManager;
import net.brilliance.model.SelectItem;
import net.brilliance.service.helper.InventoryCatalogDataDispatchHelper;

@Controller
@RequestMapping("/" + ControllerConstants.REQUEST_MAPPING_CATALOG)
public class CatalogueController extends BaseController { 
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "general/catalog/";

	@Autowired
	private CatalogManager businessManager;

	@Inject 
	private InventoryCatalogDataDispatchHelper inventoryCatalogDataDispatchHelper;

	/**
	 * Import catalogs.
   */
	@Override
	protected String performImport(Model model, HttpServletRequest request) {
		cLog.info("Importing catalogs .....");
		constructData();
		cLog.info("Leave importing catalogs!");
		return "pages/general/catalog/catalogBrowse";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		return "pages/general/catalog/catalogBrowse";
	}

	@Override
	protected String performListing(Model model, HttpServletRequest request) {
		System.out.println("Catalog performListing");
		return "pages/general/catalog/catalogBrowse";
	}

	@Override
	protected void constructData() {
		cLog.info("Constructing catalogues data.....");

		List<Catalogue> catalogues;
		try {
			catalogues = inventoryCatalogDataDispatchHelper.dispatchCatalogues();
			for (Catalogue catalog :catalogues){
				if (!businessManager.getByCode(catalog.getCode()).isPresent()){
					businessManager.save(catalog);
				}
			}
		} catch (EcosysException e) {
			cLog.error("Import catalogues", e);
		}
		cLog.info("Leave constructing catalogues data!");
	}

	@Override
	protected void onPostConstruct() {
		super.postConstructData("Setup catalogues");
	}

	/**
	 * Export catalogs.
   */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String exports(Model model, HttpServletRequest request) {
		cLog.info("Exporting catalogs .....");
		return "pages/general/catalog/catalogBrowse";
	}

	/**
	 * Create a new department and place in Model attribute.
	 */
	@RequestMapping(value="/create", method=RequestMethod.GET)
    public String createForm(Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, Catalogue.builder().build());
        return PAGE_CONTEXT + "catalogEdit";
    }

	/**
	 * Create/update a contact.
	*/
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@Valid Catalogue department, BindingResult bindingResult,
			Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, department);
			return PAGE_CONTEXT + "catalogEdit";
		}

		if (!CommonUtility.isNull(department.getParent()) && CommonUtility.isNull(department.getParent().getId())){
			department.setParent(null);
		}

		cLog.info("Creating/updating department");
		
		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("general_save_success", new Object[] {}, locale)));

		businessManager.save(department);
		String ret = "redirect:/catalog/"+department.getId().toString();
		return ret;//"redirect:/department/" + UrlUtil.encodeUrlPathSegment(department.getId().toString(), httpServletRequest);
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessManager.get(id));
			return PAGE_CONTEXT + "catalogEdit";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch department with id: " + id);

		Catalogue fetchedObject = businessManager.get(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);
		
		return PAGE_CONTEXT + "catalogShow";
	}

	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		List<SelectItem> suggestedItems = new ArrayList<>();
		List<Catalogue> fetchedObjects = this.businessManager.search(keyword);
		for (Catalogue dept : fetchedObjects) {
			suggestedItems.add(new SelectItem(dept.getId().intValue(), dept.getCode(), dept.getName()));
		}
		return suggestedItems;
	}

	@Override
	protected String performSearch(SearchParameter params) {
		Map<String, Object> parameters = new HashMap<>();
		Page<Catalogue> pageContentData = businessManager.search(parameters);
		params.getModel().addAttribute("catalogues", pageContentData);
		/*HttpSession session = super.getSession();
		session.setAttribute(CommonConstants.CACHED_PAGE_MODEL, params.getModel());*/
		Gson gson = new Gson();
		//return gson.toJson(pageContentData.getContent());
		return "pages/general/catalog/catalogBrowse :: result-teable " + gson.toJson(pageContentData.getContent());
	}

	protected List performSearchObjects(SearchParameter params){
		Map<String, Object> parameters = new HashMap<>();
		Page<Catalogue> pageContentData = businessManager.search(parameters);
		params.getModel().addAttribute("catalogues", pageContentData);
		return pageContentData.getContent();
	}
}
