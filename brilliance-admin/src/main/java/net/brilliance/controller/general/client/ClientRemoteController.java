package net.brilliance.controller.general.client;

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
import net.brilliance.domain.entity.contact.ClientProfile;
import net.brilliance.manager.contact.ClientProfileManager;

@RequestMapping(CommonConstants.REST_API + "client")
@RestController
//@PostAuthorize("isAuthenticated()")
public class ClientRemoteController { 

	final Logger logger = GlobalLoggerFactory.getLogger(ClientRemoteController.class);

	@Autowired
	private ClientProfileManager serviceManager;

	@RequestMapping(value = "/get/{code}", method = RequestMethod.GET)
	public @ResponseBody ClientProfile queryByCode(HttpServletRequest request, @PathVariable("code") String code) {
		ClientProfile fetchedObject = null;
    	try {
			fetchedObject = this.serviceManager.getByCode(code);
			if (null==fetchedObject){
				fetchedObject = ClientProfile.getInstance("CLNT-01", "Client: Trần Trừng Trị", "Nhà không số, đường không tên, thành phố vô phương", "1010101010101", "demo@gmail.com.vn");
			}
			System.out.println("Found contact: [" + fetchedObject + "]");
    	} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return fetchedObject;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(HttpServletRequest request, @RequestBody ClientProfile contact){
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
