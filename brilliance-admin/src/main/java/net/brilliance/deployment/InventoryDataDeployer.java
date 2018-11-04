/**
 * 
 */
package net.brilliance.deployment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import net.brilliance.common.ListUtility;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.config.Item;
import net.brilliance.domain.entity.config.Language;
import net.brilliance.domain.entity.config.LocalizedItem;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.model.Bucket;
import net.brilliance.service.api.invt.ItemService;
import net.brilliance.service.api.invt.LanguageService;
import net.brilliance.service.helper.GlobalDataServiceHelper;

/**
 * @author ducbq
 *
 */
@Component
public class InventoryDataDeployer {
	private final Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private ItemService itemService;

	@Inject
	private LanguageService languageService;

	@Inject
	private GlobalDataServiceHelper globalDataServiceHelper;

	private Map<String, Language> languagesMap = ListUtility.createMap();
	private Map<String, Item> itemsMap = ListUtility.createMap();

	private final static String[] sheetIds = new String[]{"languages", "items", "localized-items"}; 

	@Async
	public void deployData(Object arg){
		Bucket bucket = loadConfigurationData("/config/data/data-catalog.xlsx", sheetIds, new int[]{1, 1, 1});
		deployDataItems(bucket);
	}

	private void deployLanguages(List<List<String>> languageStrings){
		logger.info("Enter deploy languages.");
		Language deployedObject = null;
		Language currentObject = null;
		try {
			for (List<String> languageParts :languageStrings){
				currentObject = this.parseLanguage(languageParts);
				if (null==(deployedObject = this.languageService.getByCode(currentObject.getCode()))){
					deployedObject = languageService.saveOrUpdate(currentObject);
				}
				languagesMap.put(deployedObject.getCode(), deployedObject);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Leave deploy languages.");
	}

	private void deployItems(List<List<String>> itemStrings){
		logger.info("Enter deploy items.");
		Item deployedObject = null;
		Item currentObject = null;
		try {
			for (List<String> objectParts :itemStrings){
				currentObject = this.parseItem(objectParts);
				if (null==(deployedObject = this.itemService.getOne(currentObject.getCode()))){
					deployedObject = this.itemService.saveOrUpdate(currentObject);
				}
				itemsMap.put(deployedObject.getCode(), deployedObject);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Leave deploy items.");
	}

	private void deployLocalizedItems(List<List<String>> itemStrings){
		logger.info("Enter deploy localized items.");
		LocalizedItem deployedObject = null;
		try {
			for (List<String> objectParts :itemStrings){
				deployedObject = this.parseLocalizedItem(objectParts);
				if (null==deployedObject.getItem()||null==deployedObject.getLanguage())
					continue;

				if (null==this.itemService.getLocalizedItem(deployedObject.getItem(), deployedObject.getLanguage())){
					deployedObject = this.itemService.saveLocalizedItem(deployedObject);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Leave deploy localized items.");
	}
	
	@SuppressWarnings("unchecked")
	private void deployDataItems(Bucket dataBucket){
		logger.info("Enter deploy default inventory items.");
		try {
			//Deploy languages
			this.deployLanguages((List<List<String>>)dataBucket.get(sheetIds[0]));

			//Deploy items
			this.deployItems((List<List<String>>)dataBucket.get(sheetIds[1]));

			//Deploy localized items
			this.deployLocalizedItems((List<List<String>>)dataBucket.get(sheetIds[2]));
			System.out.println("DONE!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Leave deploy default inventory items.");
	}

	private Item parseItem(List<String> strings){
		return Item.instance(strings.get(0), strings.get(1));
	}

	private Language parseLanguage(List<String> strings){
		return Language.instance(strings.get(0), strings.get(1));
	}

	private LocalizedItem parseLocalizedItem(List<String> strings){
		Item item = itemsMap.get(strings.get(0));
		Language language = languagesMap.get(strings.get(1));
		return LocalizedItem.instance(item, language, strings.get(2), strings.get(3));
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
			//return (List<List<String>>)bucket.get(configParams.get(Bucket.PARAM_WORK_DATA_SHEET));
		} catch (EcosysException | IOException e) {
			e.printStackTrace();
		}
		return bucket;
	}
}
