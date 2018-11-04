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
import net.brilliance.domain.entity.aquacultural.Pond;
import net.brilliance.exceptions.DataFormatException;
import net.brilliance.manager.agricultural.PondManager;

@RestController
@RequestMapping(value = CommonConstants.REST_API + ControllerConstants.REQUEST_MAPPING_POND)
@Api(tags = {"ponds"})
public class PondRestController extends AbstractRestHandler {

	final Logger logger = GlobalLoggerFactory.getLogger(PondRestController.class);

	@Autowired
	private PondManager serviceManager;


  @RequestMapping(value = "",
          method = RequestMethod.POST,
          consumes = {"application/json", "application/xml"},
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a pond resource.", notes = "Returns the URL of the new resource in the Location header.")
  public void createPond(@RequestBody Pond pond, HttpServletRequest request, HttpServletResponse response) {
      Pond createdPond = this.serviceManager.createPond(pond);
      response.setHeader("Location", request.getRequestURL().append("/").append(createdPond.getId()).toString());
  }

  @RequestMapping(value = "",
          method = RequestMethod.GET,
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all ponds.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
  public
  @ResponseBody
  Page<Pond> getAllPond(@ApiParam(value = "The page number (zero-based)", required = true)
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
  @ApiOperation(value = "Get a single pond.", notes = "You have to provide a valid pond ID.")
  public
  @ResponseBody
  Pond getPond(@ApiParam(value = "The ID of the pond.", required = true)
                           @PathVariable("id") Long id,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {
      Pond pond = this.serviceManager.getPond(id);
      checkResourceFound(pond);
      return pond;
  }

  @RequestMapping(value = "/{id}",
          method = RequestMethod.PUT,
          consumes = {"application/json", "application/xml"},
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update a pond resource.", notes = "You have to provide a valid pond ID in the URL and in the payload. The ID attribute can not be updated.")
  public void updatePond(@ApiParam(value = "The ID of the existing pond resource.", required = true)
                               @PathVariable("id") Long id, @RequestBody Pond pond,
                               HttpServletRequest request, HttpServletResponse response) {
      checkResourceFound(this.serviceManager.getPond(id));
      if (id != pond.getId()) throw new DataFormatException("ID doesn't match!");
      this.serviceManager.updatePond(pond);
  }

  //TODO: @ApiImplicitParams, @ApiResponses
  @RequestMapping(value = "/{id}",
          method = RequestMethod.DELETE,
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete a pond resource.", notes = "You have to provide a valid pond ID in the URL. Once deleted the resource can not be recovered.")
  public void deletePond(@ApiParam(value = "The ID of the existing pond resource.", required = true)
                               @PathVariable("id") Long id, HttpServletRequest request,
                               HttpServletResponse response) {
      checkResourceFound(this.serviceManager.getPond(id));
      this.serviceManager.deletePond(id);
  }
}
