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
import net.brilliance.domain.BusinessPackage;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.BusinessPackageService;

@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_BRX_CLIENT_BOOKING)
@RestController
public class ClientBookingRestController extends BaseRestController<BusinessPackage>{
	private final static String CACHE_OBJECTS_KEY = "cache.bizPackages";
	@Inject 
	private BusinessPackageService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<BusinessPackage> onListBusinessPackages(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("RestController::Come to enterprise data listing ...>>>>>>");
		List<BusinessPackage> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<BusinessPackage> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<BusinessPackage>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		logger.info("BusinessPackage data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(BusinessPackage updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<BusinessPackage> doFetchBusinessObjects(Integer page, Integer size) {
		Page<BusinessPackage> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("BusinessPackage Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected BusinessPackage doFetchBusinessObject(Long id) {
		BusinessPackage fetchedBusinessObject = businessService.getObject(id);
		logger.info("BusinessPackage Rest::FetchBusinessObject: " + fetchedBusinessObject.getName());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("BusinessPackage Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("BusinessPackage Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(BusinessPackage businessObject) {
		logger.info("BusinessPackage Rest::CreateBusinessObject: " + businessObject.getName());
		businessService.saveOrUpdate((BusinessPackage)businessObject);
		logger.info("BusinessPackage Rest::CreateBusinessObject is done");
	}
}
