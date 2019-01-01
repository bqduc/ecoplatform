package net.brilliance.controller.general;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

import com.google.gson.Gson;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.domain.entity.general.Attachment;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.model.ui.UISelectItem;
import net.brilliance.service.api.contact.ContactService;
import net.brilliance.service.api.general.AttachmentService;

@Controller
@RequestMapping(ControllerConstants.URI_ATTACHMENT)
public class AttachmentController extends BaseController { 
	private static final String PAGE_CONTEXT_PREFIX = ControllerConstants.CONTEXT_WEB_PAGES + "general/storage/attachment";

	@Inject
	private AttachmentService businessManager;

	@Inject
	private ContactService contactService;

	@RequestMapping(path={"/", ""}, method=RequestMethod.GET)
	public String viewDefaultPage(){
		return getDefaultPage();
	}

	@Override
	protected String performListing(Model model, HttpServletRequest request) {
		return PAGE_CONTEXT_PREFIX + ControllerConstants.BROWSE;
	}

	/**
	 * Export catalogs.
   */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String exports(Model model, HttpServletRequest request) {
		logger.info("Exporting enterprise .....");
		return PAGE_CONTEXT_PREFIX + ControllerConstants.BROWSE;
	}

	/**
	 * Create a new department and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		Attachment newAttachment = Attachment
		.builder()
		.build();
		model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, newAttachment);
		return PAGE_CONTEXT_PREFIX + ControllerConstants.EDIT;
	}

	/**
	 * Create/update a contact.
	*/
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@Valid Attachment uiBizObject, BindingResult bindingResult,
			Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale, @RequestParam(value = "file", required = false) MultipartFile file) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, uiBizObject);
			return PAGE_CONTEXT_PREFIX + ControllerConstants.EDIT;
		}

		logger.info("Creating/updating an attachment");
		
		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("general_save_success", new Object[] {}, locale)));

		try {
			uiBizObject.setName(file.getOriginalFilename());
			uiBizObject.setMimetype(file.getContentType());
			uiBizObject.setData(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		businessManager.saveOrUpdate(uiBizObject);
		//systemSequenceManager.registerSequence(uiBizObject.getCode());

		//TODO: Pay attention please
		String ret = "redirect:/attachment/";
		return ret;
	}

	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadAttachment(@PathVariable("id") Long id) {
		Attachment attachment = businessManager.getObject(id);
		return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
					.body(attachment.getData());	
	}

	/**
	 * Retrieve the book with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
			model.addAttribute(net.brilliance.common.CommonConstants.FETCHED_OBJECT, businessManager.getObject(id));
			return PAGE_CONTEXT_PREFIX + ControllerConstants.EDIT;
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		logger.info("Fetch business object of catalogue subtype with id: " + id);

		model.addAttribute(ControllerConstants.FETCHED_OBJECT, businessManager.getObject(id));
		
		return PAGE_CONTEXT_PREFIX + ControllerConstants.VIEW;
	}

	@Override
	protected String performSearch(SearchParameter params) {
		Map<String, Object> parameters = new HashMap<>();
		Page<Attachment> pageContentData = businessManager.search(parameters);
		params.getModel().addAttribute(ControllerConstants.FETCHED_OBJECT, pageContentData);
		/*HttpSession session = super.getSession();
		session.setAttribute(CommonConstants.CACHED_PAGE_MODEL, params.getModel());*/
		Gson gson = new Gson();
		//return gson.toJson(pageContentData.getContent());
		return PAGE_CONTEXT_PREFIX + "Browse :: result-teable " + gson.toJson(pageContentData.getContent());
	}

	private String getDefaultPage(){
		return PAGE_CONTEXT_PREFIX + ControllerConstants.BROWSE;
	}

	@RequestMapping(value = "/suggestCoordinator", method = RequestMethod.GET)
	public @ResponseBody List<UISelectItem> suggestObject(HttpServletRequest request, @RequestParam("keyword") String keyword) {
		logger.info("Enter keyword: " + keyword);
		Page<Contact> contacts = contactService.searchObjects(keyword, null);
		System.out.println(contacts.getContent());
		List<UISelectItem> uiSelectItems = ListUtility.createSelectItems(contacts.getContent(), 
				ListUtility.createMap(
  				CommonConstants.PROPERTY_KEY, "id", 
  				CommonConstants.PROPERTY_CODE, "code", 
  				CommonConstants.PROPERTY_NAME, "name", 
  				CommonConstants.PROPERTY_NAME_LOCAL, "nameLocal")
				);
		if (CommonUtility.isNull(uiSelectItems)){
			uiSelectItems = ListUtility.createArrayList();
		}
		return uiSelectItems;
	}
}
