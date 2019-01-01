package net.brilliance.controller.crx.orders;

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
import net.brilliance.domain.entity.crx.BizOrder;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.crx.orders.OrderService;

@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_ORDER)
@RestController
public class OrderRestController extends BaseRestController<BizOrder>{
	private final static String CACHE_OBJECTS_KEY = "cache.orders";
	@Inject 
	private OrderService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<BizOrder> onListing(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("RestController::Come to order data listing ...>>>>>>");
		List<BizOrder> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<BizOrder> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<BizOrder>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		logger.info("Order data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(BizOrder updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<BizOrder> doFetchBusinessObjects(Integer page, Integer size) {
		Page<BizOrder> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("Order Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected BizOrder doFetchBusinessObject(Long id) {
		BizOrder fetchedBusinessObject = businessService.getObject(id);
		logger.info("Order Rest::FetchBusinessObject: " + fetchedBusinessObject.getCode());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("Order Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("Order Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(BizOrder businessObject) {
		logger.info("Order Rest::CreateBusinessObject: " + businessObject.getCode());
		businessService.saveOrUpdate((BizOrder)businessObject);
		logger.info("Order Rest::CreateBusinessObject is done");
	}
}
