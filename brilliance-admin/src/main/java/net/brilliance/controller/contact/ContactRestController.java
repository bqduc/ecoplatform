package net.brilliance.controller.contact;

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

import net.brilliance.controller.base.BaseRestController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.crm.contact.Contact;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.contact.ContactService;

@RestController
@RequestMapping(ControllerConstants.REQUEST_URI_CONTACT)
public class ContactRestController extends BaseRestController<Contact>{
	@Inject
	private ContactService businessService;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Contact> list(HttpServletRequest request, HttpServletResponse response, Model model) {
		PageRequest pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
		SearchParameter searchParameter = SearchParameter.getInstance()
				.setPageable(pageRequest);
		Page<Contact> objects = businessService.getObjects(searchParameter);
		return objects.getContent();
	}

	@Override
	protected void doUpdateBusinessObject(Contact updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<Contact> doFetchBusinessObjects(Integer page, Integer size) {
		Page<Contact> fetchedBusinessObjects = businessService.getObjects(page, size);
		logger.info("Contact Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected Contact doFetchBusinessObject(Long id) {
		Contact fetchedBusinessObject = businessService.getObject(id);
		logger.info("Contact Rest::FetchBusinessObject: " + fetchedBusinessObject.getCode());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		logger.info("Contact Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		logger.info("Contact Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(Contact businessObject) {
		logger.info("Contact Rest::CreateBusinessObject: " + businessObject.getCode());
		businessService.saveOrUpdate((Contact)businessObject);
		logger.info("Contact Rest::CreateBusinessObject is done");
	}
}
