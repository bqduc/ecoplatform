package net.brilliance.controller.agricultural;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.brilliance.common.CommonConstants;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.controller.base.AbstractRestHandler;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.aquacultural.Aquafeed;
import net.brilliance.exceptions.DataFormatException;
import net.brilliance.manager.agricultural.AquafeedManager;

@RestController
@RequestMapping(value = CommonConstants.REST_API + ControllerConstants.REQUEST_MAPPING_AQUAFEEDS)
@Api(tags = {"aquafeeds"})
public class AquafeedRestController extends AbstractRestHandler {

	final Logger logger = GlobalLoggerFactory.getLogger(AquafeedRestController.class);

	@Autowired
	private AquafeedManager serviceManager;

	/*@RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
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
	}*/


  @RequestMapping(value = "",
          method = RequestMethod.POST,
          consumes = {"application/json", "application/xml"},
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a aquafeed resource.", notes = "Returns the URL of the new resource in the Location header.")
  public void createAquafeed(@RequestBody Aquafeed aquafeed,
                               HttpServletRequest request, HttpServletResponse response) {
      Aquafeed createdAquafeed = this.serviceManager.createAquafeed(aquafeed);
      response.setHeader("Location", request.getRequestURL().append("/").append(createdAquafeed.getId()).toString());
  }

  @RequestMapping(value = "",
          method = RequestMethod.GET,
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all aquafeeds.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
  public
  @ResponseBody
  Page<Aquafeed> getAllAquafeed(@ApiParam(value = "The page number (zero-based)", required = true)
                                    @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                    @ApiParam(value = "Tha page size", required = true)
                                    @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                    HttpServletRequest request, HttpServletResponse response) {
      return this.serviceManager.getList(page, size);
  }

  @RequestMapping(value = "/{id}",
          method = RequestMethod.GET,
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a single aquafeed.", notes = "You have to provide a valid aquafeed ID.")
  public
  @ResponseBody
  Aquafeed getAquafeed(@ApiParam(value = "The ID of the aquafeed.", required = true)
                           @PathVariable("id") Long id,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {
      Aquafeed aquafeed = this.serviceManager.getAquafeed(id);
      checkResourceFound(aquafeed);
      return aquafeed;
  }

  @RequestMapping(value = "/{id}",
          method = RequestMethod.PUT,
          consumes = {"application/json", "application/xml"},
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update a aquafeed resource.", notes = "You have to provide a valid aquafeed ID in the URL and in the payload. The ID attribute can not be updated.")
  public void updateAquafeed(@ApiParam(value = "The ID of the existing aquafeed resource.", required = true)
                               @PathVariable("id") Long id, @RequestBody Aquafeed aquafeed,
                               HttpServletRequest request, HttpServletResponse response) {
      checkResourceFound(this.serviceManager.getAquafeed(id));
      if (id != aquafeed.getId()) throw new DataFormatException("ID doesn't match!");
      this.serviceManager.updateAquafeed(aquafeed);
  }

  //TODO: @ApiImplicitParams, @ApiResponses
  @RequestMapping(value = "/{id}",
          method = RequestMethod.DELETE,
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete a aquafeed resource.", notes = "You have to provide a valid aquafeed ID in the URL. Once deleted the resource can not be recovered.")
  public void deleteAquafeed(@ApiParam(value = "The ID of the existing aquafeed resource.", required = true)
                               @PathVariable("id") Long id, HttpServletRequest request,
                               HttpServletResponse response) {
      checkResourceFound(this.serviceManager.getAquafeed(id));
      this.serviceManager.deleteAquafeed(id);
  }
}
