package net.brilliance.controller.controller.catalog;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brilliance.controller.base.BaseRestController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.manager.catalog.CatalogManager;

@RestController
@RequestMapping("/" + ControllerConstants.REQUEST_MAPPING_CATALOG)
public class CatalogueRestController extends BaseRestController <Catalogue>{
	@Inject 
	private CatalogManager businessManager;

	@RequestMapping(path = "/listCatalogues", method = RequestMethod.GET)
	public List<Catalogue> onListCatalogues() {
		return businessManager.getAll();
	}
}
