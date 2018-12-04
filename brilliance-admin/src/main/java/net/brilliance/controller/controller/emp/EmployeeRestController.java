package net.brilliance.controller.controller.emp;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.controller.base.BaseRestController;
import net.brilliance.domain.entity.hc.Employee;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.manager.hc.EmployeeService;

@RestController
public class EmployeeRestController extends BaseRestController<Employee> {
	private final static String CACHED_EMPLOYEES = "cached.employees";
	protected Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(path = "/listEmployees", method = RequestMethod.GET)
	public List<Employee> onListEmployees() {
		return getBusinessObjects();
	}

	@RequestMapping(path = "/employees", method = RequestMethod.GET)
	public List<Employee> getAllEmployees() {
		return getBusinessObjects();
	}

	@RequestMapping(path="/employeesExt", method=RequestMethod.POST)
	public List<Employee> getEmployees(SearchParameter searchParams){
		List<Employee> results = employeeService.getEmployees();
		List<Employee> expectedResults = results.subList(0, 150);
		System.out.println(results);
		return expectedResults;
	}

	private List<Employee> getBusinessObjects(){
		List<Employee> businessObjects = (List<Employee>)this.httpSession.getAttribute(CACHED_EMPLOYEES);
		if (CommonUtility.isEmpty(businessObjects)){
			businessObjects = employeeService.getEmployees();
			this.httpSession.setAttribute(CACHED_EMPLOYEES, businessObjects);
		}

		return businessObjects;
	}
	/*@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public Employee getEmployeeById(@PathVariable("id") long id) {
		return employeeService.getEmployeeById(id);
	}*/
}
