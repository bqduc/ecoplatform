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
import net.brilliance.domain.entity.general.City;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.general.CityService;

@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_CITY)
@RestController
public class CityRestController extends BaseRestController<City>{
	private final static String CACHE_OBJECTS_KEY = "cache.cities";
	@Inject 
	private CityService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<City> onList(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("RestController::Come to enterprise data listing ...>>>>>>");
		List<City> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<City> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<City>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		logger.info("City data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(City updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<City> doFetchBusinessObjects(Integer page, Integer size) {
		Page<City> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("City Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected City doFetchBusinessObject(Long id) {
		City fetchedBusinessObject = businessService.getObject(id);
		logger.info("City Rest::FetchBusinessObject: " + fetchedBusinessObject.getName());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("City Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("City Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(City businessObject) {
		logger.info("City Rest::CreateBusinessObject: " + businessObject.getName());
		businessService.saveOrUpdate((City)businessObject);
		logger.info("City Rest::CreateBusinessObject is done");
	}
}
