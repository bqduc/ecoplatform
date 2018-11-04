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
import net.brilliance.domain.entity.aquacultural.LivestockFeed;
import net.brilliance.exceptions.DataFormatException;
import net.brilliance.manager.agricultural.LivestockFeedManager;

@RestController
@RequestMapping(value = CommonConstants.REST_API + ControllerConstants.REQUEST_MAPPING_LIVESTOCK_FEED)
@Api(tags = {"livestockFeeds"})
public class LivestockFeedRestController extends AbstractRestHandler {

	final Logger logger = GlobalLoggerFactory.getLogger(LivestockFeedRestController.class);

	@Autowired
	private LivestockFeedManager serviceManager;

  @RequestMapping(value = "",
          method = RequestMethod.POST,
          consumes = {"application/json", "application/xml"},
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a livestockFeed resource.", notes = "Returns the URL of the new resource in the Location header.")
  public void createLivestockFeed(@RequestBody LivestockFeed livestockFeed,
                               HttpServletRequest request, HttpServletResponse response) {
      LivestockFeed createdLivestockFeed = this.serviceManager.createLivestockFeed(livestockFeed);
      response.setHeader("Location", request.getRequestURL().append("/").append(createdLivestockFeed.getId()).toString());
  }

  @RequestMapping(value = "",
          method = RequestMethod.GET,
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all livestockFeeds.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
  public
  @ResponseBody
  Page<LivestockFeed> getAllLivestockFeed(@ApiParam(value = "The page number (zero-based)", required = true)
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
  @ApiOperation(value = "Get a single livestockFeed.", notes = "You have to provide a valid livestockFeed ID.")
  public
  @ResponseBody
  LivestockFeed getLivestockFeed(@ApiParam(value = "The ID of the livestockFeed.", required = true)
                           @PathVariable("id") Long id,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {
      LivestockFeed livestockFeed = this.serviceManager.getLivestockFeed(id);
      checkResourceFound(livestockFeed);
      return livestockFeed;
  }

  @RequestMapping(value = "/{id}",
          method = RequestMethod.PUT,
          consumes = {"application/json", "application/xml"},
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update a livestockFeed resource.", notes = "You have to provide a valid livestockFeed ID in the URL and in the payload. The ID attribute can not be updated.")
  public void updateLivestockFeed(@ApiParam(value = "The ID of the existing livestockFeed resource.", required = true)
                               @PathVariable("id") Long id, @RequestBody LivestockFeed livestockFeed,
                               HttpServletRequest request, HttpServletResponse response) {
      checkResourceFound(this.serviceManager.getLivestockFeed(id));
      if (id != livestockFeed.getId()) throw new DataFormatException("ID doesn't match!");
      this.serviceManager.updateLivestockFeed(livestockFeed);
  }

  //TODO: @ApiImplicitParams, @ApiResponses
  @RequestMapping(value = "/{id}",
          method = RequestMethod.DELETE,
          produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete a livestockFeed resource.", notes = "You have to provide a valid livestock feed ID in the URL. Once deleted the resource can not be recovered.")
  public void deleteLivestockFeed(@ApiParam(value = "The ID of the existing livestockFeed resource.", required = true)
                               @PathVariable("id") Long id, HttpServletRequest request,
                               HttpServletResponse response) {
      checkResourceFound(this.serviceManager.getLivestockFeed(id));
      this.serviceManager.deleteLivestockFeed(id);
  }
}
