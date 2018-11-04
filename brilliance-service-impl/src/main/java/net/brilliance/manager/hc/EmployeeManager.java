package net.brilliance.manager.hc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.domain.entity.config.Configuration;
import net.brilliance.domain.entity.hc.Employee;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.manager.BaseManager;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.manager.configuration.ConfigurationManager;
import net.brilliance.manager.configuration.DefaultConfigurations;
import net.brilliance.model.Bucket;
import net.brilliance.repository.hc.EmployeeRepository;
import net.brilliance.service.helper.GlobalDataServiceHelper;

@Service("employeeManager")
public class EmployeeManager extends BaseManager<Employee, Long> {
	private static final long serialVersionUID = -5456144769781208206L;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ConfigurationManager configurationManager;

	@Inject
	private GlobalDataServiceHelper dataRepositoryHelper;

	@Override
	protected BaseRepository<Employee, Long> getRepository() {
		return this.employeeRepository;
	}

	public long getCountByCode(String code){
		return this.employeeRepository.countByCode(code);
	}

	@SuppressWarnings("unchecked")
	public String importEmployees(InputStream inputStream, String sheetName, int startedIndex) throws EcosysException {
		List<String> dataParts = null;
		Bucket dataBucket = null;
		Employee emp = null;
		try {
			dataBucket = dataRepositoryHelper.readSpreadsheetData(inputStream, new String[]{sheetName});
			List<List<String>> dataStrings = (List<List<String>>)dataBucket.getBucketData().get(sheetName);
			for (int i = startedIndex; i < dataStrings.size(); ++i){
				dataParts = dataStrings.get(i);
				emp = this.parseEmployee(dataParts);
				this.employeeRepository.save(emp);
			}
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		return CommonConstants.STRING_BLANK;//Should return the import messages log
	}

	protected Employee parseEmployee(List<String> dataParts) throws EcosysException {
		Employee emp = new Employee();
		try {
			emp
			.setCode(dataParts.get(1))
			.setFirstName(dataParts.get(3))
			.setLastName(dataParts.get(2))
			.setEmail(dataParts.get(6))
			.setAddress(dataParts.get(7))
			;
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		System.out.println(dataParts.get(1)+"|\t"+dataParts.get(2)+" "+dataParts.get(3)+"|\t");
		return emp;
	}

	public void initDefaultData() throws EcosysException{
		Configuration configuration = configurationManager.getByName(DefaultConfigurations.setupEmployees.getConfigurationName());
		if (null != configuration && true==CommonUtility.getBooleanValue(configuration.getValue())){
			return;
		}

		try {
			String fileInputName = "salesman.xlsx";
			String sheetName = "sheet1";
			String startedRowIndex = "1"; //The first row often is column headers
			if (CommonUtility.isNotEmpty(configuration.getValueExtended())){
				String[] extendedValue = configuration.getValueExtended().split(CommonUtility.SEPARATORS[1]);
				fileInputName = extendedValue[0];
				sheetName = extendedValue[1];
				startedRowIndex = extendedValue[2];
			}
			this.importEmployees(new FileInputStream(fileInputName), sheetName, Integer.valueOf(startedRowIndex));
		} catch (Exception e) {
			throw new EcosysException(e);
		} finally {
			configuration = Configuration.getInstance(DefaultConfigurations.setupEmployees.getConfigurationName(), "true");
			configurationManager.save(Configuration.getInstance(DefaultConfigurations.setupEmployees.getConfigurationName(), "true"));
		}
	}
}
