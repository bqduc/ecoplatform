/**
 * 
 */
package net.brilliance.controller.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.config.Configuration;
import net.brilliance.domain.entity.general.Category;
import net.brilliance.domain.entity.general.Department;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.logging.CommonLoggingService;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.helper.WebServicingHelper;
import net.brilliance.manager.catalog.CategoryManager;
import net.brilliance.manager.catalog.impl.DepartmentManager;
import net.brilliance.manager.configuration.ConfigurationManager;
import net.brilliance.model.Bucket;
import net.brilliance.model.SelectItem;
import net.brilliance.service.helper.GlobalDataServiceHelper;
import net.sf.ehcache.hibernate.management.impl.BeanUtils;

/**
 * @author ducbq
 *
 */
@SuppressWarnings("rawtypes")
public abstract class BaseController {
	protected static final String PAGE_POSTFIX_BROWSE = "Browse";
	protected static final String PAGE_POSTFIX_SHOW = "Show";
	protected static final String PAGE_POSTFIX_EDIT = "Edit";

	protected static final String REDIRECT =	"redirect:/";

	@Inject
	protected DepartmentManager departmentServiceManager;

	@Inject
	protected CategoryManager categoryServiceManager;

	@Autowired
	protected MessageSource messageSource;

	@Inject
	private ConfigurationManager configurationManager;

	@Inject
	private GlobalDataServiceHelper globalDataServiceHelper;

	@Inject 
	protected CommonLoggingService cLog;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	protected abstract String performSearch(SearchParameter params);

	protected List performSearchObjects(SearchParameter params){
		return null;
	}

  //@PostConstruct
  protected void init() {
  	String className = this.getClass().getName();
  	cLog.info("Enter post construction of " + className);
  	onPostConstruct();
  	cLog.info("Leave post construction of " + className);
  }

	@PostMapping(value = "/search/query", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String search(@RequestBody(required = false) SearchParameter params, Model model, Pageable pageable) {
		cLog.info("Enter search/query");
		return performSearch(
				params
				.setPageable(pageable)
				.setModel(model));
	}

	@RequestMapping(path="/searchex/query", method=RequestMethod.GET)
	public List query(@RequestBody(required = false) SearchParameter params, Model model, Pageable pageable) {
		cLog.info("Enter search/query");
		return performSearchObjects(
				params
				.setPageable(pageable)
				.setModel(model));
	}

	@RequestMapping(value = "/searchAction", method = RequestMethod.POST)
	public @ResponseBody String onSearch(@RequestBody(required = false) SearchParameter params, Model model, Pageable pageable){
		Gson gson = new GsonBuilder().serializeNulls().create();
		List searchResults = performSearchObjects(
				params
				.setPageable(pageable)
				.setModel(model));
		String jsonBizObjects = gson.toJson(searchResults);
		//String contentJson = "{\"iTotalDisplayRecords\":" + searchResults.size() + "," + "\"data\":" + jsonBizObjects + "}";
		String contentJson = jsonBizObjects;
		return contentJson;
	}

	@RequestMapping(value = "/suggest", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggest(@RequestParam("term") String keyword, HttpServletRequest request) {
		cLog.info("Enter keyword: " + keyword);
		List<SelectItem> suggestedItems = suggestItems(keyword);
		if (CommonUtility.isNull(suggestedItems)){
			suggestedItems = new ArrayList<>();
		}
		return suggestedItems;
	}

	protected List<SelectItem> suggestItems(String keyword) {
		return null;
	}

	@RequestMapping(value = "/suggestDepartment", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggestDepartment(@RequestParam("term") String keyword, HttpServletRequest request) {
		cLog.info("Enter keyword for category: " + keyword);
		Page<Department> suggestedCategories = departmentServiceManager.search(WebServicingHelper.createSearchParameters(keyword, null, null));
		return buildCategorySelectedItems(suggestedCategories.getContent());
	}

	@RequestMapping(value = "/suggestCategory", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggestCategory(@RequestParam("term") String keyword, HttpServletRequest request) {
		cLog.info("Enter keyword for category: " + keyword);
		Page<Category> suggestedCategories = categoryServiceManager.search(WebServicingHelper.createSearchParameters(keyword, null, null));
		return buildCategorySelectedItems(suggestedCategories.getContent());
	}

	protected List<SelectItem> buildCategorySelectedItems(List<?> objects){
		Long objectId = null;
		String objectCode = null, objectName = null;
		List<SelectItem> selectItems = new ArrayList<>();
		for (Object object :objects){
			objectId = (Long)BeanUtils.getBeanProperty(object, "id");
			objectCode = (String)BeanUtils.getBeanProperty(object, "code");
			objectName = (String)BeanUtils.getBeanProperty(object, "name");
			selectItems.add(new SelectItem(objectId, objectCode, objectName));
		}
		return selectItems;
	}

	protected List<SelectItem> buildCategorySelectedItems(List<?> objects, String idProperty, String displayCodeProperty, String displayNameProperty){
		Long objectId = null;
		String objectCode = null, objectName = null;
		List<SelectItem> selectItems = new ArrayList<>();
		for (Object object :objects){
			objectId = (Long)BeanUtils.getBeanProperty(object, idProperty);
			objectCode = (String)BeanUtils.getBeanProperty(object, displayCodeProperty);
			objectName = (String)BeanUtils.getBeanProperty(object, displayNameProperty);
			selectItems.add(new SelectItem(objectId, objectCode, objectName));
		}
		return selectItems;
	}

	protected List<SelectItem> buildSelectItems(List<?> objects){
		Long objectId = null;
		String objectCode = null, objectName = null;
		List<SelectItem> selectItems = new ArrayList<>();
		for (Object object :objects){
			objectId = (Long)BeanUtils.getBeanProperty(object, "id");
			objectCode = (String)BeanUtils.getBeanProperty(object, "code");
			objectName = (String)BeanUtils.getBeanProperty(object, "name");
			selectItems.add(new SelectItem(objectId, objectCode, objectName));
		}
		return selectItems;
	}

	protected List<SelectItem> buildSelectItems(List<?> objects, String keyProperty, String[] displayProperties){
		Long objectId = null;
		List<SelectItem> selectItems = ListUtility.createArrayList();
		Map<String, Object> displayValueMap = ListUtility.createMap();
		for (Object object :objects){
			objectId = (Long)BeanUtils.getBeanProperty(object, keyProperty);
			for (String displayProperty :displayProperties){
				displayValueMap.put(displayProperty, BeanUtils.getBeanProperty(object, displayProperty));
			}

			selectItems.add(SelectItem.buildInstance(objectId, displayValueMap));
		}
		return selectItems;
	}

	@RequestMapping(value = "/suggestObjects", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggestObject(@RequestParam("keyword") String keyword, HttpServletRequest request) {
		cLog.info("Enter keyword: " + keyword);
		List<SelectItem> suggestedItems = suggestItems(keyword);
		if (CommonUtility.isNull(suggestedItems)){
			suggestedItems = new ArrayList<>();
		}
		return suggestedItems;
	}

	protected List<SelectItem> buildSelectedItems(List<?> objects){
		List<SelectItem> selectItems = new ArrayList<>();
		for (Object object :objects){
			selectItems.add(this.buildSelectableObject(object));
		}
		return selectItems;
	}

	protected SelectItem buildSelectableObject(Object beanObject){
		return null;
	}

	protected String buildBaseURL(ServletRequest request){
		String baseUrl = String.format("%s://%s:%d",request.getScheme(),  request.getServerName(), request.getServerPort());
		return baseUrl;
	}

	protected void loadDependencies(Model model){
		//Load dependencies and put back to model
	}
	
	protected HttpSession getSession(){
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		return attr.getRequest().getSession();
	}

	protected String performListing(Model model, HttpServletRequest request){
		return CommonConstants.STRING_BLANK;
	}

	protected void onPostConstruct(){
	}

	protected void constructData(){
	}

	protected void postConstructData(String configuredName){
		Configuration config = configurationManager.getByName(configuredName);
		if (null==config||CommonUtility.BOOLEAN_STRING_TRUE.equalsIgnoreCase(config.getValue())){
			constructData();

			//Check and save back the configuration to mark that forum data has been setup
			if (null==config){
				config = Configuration.getInstance(configuredName, CommonUtility.BOOLEAN_STRING_FALSE);
			}else{
				config.setValue(CommonUtility.BOOLEAN_STRING_FALSE);
			}

			configurationManager.save(config);
		}
	}

	protected HttpServletRequest getCurrentHttpRequest(){
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes instanceof ServletRequestAttributes) {
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        return request;
    }

    cLog.debug("Not called in the context of an HTTP request");
    return null;
	}	

	protected Bucket doLoadExternalData(String resourceFileName, String[]sheetIds, int[] startedIndexes) throws EcosysException{
		Bucket bucket = null;
		Map<Object, Object>configParams = ListUtility.createMap();
		//Configure the started index for each sheet
		for (int idx = 0; idx < sheetIds.length; idx++){
			configParams.put(sheetIds[idx] + Bucket.PARAM_STARTED_ROW_INDEX, Integer.valueOf(startedIndexes[idx]));
		}
		configParams.put(Bucket.PARAM_DATA_SHEETS, ListUtility.arraysAsList(sheetIds));
		configParams.put(Bucket.PARAM_WORK_DATA_SHEET, sheetIds);

		try {
			bucket = globalDataServiceHelper.readSpreadsheetData(new ClassPathResource(resourceFileName).getInputStream(), configParams);
			//return (List<List<String>>)bucket.get(configParams.get(Bucket.PARAM_WORK_DATA_SHEET));
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		return bucket;
	}

	protected boolean isContinueOther(Model model, HttpServletRequest request){
		boolean isContinuedOther = false;
		isContinuedOther = "on".equalsIgnoreCase(request.getParameter(ControllerConstants.PARAM_CREATE_OTHER));
		if (true==isContinuedOther){
			request.getSession().setAttribute(ControllerConstants.PARAM_CREATE_OTHER, Boolean.TRUE);
		}
		return isContinuedOther;
	}

	/**
	 * Import business objects.
   */
	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public String imports(Model model, HttpServletRequest request) {
		cLog.info("Importing business objects .....");
		String importResults = performImport(model, request);
		cLog.info("Leave importing business objects!");
		return importResults;
	}

	protected String performImport(Model model, HttpServletRequest request){
		return ControllerConstants.DEAULT_PAGE_CONTEXT_PREFIX;
	}

	/**
	 * Export business objects.
   */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String exports(Model model, HttpServletRequest request) {
		cLog.info("Exporting business objects .....");
		String exportResults = performExport(model, request);
		cLog.info("Leaving exporting business objects .....");
		return exportResults;
	}

	protected String performExport(Model model, HttpServletRequest request){
		return ControllerConstants.DEAULT_PAGE_CONTEXT_PREFIX;
	}

	protected String buildRedirectShowBizObjectRoute(String controllerId, Long businessObjectId){
		return new StringBuilder(ControllerConstants.REDIRECT_PREFIX)
				.append(controllerId).append("/")
				.append(businessObjectId)
				.toString();
	}
}