package net.brilliance.controller.dxp;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brilliance.common.CommonUtility;
import net.brilliance.controller.base.BaseRestController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.config.Item;
import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.invt.ItemService;

@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_DXP_ITEM)
@RestController
public class ItemRestController extends BaseRestController<Enterprise>{
	private final static String CACHE_OBJECTS_KEY = "cache.enterprises";

	@Inject 
	private ItemService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Item> onListItems(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("RestController::Come to Item data listing ...>>>>>>");
		List<Item> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<Item> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<Item>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		logger.info("Item data is loaded. >>>>>>");

		return results;
	}

	/*@Override
	protected void doUpdateBusinessObject(Item updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<Item> doFetchBusinessObjects(Integer page, Integer size) {
		Page<Item> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("Item Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected Item doFetchBusinessObject(Long id) {
		Item fetchedBusinessObject = businessService.getObject(id);
		logger.info("Item Rest::FetchBusinessObject: " + fetchedBusinessObject.getCode());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("Item Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("Item Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(Item businessObject) {
		logger.info("Item Rest::CreateBusinessObject: " + businessObject.getCode());
		businessService.saveOrUpdate((Item)businessObject);
		logger.info("Item Rest::CreateBusinessObject is done");
	}*/
}
