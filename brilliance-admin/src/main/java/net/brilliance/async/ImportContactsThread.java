/**
 * 
 */
package net.brilliance.async;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.brilliance.common.ListUtility;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.model.ExecutionContext;
import net.brilliance.model.Bucket;
import net.brilliance.service.api.contact.ContactService;
import net.brilliance.service.helper.GlobalDataServiceHelper;

/**
 * @author ducbq
 *
 */
@Slf4j
@Component
@Scope("prototype")
public class ImportContactsThread implements Runnable {
	private ContactService contactService;
	private GlobalDataServiceHelper globalDataServiceHelper;
	private ExecutionContext executionContext;
	
	public ImportContactsThread(ExecutionContext executionContext){
		this.executionContext = ExecutionContext
				.builder()
				.context(executionContext.getContext())
				.build();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			loadContacts();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public ContactService getContactService() {
		return contactService;
	}
	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	protected void loadContacts() throws EcosysException{
		String sourceStream = (String)executionContext.getContextData("sourceStream");
		String[] sheetIds = (String[])executionContext.getContextData("sheetIds");
		String sheetId = (String)executionContext.getContextData("sheetId");
		int[] startedIndexes = (int[])executionContext.getContextData("startIndexes");
		this.contactService = (ContactService)executionContext.getContextData("contactService");
		this.globalDataServiceHelper = (GlobalDataServiceHelper)executionContext.getContextData("globalDataServiceHelper");
		Bucket bucket = this.doLoadExternalData(sourceStream, sheetIds, startedIndexes);
		contactService.deployContacts((List<List<String>>)bucket.get(sheetId));
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
}
