/**
 * 
 */
package net.brilliance.manager.configuration;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.DateTimeUtility;
import net.brilliance.common.GenderTypeUtility;
import net.brilliance.domain.entity.config.Configuration;
import net.brilliance.domain.entity.config.ConfigurationDetail;
import net.brilliance.domain.entity.hc.Employee;
import net.brilliance.domain.model.enums.EmployeeConfigurationModel;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.global.WebAdminConstants;
import net.brilliance.manager.hc.EmployeeManager;
import net.brilliance.model.Bucket;
import net.brilliance.service.helper.GlobalDataServiceHelper;

/**
 * @author ducbq
 *
 */
@Service
public class ConfigurationServicesHelper {
	@Inject
	private ConfigurationManager configurationManager;

	@Inject
	private EmployeeManager employeeManager;

	@Inject
	private ConfigurationDetailManager configurationDetailManager;

	@Inject
	private GlobalDataServiceHelper globalDataServiceHelper;
	
	/*private List<String> malePatternList = new ArrayList<>();
	private List<String> femalePatternList = new ArrayList<>();*/

	public void importInventoryItems() throws EcosysException {
		try {
		} catch (Exception e) {
			throw new EcosysException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Employee> importEmployees() throws EcosysException {
		List<Employee> employees = new ArrayList<>();
		Bucket dataBucket = null;
		Employee emp = null;
		ClassPathResource classPathResource = null;
		try {
			classPathResource = new ClassPathResource(EmployeeConfigurationModel.DataSource.getModel());
			
			dataBucket = globalDataServiceHelper.readSpreadsheetData(
					new FileInputStream(classPathResource.getFile()), 
					new String[]{EmployeeConfigurationModel.DataSource.getDataPattern()});

			List<List<String>> dataStrings = (List<List<String>>)dataBucket.getBucketData().get(EmployeeConfigurationModel.DataSource.getDataPattern());
			for (int i = EmployeeConfigurationModel.DataSource.getIndex(); i < dataStrings.size(); ++i){
				emp = this.parseEmployee(dataStrings.get(i));
				try {
					if (1 > employeeManager.getCountByCode(emp.getCode())){
						employeeManager.save(emp);
						employees.add(emp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		return employees;
	}

	private Employee parseEmployee(List<String> dataParts) throws EcosysException {
		Employee emp = new Employee();
		Date defaultDate = DateTimeUtility.getSystemDate();
		try {
			emp
			.setCode(dataParts.get(EmployeeConfigurationModel.Code.getIndex()))
			.setFirstName(dataParts.get(EmployeeConfigurationModel.FirstName.getIndex()))
			.setLastName(dataParts.get(EmployeeConfigurationModel.LastName.getIndex()))
			.setEmail(dataParts.get(EmployeeConfigurationModel.Email.getIndex()))
			.setAddress(dataParts.get(EmployeeConfigurationModel.Address.getIndex()))
			.setPhone(dataParts.get(EmployeeConfigurationModel.Phone.getIndex()))
			.setPhoneMobile(dataParts.get(EmployeeConfigurationModel.PhoneMobile.getIndex()))
			.setEmail(dataParts.get(EmployeeConfigurationModel.Email.getIndex()))
			.setGender(GenderTypeUtility.getGenderType(dataParts.get(EmployeeConfigurationModel.Gender.getIndex())))

			.setDateOfBirth(DateTimeUtility.getDateInstance(dataParts.get(EmployeeConfigurationModel.DateOfBirth.getIndex()), defaultDate))
			.setPlaceOfBirth(dataParts.get(EmployeeConfigurationModel.PlaceOfBirth.getIndex()))

			.setMaritalStatus(dataParts.get(EmployeeConfigurationModel.MaritalStatus.getIndex()))
			.setEducation(dataParts.get(EmployeeConfigurationModel.Education.getIndex()))
			.setFreignLanguages(dataParts.get(EmployeeConfigurationModel.ForeignLanguage.getIndex()))
			.setWorkExperiences(dataParts.get(EmployeeConfigurationModel.WorkExperiences.getIndex()))

			.setNationality(dataParts.get(EmployeeConfigurationModel.Nationality.getIndex()))
			.setNationalId(dataParts.get(EmployeeConfigurationModel.NationalId.getIndex()))
			.setNationalIdIssueDate(DateTimeUtility.getDateInstance(dataParts.get(EmployeeConfigurationModel.NationalIdIssueDate.getIndex()), defaultDate))
			.setNationalIdIssuePlace(dataParts.get(EmployeeConfigurationModel.NationalIdIssuePlace.getIndex()))
			.setNationalIdExpiredDate(DateTimeUtility.getDateInstance(dataParts.get(EmployeeConfigurationModel.NationalIdExpiredDate.getIndex()), defaultDate))

			.setDescription(dataParts.get(EmployeeConfigurationModel.Info.getIndex()))
			;
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		return emp;
	}

	/*private GenderType getGenderType(String data){
		if (malePatternList.isEmpty()){
			malePatternList = Arrays.asList(EmployeeConfigurationModel.GenderTypeMale.getDataPattern().split(CommonConstants.SEMICOLON));
		}

		if (malePatternList.contains(data)){
			return GenderType.Male;
		}

		if (femalePatternList.isEmpty()){
			femalePatternList = Arrays.asList(EmployeeConfigurationModel.GenderTypeFemale.getDataPattern().split(CommonConstants.SEMICOLON));
		}

		if (femalePatternList.contains(data)){
			return GenderType.Female;
		}

		return GenderType.Unknown;
	}*/

	public Configuration setupEmployeeConfigurations() throws EcosysException {
		Configuration configuration = configurationManager.getByName(DefaultConfigurations.setupEmployees.getConfigurationName());
		if (null==configuration){
			configuration = new Configuration()
					.setGroup(WebAdminConstants.CONFIG_GROUP_GENERAL)
					.setName(DefaultConfigurations.setupEmployees.getConfigurationName())
					.setValue(CommonConstants.OPTION_DISABLED)
					.setValueExtended(new StringBuilder(
							EmployeeConfigurationModel.DataSource.getModel())
							.append(";")
							.append(EmployeeConfigurationModel.DataSource.getDataPattern())
							.append(";")
							.append(EmployeeConfigurationModel.DataSource.getIndex()).toString())
					;

			configurationManager.save(configuration);
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Code.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Code.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Code.getIndex()), 
							EmployeeConfigurationModel.Code.getDataPattern()));
		}
		
		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.FirstName.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.FirstName.getModel(), 
							String.valueOf(EmployeeConfigurationModel.FirstName.getIndex()), 
							EmployeeConfigurationModel.FirstName.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.LastName.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.LastName.getModel(), 
							String.valueOf(EmployeeConfigurationModel.LastName.getIndex()), 
							EmployeeConfigurationModel.LastName.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.DateOfBirth.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.DateOfBirth.getModel(), 
							String.valueOf(EmployeeConfigurationModel.DateOfBirth.getIndex()), 
							EmployeeConfigurationModel.DateOfBirth.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.PlaceOfBirth.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.PlaceOfBirth.getModel(), 
							String.valueOf(EmployeeConfigurationModel.PlaceOfBirth.getIndex()), 
							EmployeeConfigurationModel.PlaceOfBirth.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.NationalId.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.NationalId.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalId.getIndex()), 
							EmployeeConfigurationModel.NationalId.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.NationalIdIssueDate.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.NationalIdIssueDate.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdIssueDate.getIndex()), 
							EmployeeConfigurationModel.NationalIdIssueDate.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.NationalIdIssuePlace.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.NationalIdIssuePlace.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdIssuePlace.getIndex()), 
							EmployeeConfigurationModel.NationalIdIssuePlace.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.NationalIdExpiredDate.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.NationalIdExpiredDate.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdExpiredDate.getIndex()), 
							EmployeeConfigurationModel.NationalIdExpiredDate.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Address.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Address.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Address.getIndex()), 
							EmployeeConfigurationModel.Address.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Phone.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Phone.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Phone.getIndex()), 
							EmployeeConfigurationModel.Phone.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.PhoneMobile.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.PhoneMobile.getModel(), 
							String.valueOf(EmployeeConfigurationModel.PhoneMobile.getIndex()), 
							EmployeeConfigurationModel.PhoneMobile.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Email.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Email.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Email.getIndex()), 
							EmployeeConfigurationModel.Email.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Gender.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Gender.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Gender.getIndex()), 
							EmployeeConfigurationModel.Gender.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.MaritalStatus.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.MaritalStatus.getModel(), 
							String.valueOf(EmployeeConfigurationModel.MaritalStatus.getIndex()), 
							EmployeeConfigurationModel.MaritalStatus.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Education.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Education.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Education.getIndex()), 
							EmployeeConfigurationModel.Education.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Info.getModel())){
			configuration.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Info.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Info.getIndex()), 
							EmployeeConfigurationModel.Info.getDataPattern()));
		}

		configurationManager.save(configuration);

		/*if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.FirstName.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.FirstName.getModel(), 
							String.valueOf(EmployeeConfigurationModel.FirstName.getIndex()), 
							EmployeeConfigurationModel.FirstName.getDataPattern()));
		}
		
		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.LastName.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.LastName.getModel(), 
							String.valueOf(EmployeeConfigurationModel.LastName.getIndex()), 
							EmployeeConfigurationModel.LastName.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.DateOfBirth.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.DateOfBirth.getModel(), 
							String.valueOf(EmployeeConfigurationModel.DateOfBirth.getIndex()), 
							EmployeeConfigurationModel.DateOfBirth.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.PlaceOfBirth.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.PlaceOfBirth.getModel(), 
							String.valueOf(EmployeeConfigurationModel.PlaceOfBirth.getIndex()), 
							EmployeeConfigurationModel.PlaceOfBirth.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.NationalId.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.NationalId.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalId.getIndex()), 
							EmployeeConfigurationModel.NationalId.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.NationalIdIssueDate.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.NationalIdIssueDate.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdIssueDate.getIndex()), 
							EmployeeConfigurationModel.NationalIdIssueDate.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.NationalIdIssuePlace.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.NationalIdIssuePlace.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdIssuePlace.getIndex()), 
							EmployeeConfigurationModel.NationalIdIssuePlace.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.NationalIdExpiredDate.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.NationalIdExpiredDate.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdExpiredDate.getIndex()), 
							EmployeeConfigurationModel.NationalIdExpiredDate.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Address.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Address.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Address.getIndex()), 
							EmployeeConfigurationModel.Address.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Phone.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Phone.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Phone.getIndex()), 
							EmployeeConfigurationModel.Phone.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.PhoneMobile.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.PhoneMobile.getModel(), 
							String.valueOf(EmployeeConfigurationModel.PhoneMobile.getIndex()), 
							EmployeeConfigurationModel.PhoneMobile.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Email.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Email.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Email.getIndex()), 
							EmployeeConfigurationModel.Email.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Gender.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Gender.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Gender.getIndex()), 
							EmployeeConfigurationModel.Gender.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.MaritalStatus.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.MaritalStatus.getModel(), 
							String.valueOf(EmployeeConfigurationModel.MaritalStatus.getIndex()), 
							EmployeeConfigurationModel.MaritalStatus.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Education.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Education.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Education.getIndex()), 
							EmployeeConfigurationModel.Education.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(configuration, EmployeeConfigurationModel.Info.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							configuration, 
							EmployeeConfigurationModel.Info.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Info.getIndex()), 
							EmployeeConfigurationModel.Info.getDataPattern()));
		}*/
		return configuration;
	}
}
