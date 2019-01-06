package net.brilliance.controller.corporate.brx;

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
import net.brilliance.domain.Route;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.RouteService;

@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_BRX_ROUTE)
@RestController
public class RouteRestController extends BaseRestController<Route>{
	private final static String CACHE_OBJECTS_KEY = "cache.bizPackages";
	@Inject 
	private RouteService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Route> onListRoutes(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("RestController::Come to enterprise data listing ...>>>>>>");
		List<Route> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<Route> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<Route>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		logger.info("Route data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(Route updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<Route> doFetchBusinessObjects(Integer page, Integer size) {
		Page<Route> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("Route Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected Route doFetchBusinessObject(Long id) {
		Route fetchedBusinessObject = businessService.getObject(id);
		logger.info("Route Rest::FetchBusinessObject: " + fetchedBusinessObject.getName());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("Route Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("Route Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(Route businessObject) {
		logger.info("Route Rest::CreateBusinessObject: " + businessObject.getName());
		businessService.saveOrUpdate((Route)businessObject);
		logger.info("Route Rest::CreateBusinessObject is done");
	}
}
