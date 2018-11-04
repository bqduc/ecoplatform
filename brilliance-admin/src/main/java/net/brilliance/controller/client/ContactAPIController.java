package net.brilliance.controller.client;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.contact.ContactProfile;
import net.brilliance.manager.contact.ContactProfileManager;

@RequestMapping(CommonConstants.REST_API + "contact")
@RestController
public class ContactAPIController { 

	final Logger logger = GlobalLoggerFactory.getLogger(ContactAPIController.class);

	@Autowired
	private ContactProfileManager serviceManager;

	@RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
	public @ResponseBody ContactProfile get(HttpServletRequest request, @PathVariable("name") String name) {
		ContactProfile fetchedObject = null;
    	try {
			fetchedObject = this.serviceManager.getByName(name);
			if (null==fetchedObject){
				fetchedObject = new ContactProfile();
				fetchedObject.setFullName("Trần Trừng Trị");
				//fetchedObject.setLogin("CTA-01");
			}
			System.out.println("Found contact: [" + fetchedObject + "]");
    	} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return fetchedObject;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(HttpServletRequest request, @RequestBody ContactProfile contact){
		ResponseEntity<?> responseEntity = null;
		try {
    		this.serviceManager.save(contact);
    		URI projectUri = ServletUriComponentsBuilder
					.fromCurrentRequest().path("/contact/{id}")
					.buildAndExpand(contact.getId()).toUri();

    		responseEntity = ResponseEntity.created(projectUri).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseEntity = ResponseEntity.noContent().build();
		}
		return responseEntity;
	}
}
