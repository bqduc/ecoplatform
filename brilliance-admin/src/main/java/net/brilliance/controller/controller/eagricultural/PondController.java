package net.brilliance.controller.controller.eagricultural;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.aquacultural.Pond;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.helper.WebServicingHelper;
import net.brilliance.manager.agricultural.PondManager;
import net.brilliance.util.ImageUtil;
import net.brilliance.util.Message;

@Controller
@RestController
@RequestMapping({ControllerConstants.REQUEST_MAPPING_POND, "/" + ControllerConstants.REQUEST_REST_API_POND})
@PostAuthorize("isAuthenticated()")
public class PondController extends BaseController {
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "stock/";
	private static final String DEFAULT_PAGED_REDIRECT = REDIRECT + "pond/list/1";

	@Inject
	private PondManager serviceManager;

	/**
	 * List all stores.
	 */
	@RequestMapping(value = { "/list", "" }, method = RequestMethod.GET)
	public String list(Model model) {
		cLog.info("Listing stores ...");
		return DEFAULT_PAGED_REDIRECT;
	}

	@RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
	public String listByPage(@PathVariable Integer pageNumber, Model model) {
		cLog.info("Listing stores for page: ", pageNumber, ". At: ", Calendar.getInstance().getTime());

		Page<Pond> page = serviceManager.getList(pageNumber);
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE);
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, page.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, page);
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		return PAGE_CONTEXT + "storeBrowse";
	}

	/**
	 * Retrieve the store with the specified id.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Getting store with id: " + id);

		Pond store = serviceManager.get(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, store);

		return PAGE_CONTEXT + "storeShow";
	}

	/**
	 * Retrieve the store with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		cLog.info("Edit store with id: " + id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, serviceManager.get(id));
		loadDependencies(model);
		return PAGE_CONTEXT + "storeEdit";
	}

	/**
	 * Create a new store and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, new Pond());
		loadDependencies(model);
		return PAGE_CONTEXT + "storeEdit";
	}

	/**
	 * Create/update a store.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String onSave(@Valid Pond store, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale, @RequestParam(value = "file", required = false) MultipartFile file) {

		if (bindingResult.hasErrors()) {
			model.addAttribute(ControllerConstants.FETCHED_OBJECT, store);
			return PAGE_CONTEXT + "storeEdit";
		}

		cLog.info("Creating/updating store");

		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("store_save_success", new Object[] {}, locale)));

		// Process upload file
		if (!file.isEmpty() && (file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE) || file.getContentType().equals(MediaType.IMAGE_PNG_VALUE)
				|| file.getContentType().equals(MediaType.IMAGE_GIF_VALUE))) {

			cLog.info("File name: " + file.getName());
			cLog.info("File size: " + file.getSize());
			cLog.info("File content type: " + file.getContentType());

			byte[] fileContent = null;
			String imageString = null;

			try {
				InputStream inputStream = file.getInputStream();
				fileContent = IOUtils.toByteArray(inputStream);

				// Convert byte[] into String image
				imageString = ImageUtil.encodeToString(fileContent);

				store.setPhoto(imageString);

			} catch (IOException ex) {
				cLog.error("Error saving uploaded file");
				store.setPhoto(ImageUtil.smallNoImage());
			}
		} else { // File is improper type or no file was uploaded.

			// If store already exists, load its image into the 'store' object.
			if (store.getId() != null) {
				Pond savedProduct = serviceManager.get(store.getId());
				store.setPhoto(savedProduct.getPhoto());

			} else {// Else set to default no-image picture.
				store.setPhoto(ImageUtil.smallNoImage());
			}
		}

		serviceManager.save(store);

		return REDIRECT + "store/"+store.getId().toString();
		//return "redirect:/" + UrlUtil.encodeUrlPathSegment(store.getId().toString(), httpServletRequest);
	}

	/**
	 * Returns the photo for the store with the specified id.
	 */
	@RequestMapping(value = "/photo/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] downloadPhoto(@PathVariable("id") Long id) {
		byte[] imageBytes = null;
		Pond store = serviceManager.get(id);
		if (CommonUtility.isNotEmpty(store.getPhoto())){
			cLog.info("Downloading photo for id: {} with size: {}", store.getId(), store.getPhoto().length());

			// Convert String image into byte[]
			imageBytes = ImageUtil.decode(store.getPhoto());
		}else{
			imageBytes = new byte[0];
		}
		return imageBytes;
	}

	/**
	 * Deletes the store with the specified id.
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Long id, Model model, Locale locale) {
		cLog.info("Deleting store with id: " + id);
		Pond store = serviceManager.get(id);

		if (store != null) {
			serviceManager.delete(store);
			cLog.info("Pond deleted successfully");

			model.addAttribute("message", new Message("success", messageSource.getMessage("store_delete_success", new Object[] {}, locale)));
		}

		return DEFAULT_PAGED_REDIRECT;
	}

	/**
	 * Search stores base on input values from search section.
   */
	@RequestMapping(value={"/search/{searchPattern}", "/search"}, method = RequestMethod.GET)
	public String search(@PathVariable Map<String, String> pathVariables, Model model) {
		cLog.info("Searching stores ......");
		Page<Pond> pageContentData = null;
		if (pathVariables.containsKey("searchPattern")){
			cLog.info("Searching measure units with keyword: " + pathVariables.containsKey("searchPattern"));
			Short pageNumber = pathVariables.containsKey("pageNumber")?Short.valueOf(pathVariables.get("pageNumber")):(short)1;
			pageContentData = serviceManager.search(WebServicingHelper.createSearchParameters(pathVariables.get("searchPattern"), pageNumber, null));
		}else{
			pageContentData = serviceManager.getList(1);
		}
		int current = pageContentData.getNumber() + 1; 
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE); 
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, pageContentData.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, pageContentData); 
		model.addAttribute("beginIndex", begin); 
		model.addAttribute("endIndex", end); 
		model.addAttribute("currentIndex", current);

		return PAGE_CONTEXT + "measureUnitBrowse :: resultsList";
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}
