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
import net.brilliance.domain.entity.general.Attachment;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.general.AttachmentService;

@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_ATTACHMENT)
@RestController
public class AttachmentRestController extends BaseRestController<Attachment>{
	private final static String CACHE_OBJECTS_KEY = "cache.attachmens";
	@Inject 
	private AttachmentService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Attachment> listBusinessObjects(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("RestController::Come to list business objects ...>>>>>>");
		List<Attachment> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<Attachment> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<Attachment>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder().pageable(pageRequest).build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		logger.info("Attachment data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(Attachment updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<Attachment> doFetchBusinessObjects(Integer page, Integer size) {
		Page<Attachment> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("Attachment Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected Attachment doFetchBusinessObject(Long id) {
		Attachment fetchedBusinessObject = businessService.getObject(id);
		logger.info("Attachment Rest::FetchBusinessObject: " + fetchedBusinessObject.getName());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("Attachment Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("Attachment Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(Attachment businessObject) {
		logger.info("Attachment Rest::CreateBusinessObject: " + businessObject.getName());
		businessService.saveOrUpdate((Attachment)businessObject);
		logger.info("Attachment Rest::CreateBusinessObject is done");
	}
}
