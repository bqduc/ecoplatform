package net.brilliance.controller.controller.catalog;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.stock.Product;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.manager.catalog.ProductServiceManager;
import net.brilliance.model.SelectItem;
import net.brilliance.util.ImageUtil;
import net.brilliance.util.Message;

@RequestMapping("/productOrigin")
@Controller
public class ProductControllerOrigin extends BaseController {
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "general/catalog/";
	private static final String DEFAULT_PAGED_REDIRECT = REDIRECT + "product/list/1";

	final Logger logger = GlobalLoggerFactory.getLogger(ProductControllerOrigin.class);

	@Inject
	private ProductServiceManager serviceManager;

	@Autowired
	private MessageSource messageSource;

	/**
	 * List all products.
	 */
	@RequestMapping(value = { "/list", "" }, method = RequestMethod.GET)
	public String list(Model model) {
		logger.info("Listing products");

		if (serviceManager.count() < 1) {
			// serviceManager.createDummyObjects();
		}
		return DEFAULT_PAGED_REDIRECT;
	}

	@RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
	public String listByPage(@PathVariable Integer pageNumber, Model model) {
		logger.info("Listing products at: " + Calendar.getInstance().getTime());

		Page<Product> page = serviceManager.getList(pageNumber);
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE);
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, page.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, page);
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		return PAGE_CONTEXT + "productBrowse";
	}

	/**
	 * Retrieve the product with the specified id.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		logger.info("Getting product with id: " + id);

		Product product = serviceManager.get(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, product);

		return PAGE_CONTEXT + "productShow";
	}

	/**
	 * Retrieve the product with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		logger.info("Edit product with id: " + id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, serviceManager.get(id));
		loadDependencies(model);
		return PAGE_CONTEXT + "productEdit";
	}

	/**
	 * Create a new product and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, new Product());
		loadDependencies(model);
		return PAGE_CONTEXT + "productEdit";
	}

	/**
	 * Create/update a product.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid Product product, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale, @RequestParam(value = "file", required = false) MultipartFile file) {

		if (bindingResult.hasErrors()) {
			model.addAttribute(ControllerConstants.FETCHED_OBJECT, product);
			return PAGE_CONTEXT + "productEdit";
		}

		logger.info("Creating/updating product");

		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("product_save_success", new Object[] {}, locale)));

		// Process upload file
		if (!file.isEmpty() && (file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE) || file.getContentType().equals(MediaType.IMAGE_PNG_VALUE)
				|| file.getContentType().equals(MediaType.IMAGE_GIF_VALUE))) {

			logger.info("File name: " + file.getName());
			logger.info("File size: " + file.getSize());
			logger.info("File content type: " + file.getContentType());

			byte[] fileContent = null;
			String imageString = null;

			try {
				InputStream inputStream = file.getInputStream();
				fileContent = IOUtils.toByteArray(inputStream);

				// Convert byte[] into String image
				imageString = ImageUtil.encodeToString(fileContent);

				product.setPhoto(imageString);

			} catch (IOException ex) {
				logger.error("Error saving uploaded file");
				product.setPhoto(ImageUtil.smallNoImage());
			}
		} else { // File is improper type or no file was uploaded.

			// If product already exists, load its image into the 'product' object.
			if (product.getId() != null) {
				Product savedProduct = serviceManager.get(product.getId());
				product.setPhoto(savedProduct.getPhoto());

			} else {// Else set to default no-image picture.
				product.setPhoto(ImageUtil.smallNoImage());
			}
		}

		if (null != product.getParent() && product.getParent().getId()==null){
			product.setParent(null);
		}

		serviceManager.save(product);

		return REDIRECT + "product/"+product.getId().toString();
		//return "redirect:/" + UrlUtil.encodeUrlPathSegment(product.getId().toString(), httpServletRequest);
	}

	/**
	 * Returns the photo for the product with the specified id.
	 */
	@RequestMapping(value = "/photo/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] downloadPhoto(@PathVariable("id") Long id) {
		Product product = serviceManager.get(id);
		logger.info("Downloading photo for id: {} with size: {}", product.getId(), product.getPhoto().length());

		// Convert String image into byte[]
		byte[] imageBytes = ImageUtil.decode(product.getPhoto());

		return imageBytes;
	}

	/**
	 * Deletes the product with the specified id.
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Long id, Model model, Locale locale) {
		logger.info("Deleting product with id: " + id);
		Product product = serviceManager.get(id);

		if (product != null) {
			serviceManager.delete(product);
			logger.info("Product deleted successfully");

			model.addAttribute("message", new Message("success", messageSource.getMessage("product_delete_success", new Object[] {}, locale)));
		}

		return DEFAULT_PAGED_REDIRECT;
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String resetDatabase(Model model) {
		logger.info("Resetting database to original state");

		/*
		 * bookService.deleteAll(); bookService.restoreDefaultProducts();
		 * 
		 * List<Product> products = bookService.findAll(); model.addAttribute(ControllerConstants.FETCHED_OBJECTS, products);
		 * 
		 * return PAGE_CONTEXT + "productBrowse";
		 */
		return DEFAULT_PAGED_REDIRECT;
	}

	/*@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}*/

	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		List<Product> suggestedCategories = serviceManager.search(keyword);
		return buildCategorySelectedItems(suggestedCategories);
	}

	protected void loadDependencies(Model model){
		//Load categories
		Product workingBizObject = (Product)model.asMap().get(ControllerConstants.FETCHED_OBJECT);
		/*if (!CommonUtility.isNull(workingBizObject) && !CommonUtility.isNull(workingBizObject.getCategory())){
			model.addAttribute("selectedCategory", workingBizObject.getCategory().getId());
		}*/
		model.addAttribute(ControllerConstants.LIST_CATEGORY, super.categoryServiceManager.getAll());
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}
