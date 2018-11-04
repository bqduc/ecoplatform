package net.brilliance.controller.controller.stock;

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

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.stock.Store;
import net.brilliance.manager.stock.StoreManager;

@RequestMapping(ControllerConstants.REST_API_STORE)
@RestController
public class StoreRestController {

	final Logger logger = GlobalLoggerFactory.getLogger(StoreRestController.class);

	@Autowired
	private StoreManager serviceManager;

	@RequestMapping(value = "/get/{code}", method = RequestMethod.GET)
	public @ResponseBody Store queryByCode(HttpServletRequest request, @PathVariable("code") String code) {
		Store fetchedObject = null;
		try {
			fetchedObject = this.serviceManager.getByCode(code);
			System.out.println("Fetched object: [" + fetchedObject + "]");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return fetchedObject;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(HttpServletRequest request, @RequestBody Store clientDataObject) {
		ResponseEntity<?> responseEntity = null;
		try {
			this.serviceManager.save(clientDataObject);
			URI projectUri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/" + ControllerConstants.REQUEST_MAPPING_STORE + "/{id}").buildAndExpand(clientDataObject.getId())
					.toUri();

			responseEntity = ResponseEntity.created(projectUri).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseEntity = ResponseEntity.noContent().build();
		}
		return responseEntity;
	}
}
