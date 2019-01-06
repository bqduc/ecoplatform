/**
 * 
 */
package net.brilliance.converter;

import java.text.ParseException;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import net.brilliance.domain.entity.general.Department;
import net.brilliance.manager.catalog.impl.DepartmentManager;

/**
 * @author ducbq
 *
 */
@Component
public class DepartmentConverter implements Formatter<Department> {
	@Inject 
	private DepartmentManager departmentService;

	@Override
	public Department parse(String id, Locale locale) throws ParseException {
		Department parsedObject = null;
		try {
			Department result = departmentService.get(Long.valueOf(id));
			parsedObject = (result!=null)?result:new Department();
		} catch (Exception e) {
		}
		return parsedObject;
	}

	@Override
	public String print(Department department, Locale locale) {
		return String.valueOf(department.getId());
	}
}
