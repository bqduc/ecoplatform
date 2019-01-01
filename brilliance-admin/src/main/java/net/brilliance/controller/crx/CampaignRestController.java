package net.brilliance.controller.crx;

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
import net.brilliance.domain.entity.crx.Campaign;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.crx.CampaignService;

@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_CAMPAIGN)
@RestController
public class CampaignRestController extends BaseRestController<Campaign>{
	private final static String CACHE_OBJECTS_KEY = "cache.campaigns";
	@Inject 
	private CampaignService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Campaign> onListCampaigns(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("RestController::Come to enterprise data listing ...>>>>>>");
		List<Campaign> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<Campaign> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<Campaign>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		logger.info("Campaign data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(Campaign updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<Campaign> doFetchBusinessObjects(Integer page, Integer size) {
		Page<Campaign> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("Campaign Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected Campaign doFetchBusinessObject(Long id) {
		Campaign fetchedBusinessObject = businessService.getObject(id);
		logger.info("Campaign Rest::FetchBusinessObject: " + fetchedBusinessObject.getName());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("Campaign Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("Campaign Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(Campaign businessObject) {
		logger.info("Campaign Rest::CreateBusinessObject: " + businessObject.getName());
		businessService.saveOrUpdate((Campaign)businessObject);
		logger.info("Campaign Rest::CreateBusinessObject is done");
	}
}
