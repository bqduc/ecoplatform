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
import net.brilliance.domain.BusinessSubPackage;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.BusinessSubPackageService;

@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_BUSINESS_SUB_PACKAGE)
@RestController
public class BusinessSubPackageRestController extends BaseRestController<BusinessSubPackage>{
	private final static String CACHE_OBJECTS_KEY = "cache.campaigns";
	@Inject 
	private BusinessSubPackageService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<BusinessSubPackage> onListBusinessSubPackages(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("RestController::Come to enterprise data listing ...>>>>>>");
		List<BusinessSubPackage> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<BusinessSubPackage> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<BusinessSubPackage>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		logger.info("BusinessSubPackage data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(BusinessSubPackage updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<BusinessSubPackage> doFetchBusinessObjects(Integer page, Integer size) {
		Page<BusinessSubPackage> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("BusinessSubPackage Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected BusinessSubPackage doFetchBusinessObject(Long id) {
		BusinessSubPackage fetchedBusinessObject = businessService.getObject(id);
		logger.info("BusinessSubPackage Rest::FetchBusinessObject: " + fetchedBusinessObject.getName());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("BusinessSubPackage Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("BusinessSubPackage Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(BusinessSubPackage businessObject) {
		logger.info("BusinessSubPackage Rest::CreateBusinessObject: " + businessObject.getName());
		businessService.saveOrUpdate((BusinessSubPackage)businessObject);
		logger.info("BusinessSubPackage Rest::CreateBusinessObject is done");
	}
}
