package net.brilliance.controller.inventory;

import java.io.InputStream;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.brilliance.common.CommonUtility;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.contact.Contact;
import net.brilliance.domain.entity.stock.Product;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.service.api.inventory.ProductService;

@Controller
@RequestMapping(ControllerConstants.REQUEST_URI_PRODUCT)
public class ProductController extends BaseController {
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "inventory/";

	private static final String PAGE_CONTEXT_BROWSE = PAGE_CONTEXT + "productBrowse";
	private static final String PAGE_CONTEXT_SHOW = PAGE_CONTEXT + "productShow";
	private static final String PAGE_CONTEXT_EDIT = PAGE_CONTEXT + "productEdit";

	@Inject
	private ProductService businessServiceManager;

	@RequestMapping(path="/", method=RequestMethod.GET)
	public String goHome(){
		return PAGE_CONTEXT_BROWSE;
	}

	@RequestMapping(path="/search", method=RequestMethod.POST)
	@ResponseBody
	public String onSearch(@RequestBody(required = false) SearchParameter searchParams, Model model, Pageable pageable){
		if (CommonUtility.isEmpty(searchParams.getPageable())) {
			searchParams.setPageable(pageable);
		}
		Page<Product> results = businessServiceManager.getObjects(searchParams);
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonBizObjects = gson.toJson(results.getContent());
		return jsonBizObjects;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String onShow(@PathVariable("id") Long id, Model model) {
		cLog.info("Fetch employee object with id: " + id);

		Product fetchedObject = businessServiceManager.getObject(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);
		
		return PAGE_CONTEXT_SHOW;
	}

	/**
	 * Create a new employee object and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, new Contact());
		return PAGE_CONTEXT_EDIT;
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessServiceManager.getObject(id));
		return PAGE_CONTEXT_EDIT;
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}
