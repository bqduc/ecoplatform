package net.brilliance.controller.admin;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

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
import net.brilliance.common.ListUtility;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.databridge.GlobalDataInitializer;
import net.brilliance.domain.entity.admin.BusinessUnit;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.admin.BusinessUnitService;

@Controller
@RequestMapping(ControllerConstants.REQUEST_URI_BUSINESS_UNIT)
public class BusinessUnitController extends BaseController {
	private static final String PAGE_CONTEXT_PREFIX = ControllerConstants.CONTEXT_WEB_PAGES + "admin/businessUnit";

	@Inject
	private BusinessUnitService businessServiceManager;

	@Inject 
	private GlobalDataInitializer globalDataInitializer;

	@RequestMapping(path={"/", ""}, method=RequestMethod.GET)
	public String goHome(){
		return getDefaultPage();
	}

	@RequestMapping(path="/search", method=RequestMethod.POST)
	@ResponseBody
	public String onSearch(@RequestBody(required = false) SearchParameter searchParams, Model model, Pageable pageable){
		if (CommonUtility.isEmpty(searchParams.getPageable())) {
			searchParams.setPageable(pageable);
		}
		Page<BusinessUnit> results = businessServiceManager.getObjects(searchParams);
		List<BusinessUnit> expectedResults = results.getContent().subList(0, 1550);
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonBizObjects = gson.toJson(expectedResults);
		return jsonBizObjects;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String onShow(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch business unit object with id: " + id);

		BusinessUnit fetchedObject = businessServiceManager.getObject(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);
		
		return PAGE_CONTEXT_PREFIX + "Vieww";
	}

	/**
	 * Create a new business unit object and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, new BusinessUnit());
		return PAGE_CONTEXT_PREFIX + "Edit";
	}

	/**
	 * Import business unit objects.
	 */
	@Override
	protected String performImport(Model model, HttpServletRequest request) {
		InputStream inputStream = null;
		Map<Object, Object> params = null;
		try {
			inputStream = CommonUtility.getClassPathResourceInputStream("/config/data/data-vpex-repaired.xlsx");
			params = ListUtility.createMap("dataStream", inputStream, "dataSheetName", "noname", "startedIndex", Integer.valueOf(1));
			businessServiceManager.imports(params);
		} catch (Exception e) {
			cLog.error(CommonUtility.getStackTrace(e));
		} finally{
			try {
				CommonUtility.closeInputStream(inputStream);
			} catch (Exception e2) {
				cLog.error(CommonUtility.getStackTrace(e2));
			}
		}
		return PAGE_CONTEXT_PREFIX + "Browse";
	}

	/**
	 * Retrieve the business unit with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessServiceManager.getObject(id));
		return PAGE_CONTEXT_PREFIX + "Edit";
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getDefaultPage(){
		if (1 > this.businessServiceManager.count()){
			setupFakeObjects();
		}
		return PAGE_CONTEXT_PREFIX + "Browse";
	}

	private void setupFakeObjects(){
		this.globalDataInitializer.constructBusinessUnits();
	}
}
