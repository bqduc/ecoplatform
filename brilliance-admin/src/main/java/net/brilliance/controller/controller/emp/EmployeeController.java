package net.brilliance.controller.controller.emp;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import net.brilliance.domain.entity.hc.Employee;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.manager.hc.EmployeeManager;
import net.brilliance.manager.hc.EmployeeService;

@Controller
@RequestMapping(ControllerConstants.REQUEST_URI_EMPLOYEE)
public class EmployeeController extends BaseController {
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "emp/";

	@Autowired
	private EmployeeManager businessManager;

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(path="/", method=RequestMethod.GET)
	public String goHome(){
		return "pages/emp/employeeBrowse";
	}

	@RequestMapping(path="/searchEmployee", method=RequestMethod.POST)
	@ResponseBody
	public String onSearchEmployees(@RequestBody(required = false) SearchParameter searchParams, Model model, Pageable pageable){
		List<Employee> results = employeeService.getEmployees();
		List<Employee> expectedResults = results.subList(0, 150);
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonBizObjects = gson.toJson(expectedResults);
		return jsonBizObjects;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String onShow(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch employee object with id: " + id);

		Employee fetchedObject = businessManager.get(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);
		
		return PAGE_CONTEXT + "employeeShow";
	}

	/**
	 * Create a new employee object and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, new Employee());
		return PAGE_CONTEXT + "employeeEdit";
	}

	/**
	 * Import employee objects.
	 */
	@Override
	protected String performImport(Model model, HttpServletRequest request) {
		InputStream inputStream = null;
		try {
			inputStream = CommonUtility.getClassPathResourceInputStream("/config/data/data-vpex-repaired.xlsx");
			businessManager.importEmployees(inputStream, "chinh thuc", 1);
		} catch (Exception e) {
			cLog.error(CommonUtility.getStackTrace(e));
		} finally{
			try {
				CommonUtility.closeInputStream(inputStream);
			} catch (Exception e2) {
				cLog.error(CommonUtility.getStackTrace(e2));
			}
		}
		return PAGE_CONTEXT + "employeeBrowse";
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessManager.get(id));
		return PAGE_CONTEXT + "employeeEdit";
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}
