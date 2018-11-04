package net.brilliance.repository.hc;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.hc.Employee;
import net.brilliance.framework.repository.BaseRepository;

@Repository("employeeRepository")
public interface EmployeeRepository extends BaseRepository<Employee, Long>{
	Long countByCode(String code);
}
