package net.brilliance.manager.hc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.hc.Employee;
import net.brilliance.repository.hc.EmployeeRepository;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long id) {
		return employeeRepository.findOne(id);
	}

	@Override
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

}
