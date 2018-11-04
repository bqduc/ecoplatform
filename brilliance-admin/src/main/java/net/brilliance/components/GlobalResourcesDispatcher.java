/**
 * 
 */
package net.brilliance.components;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import net.brilliance.common.ListUtility;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.logging.CommonLoggingService;
import net.brilliance.model.Bucket;
import net.brilliance.service.helper.GlobalDataServiceHelper;

/**
 * @author ducbq
 *
 */
@Component
public class GlobalResourcesDispatcher {
	public final static String DEFAULT_DATA_CATALOGUES = "/config/data/data-catalog.xlsx";
	public final static int[] DEFAULT_DATA_INDEXES = new int[]{1, 1, 1};
	@Inject 
	private CommonLoggingService cLog;

	@Inject
	private GlobalDataServiceHelper globalDataServiceHelper;

	public Bucket pickupConfigData(Map<String, Object> params) {
		return this.loadConfigurationData(
				(String)params.get(Bucket.PARAM_INPUT_RESOURCE_NAME), 
				(String[])params.get(Bucket.PARAM_DATA_SHEETS), 
				(int[])params.get(Bucket.PARAM_DATA_INDEXES));
	}

	public Bucket pickupDefaultConfigData() {
		return this.loadConfigurationData(
				DEFAULT_DATA_CATALOGUES, 
				null, 
				DEFAULT_DATA_INDEXES);
	}

	protected Bucket loadConfigurationData(String resourceFileName, String[]sheets, int[] indexes){
		Bucket bucket = null;
		Map<Object, Object>configParams = ListUtility.createMap();
		//Configure the started index for each sheet
		for (int idx = 0; idx < sheets.length; idx++){
			configParams.put(sheets[idx] + Bucket.PARAM_STARTED_ROW_INDEX, Integer.valueOf(indexes[idx]));
		}
		configParams.put(Bucket.PARAM_DATA_SHEETS, ListUtility.arraysAsList(sheets));
		configParams.put(Bucket.PARAM_WORK_DATA_SHEET, sheets);

		try {
			bucket = globalDataServiceHelper.readSpreadsheetData(new ClassPathResource(resourceFileName).getInputStream(), configParams);
		} catch (EcosysException | IOException e) {
			cLog.error(e);
		}
		return bucket;
	}
}
