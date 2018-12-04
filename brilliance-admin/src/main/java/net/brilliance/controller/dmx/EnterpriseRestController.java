package net.brilliance.controller.dmx;

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

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.controller.base.BaseRestController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.dmx.EnterpriseService;

@RestController
@RequestMapping(ControllerConstants.REST_API + ControllerConstants.REQ_MAPPING_ENTERPRISE)
public class EnterpriseRestController extends BaseRestController<Enterprise>{
	private final static String CACHE_OBJECTS_KEY = "cache.enterprises";
	@Inject 
	private EnterpriseService businessService;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Enterprise> onListEnterprises(HttpServletRequest request, HttpServletResponse response, Model model) {
		cLog.info("RestController::Come to enterprise data listing ...>>>>>>");
		List<Enterprise> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<Enterprise> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<Enterprise>)cachedValue;
		} else {
			pageRequest = new PageRequest(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		cLog.info("Enterprise data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(Enterprise updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<Enterprise> doFetchBusinessObjects(Integer page, Integer size) {
		return businessService.getObjects(page, size);
	}

	@Override
	protected Enterprise doFetchBusinessObject(Long id) {
		return businessService.getObject(id);
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		businessService.remove(id);
	}

	@Override
	protected void doCreateBusinessObject(Enterprise businessObject) {
		businessService.saveOrUpdate((Enterprise)businessObject);
	}

	/*@Override
	protected BaseObject fetchBusinessObjectByCode(String code) {
		return this.businessManager.getOne(code);
	}

	@Override
	protected BaseObject addBusinessObject(BaseObject businessObject) {
		return this.businessManager.saveOrUpdate((Enterprise)businessObject);
	}*/
	
}
