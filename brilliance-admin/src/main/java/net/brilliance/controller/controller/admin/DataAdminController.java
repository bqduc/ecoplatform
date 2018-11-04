/**
 * 
 */
package net.brilliance.controller.controller.admin;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.deployment.InventoryDataDeployer;
import net.brilliance.domain.entity.config.Language;
import net.brilliance.domain.entity.config.LocalizedItem;
import net.brilliance.domain.entity.hc.Employee;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.manager.catalog.CatalogRegistryServiceHelper;
import net.brilliance.manager.configuration.ConfigurationServicesHelper;
import net.brilliance.model.Bucket;
import net.brilliance.service.api.contact.ContactService;
import net.brilliance.service.api.invt.ItemService;
import net.brilliance.service.api.invt.LanguageService;

/**
 * @author ducbq
 *
 */
@Controller
@RequestMapping(ControllerConstants.REQUEST_URI_DATA_ADMIN)
public class DataAdminController extends BaseController {
	private static boolean isDeploying = false;
	@Inject
	private ConfigurationServicesHelper configurationServicesHelper;

	@Inject
	private CatalogRegistryServiceHelper catalogRegistryServiceHelper;

	@Inject
	private ItemService itemService;

	@Inject
	private LanguageService languageService;

	@Inject 
	private InventoryDataDeployer inventoryDataDeployer;

	@Inject 
	private ContactService contactService;

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping({ "", "/index" })
	public String viewIndex(Model model, HttpServletRequest request) {
		cLog.info("Rendering data administration index page...");
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/importEmployees")
	public String onImportEmployees(Model model, HttpServletRequest request) {
		cLog.info("On import employees .........");
		try {
			List<Employee> employees = configurationServicesHelper.importEmployees();
			System.out.println("Imported employees: " + employees.size());
		} catch (Exception e) {
			cLog.error(CommonUtility.getStackTrace(e));
		}
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/importCatalogues")
	public String onImportCatalogues(Model model, HttpServletRequest request) {
		cLog.info("On import catalogues .........");
		String resourceFileName = "F:/Downloads/DANH-MỤC-HÀNG-HÓA-XUẤT-KHẨU-NHẬP-KHẨU-VIỆT-NAM-2015.xlsx";
		String sheet = "Danh muc-";
		Map<Object, Object> configParams = null;
		try {
			configParams = ListUtility.createMap();
			configParams.put(sheet + Bucket.PARAM_STARTED_ROW_INDEX, Integer.valueOf(2));
			configParams.put(Bucket.PARAM_DATA_SHEETS, new String[] { sheet });
			configParams.put(Bucket.PARAM_INPUT_STREAM, new FileInputStream(resourceFileName));
			configParams.put(Bucket.PARAM_WORK_DATA_SHEET, sheet);

			catalogRegistryServiceHelper.registerCataloguesFromExcel(configParams);
		} catch (Exception e) {
			cLog.error(CommonUtility.getStackTrace(e));
		}
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/deployData")
	public String deployData(Model model, HttpServletRequest request) {
		cLog.info("Enter deploy data.");
		try {
			doListLocalizedItems();
			inventoryDataDeployer.deployData(null);
		} catch (Exception e) {
			cLog.error(CommonUtility.getStackTrace(e));
		}
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/loadExternalData")
	public String loadExternalData(Model model, HttpServletRequest request) {
		cLog.info("Enter deploy data.");
		Bucket bucket = null;
		try {
			//bucket = super.doLoadExternalData("/config/data/salesman.xlsx", new String[]{"contact-data"}, new int[]{2});
			bucket = super.doLoadExternalData("/config/data/contact-data.xlsx", new String[]{"contact-data"}, new int[]{2});
			displayData(bucket);
		} catch (Exception e) {
			cLog.error(CommonUtility.getStackTrace(e));
		}
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/deployContactData")
	public String deployContactData(Model model, HttpServletRequest request) {
		cLog.info("Enter deploy data.");
		Bucket bucket = null; 
		if (true==isDeploying)
			return "pages/system/dataAdminBrowse";

		try {
			isDeploying = true;
			//bucket = super.doLoadExternalData("/config/data/salesman.xlsx", new String[]{"contact-data"}, new int[]{2});
			long started = System.currentTimeMillis();
			bucket = super.doLoadExternalData("/config/data/contact-data.xlsx", new String[]{"contact-data"}, new int[]{2});
			long duration = System.currentTimeMillis()-started;
			System.out.println("Duration: " + duration);
			contactService.deployContacts((List<List<String>>)bucket.get("contact-data"));
			isDeploying = false;
			//displayData(bucket);
		} catch (Exception e) {
			cLog.error(CommonUtility.getStackTrace(e));
		}
		return "pages/system/dataAdminBrowse";
	}

	protected void doListLocalizedItems(){
		Language language = languageService.getByCode("en");
		List<LocalizedItem> localizedItems = itemService.getLocalizedItems("CASE_STATUS", language);
		System.out.println(localizedItems);
	}

	private void displayData(Bucket bucket){
		List<List<String>> dataEntries = null;
		Map<Object, Object> dataMap = bucket.getBucketData();
		for (Object key :dataMap.keySet()){
			dataEntries = (List<List<String>>)dataMap.get(key);
			System.out.println(dataEntries);
		}

		System.out.println(bucket);
	}
}
