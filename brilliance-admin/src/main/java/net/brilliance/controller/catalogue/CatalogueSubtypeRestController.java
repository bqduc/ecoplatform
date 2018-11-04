package net.brilliance.controller.catalogue;

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
import net.brilliance.domain.entity.general.CatalogueSubtype;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.inventory.CatalogueSubtypeService;

@RestController
@RequestMapping("/" + ControllerConstants.REQUEST_MAPPING_CATALOG_SUBTYPE)
public class CatalogueSubtypeRestController extends BaseRestController{
	@Inject 
	private CatalogueSubtypeService businessManager;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<CatalogueSubtype> onListCatalogueSubtypes(HttpServletRequest request, HttpServletResponse response, Model model) {
		cLog.info("Rest::Come to catalogue subtype data listing ...>>>>>>");
		PageRequest pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
		SearchParameter searchParameter = SearchParameter.getInstance()
				.setPageable(pageRequest);
		Page<CatalogueSubtype> objects = businessManager.getObjects(searchParameter);
		cLog.info("Catalogue subtype data is loaded. >>>>>>");
		return objects.getContent();
	}
}
