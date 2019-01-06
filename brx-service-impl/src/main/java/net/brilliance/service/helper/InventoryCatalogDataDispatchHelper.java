/**
 * 
 */
package net.brilliance.service.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.brilliance.common.ListUtility;
import net.brilliance.dispatch.CatalogMapping;
import net.brilliance.dispatch.ProductMapping;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.domain.entity.general.CatalogueSubtype;
import net.brilliance.domain.entity.stock.Product;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.component.BaseComponent;
import net.brilliance.framework.logging.CommonLoggingService;
import net.brilliance.model.Bucket;

/**
 * @author ducbq
 *
 */
@Slf4j
@Component
@SuppressWarnings("unchecked")
public class InventoryCatalogDataDispatchHelper extends BaseComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1229704571701142995L;

	@Inject
	private GlobalDataServiceHelper globalDataServiceHelper;
	
	@Inject
	private CommonLoggingService cLog;
	
	protected InputStream getExternalDataInputStream() throws IOException{
		ClassPathResource resource = new ClassPathResource("/config/data/data-catalog.xlsx");
		return resource.getInputStream();
		//return new FileInputStream("D:/workspace/spring-cloud-pos/vpx/src/main/resources/db/data/data-catalog.xlsx");
	}

	protected Bucket readDatasheetBucket(String datasheetId) throws EcosysException {
		InputStream inputStream = null;
		try {
			inputStream = getExternalDataInputStream();
			return this.globalDataServiceHelper.readSpreadsheetData(inputStream, new String[]{datasheetId});
		} catch (Exception e) {
			throw new EcosysException(e);
		} finally {
			if (null != inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
					cLog.error(log, e);
				}
			}
		}
	}

	public List<CatalogueSubtype> dispatchCatalogueSubtypes() throws EcosysException {
		List<CatalogueSubtype> bizObjects = ListUtility.createArrayList();
		
		return bizObjects;
	}

	public List<Catalogue> dispatchCatalogues() throws EcosysException {
		int startedIndex = 1;
		String sheet = "Catalogues";

		List<Catalogue> results = new ArrayList<>();
		Bucket dataBucket = null;
		List<List<String>> dataStrings = null;
		List<String> dataString;
		Catalogue catalog = null;
		try {
			dataBucket = this.readDatasheetBucket(sheet);
			dataStrings = (List<List<String>>)dataBucket.get(sheet);

			for (int idx = startedIndex; idx < dataStrings.size(); ++idx){
				dataString = dataStrings.get(idx);
				catalog = Catalogue.builder()
				.code(dataString.get(CatalogMapping.CODE.getIndex()))
				.name(dataString.get(CatalogMapping.NAME.getIndex()))
				.description(dataString.get(CatalogMapping.DESCRIPTION.getIndex()))
				.build();

				results.add(catalog);
			}
		} catch (Exception e) {
			throw new EcosysException(e);
		} 
		return results;
	}

	public void dispatchInventoryItems(InputStream inputStream, int startedIndex){
		try {
			Bucket dataBucket = this.globalDataServiceHelper.readSpreadsheetData(inputStream);
			List<List<String>> dataStrings = (List<List<String>>)dataBucket.get("Sheet1");
			List<String> dataString;
			Product product = null;
			for (int idx = startedIndex; idx < dataStrings.size(); ++idx){
				dataString = dataStrings.get(idx);

				product = new Product()
				.setCode(dataString.get(ProductMapping.REGISTRATION_NO.getIndex()))
				.setName(dataString.get(ProductMapping.NAME.getIndex()))
				.setConcentration(dataString.get(ProductMapping.CONCENTRATION.getIndex()))
				.setActivePrinciple(dataString.get(ProductMapping.ACTIVE_PRINCIPLE.getIndex()))
				.setProcessingType(dataString.get(ProductMapping.PROCESSING.getIndex()))
				.setPackaging(dataString.get(ProductMapping.PACKAGING.getIndex()))
				.setStandard(dataString.get(ProductMapping.STANDARD.getIndex()))
				.setExpectationOfLife(dataString.get(ProductMapping.EXPECTATION_OF_LIFE.getIndex()))
				.setProductionCompany(dataString.get(ProductMapping.PRODUCTION_COMPANY.getIndex()))
				.setProductionCountry(dataString.get(ProductMapping.PRODUCTION_COUNTRY.getIndex()))
				.setProductionAddress(dataString.get(ProductMapping.PRODUCTION_ADDRESS.getIndex()))
				.setRegistrationCompany(dataString.get(ProductMapping.REGISTRATION_COMPANY.getIndex()))
				.setRegistrationCountry(dataString.get(ProductMapping.REGISTRATION_COUNTRY.getIndex()))
				.setRegistrationAddress(dataString.get(ProductMapping.REGISTRATION_ADDRESS.getIndex()))
				.setRoot(dataString.get(ProductMapping.ROOT.getIndex()))
				.setNationalCircularNo(dataString.get(ProductMapping.NATIONAL_CIRCULAR_NO.getIndex()))
				;
				//dataString.get(ProductMapping.CATEGORY.getIndex()))
				System.out.println(idx+1 + "\t|" + dataString + "\t"+product);
				/*if (isCategory(dataString.get(0))){
					category = dataString.get(0);
					categoryName = dataString.get(1);

					product = "";
					productName = "";
					//System.out.println((++idx) + "\tGroup\t|" + dataString.get(0)+"|\t" + dataString.get(1));
				}else if (isProduct(dataString.get(0))){
					product = dataString.get(0);
					productName = dataString.get(1);
					//System.out.println(++idx + "\tProduct\t|" + dataString.get(0)+"|\t" + dataString.get(1));
				}else if (isDepartment(dataString.get(0)) || dataString.get(0).startsWith(regExpDepartment)){
					department = dataString.get(0);
					category = "";
					categoryName = "";
					product = "";
					productName = "";
					//Loop to get all information about the department
					do{
						dataString = dataStrings.get(++idx);
					}while(!(isDepartment(dataString.get(0)) || isCategory(dataString.get(0)) || isProduct(dataString.get(0))));
				}
				System.out.println(++idx + "\t|"+ department + "\t|" +category+ "\t|" +categoryName + "\t|" + product + "\t|" +productName);*/
			}
		} catch (Exception e) {
			cLog.error(log, e);
		}
	}
}
