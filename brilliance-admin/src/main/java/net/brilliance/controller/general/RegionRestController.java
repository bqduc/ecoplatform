package net.brilliance.controller.general;

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
import net.brilliance.domain.entity.general.Region;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.general.RegionService;

@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_REGION)
@RestController
public class RegionRestController extends BaseRestController<Region>{
	private final static String CACHE_OBJECTS_KEY = "cache.regions";
	@Inject 
	private RegionService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Region> onList(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("RestController::Come to enterprise data listing ...>>>>>>");
		List<Region> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<Region> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<Region>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		logger.info("Region data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(Region updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<Region> doFetchBusinessObjects(Integer page, Integer size) {
		Page<Region> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("Region Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected Region doFetchBusinessObject(Long id) {
		Region fetchedBusinessObject = businessService.getObject(id);
		logger.info("Region Rest::FetchBusinessObject: " + fetchedBusinessObject.getName());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("Region Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("Region Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(Region businessObject) {
		logger.info("Region Rest::CreateBusinessObject: " + businessObject.getName());
		businessService.saveOrUpdate((Region)businessObject);
		logger.info("Region Rest::CreateBusinessObject is done");
	}
}
