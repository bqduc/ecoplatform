package net.brilliance.controller.dmx;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.brilliance.common.CommonUtility;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.dmx.Inventory;
import net.brilliance.framework.model.ExecutionContext;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.dmx.InventoryService;

@Controller
@RequestMapping(ControllerConstants.URI_INVENTORY)
public class InventoryController extends BaseController {
	private static final String PAGE_CONTEXT_PREFIX = ControllerConstants.CONTEXT_WEB_PAGES + "dmx/inventory";

	@Inject
	private InventoryService businessService;

	@RequestMapping(path="/", method=RequestMethod.GET)
	public String viewHome(){
		return PAGE_CONTEXT_PREFIX + PAGE_POSTFIX_BROWSE; 
	}

	@RequestMapping(path="/search", method=RequestMethod.POST)
	@ResponseBody
	public String search(@RequestBody(required = false) SearchParameter searchParams, Model model, Pageable pageable){
		Page<Inventory> searchResults = businessService.getObjects(searchParams);
		List<Inventory> expectedResults = searchResults.getContent().subList(0, 150);
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonBizObjects = gson.toJson(expectedResults);
		return jsonBizObjects;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch employee object with id: " + id);

		Inventory fetchedObject = businessService.getObject(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);
		
		return PAGE_CONTEXT_PREFIX + PAGE_POSTFIX_SHOW;
	}

	/**
	 * Create a new employee object and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, Inventory.builder().build());
		return PAGE_CONTEXT_PREFIX + PAGE_POSTFIX_EDIT;
	}

	/**
	 * Import employee objects.
	 */
	@Override
	protected String performImport(Model model, HttpServletRequest request) {
		InputStream inputStream = null;
		try {
			inputStream = CommonUtility.getClassPathResourceInputStream("/config/data/data-vpex-repaired.xlsx");
			businessService.deploy(ExecutionContext.builder().build());//.importEmployees(inputStream, "chinh thuc", 1);
		} catch (Exception e) {
			cLog.error(CommonUtility.getStackTrace(e));
		} finally{
			try {
				CommonUtility.closeInputStream(inputStream);
			} catch (Exception e2) {
				cLog.error(CommonUtility.getStackTrace(e2));
			}
		}
		return PAGE_CONTEXT_PREFIX + PAGE_POSTFIX_BROWSE;
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessService.getObject(id));
		return PAGE_CONTEXT_PREFIX + PAGE_POSTFIX_EDIT;
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}
