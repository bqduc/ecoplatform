package net.brilliance.controller.contact;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.contact.Contact;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.contact.ContactService;

@RestController
@RequestMapping(ControllerConstants.REQUEST_URI_CONTACT)
public class ContactRestController {
	protected Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private ContactService contactService;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Contact> list(HttpServletRequest request, HttpServletResponse response, Model model) {
		PageRequest pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
		SearchParameter searchParameter = SearchParameter.getInstance()
				.setPageable(pageRequest);
		Page<Contact> objects = contactService.getObjects(searchParameter);
		System.out.println("COME !");
		return objects.getContent();
	}
}
