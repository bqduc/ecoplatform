package net.brilliance.controller.dmx;

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
import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.dmx.EnterpriseService;

@RequestMapping(ControllerConstants.REST_API + ControllerConstants.REQ_MAPPING_ENTERPRISE)
@RestController
public class EnterpriseRestController extends BaseRestController<Enterprise>{
	private final static String CACHE_OBJECTS_KEY = "cache.enterprises";
	@Inject 
	private EnterpriseService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Enterprise> onListEnterprises(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("RestController::Come to enterprise data listing ...>>>>>>");
		List<Enterprise> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<Enterprise> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<Enterprise>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		logger.info("Enterprise data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(Enterprise updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<Enterprise> doFetchBusinessObjects(Integer page, Integer size) {
		Page<Enterprise> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("Enterprise Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected Enterprise doFetchBusinessObject(Long id) {
		Enterprise fetchedBusinessObject = businessService.getObject(id);
		logger.info("Enterprise Rest::FetchBusinessObject: " + fetchedBusinessObject.getCode());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("Enterprise Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("Enterprise Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(Enterprise businessObject) {
		logger.info("Enterprise Rest::CreateBusinessObject: " + businessObject.getCode());
		businessService.saveOrUpdate((Enterprise)businessObject);
		logger.info("Enterprise Rest::CreateBusinessObject is done");
	}
}
