package net.brilliance.controller.controller.catalog;

import java.net.URI;
import java.util.Optional;

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
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.general.Category;
import net.brilliance.manager.catalog.CategoryManager;

@RequestMapping(CommonConstants.REST_API + ControllerConstants.REQUEST_MAPPING_CATEGORY)
@RestController
public class CategoryAPIController {

	final Logger logger = GlobalLoggerFactory.getLogger(CategoryAPIController.class);

	@Autowired
	private CategoryManager serviceManager;

	@RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
	public @ResponseBody Category get(HttpServletRequest request, @PathVariable("name") String name) {
		Category fetchedObject = null;
		try {
			Optional<Category> queryObject = this.serviceManager.getByName(name);
			if (!queryObject.isPresent()) {
				fetchedObject = new Category();
				fetchedObject.setName("Loại khởi động");
				fetchedObject.setCode("CATE-20170501");
			}
			System.out.println("Found category: [" + fetchedObject + "]");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return fetchedObject;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(HttpServletRequest request, @RequestBody Category category) {
		ResponseEntity<?> responseEntity = null;
		try {
			this.serviceManager.save(category);
			URI projectUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + ControllerConstants.REQUEST_MAPPING_CATEGORY + "/{id}")
					.buildAndExpand(category.getId()).toUri();

			responseEntity = ResponseEntity.created(projectUri).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseEntity = ResponseEntity.noContent().build();
		}
		return responseEntity;
	}
}
