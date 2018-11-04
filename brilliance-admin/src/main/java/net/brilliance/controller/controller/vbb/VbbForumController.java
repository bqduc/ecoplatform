package net.brilliance.controller.controller.vbb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
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
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.contact.ContactProfile;
import net.brilliance.domain.entity.stock.Store;
import net.brilliance.domain.entity.vbb.VbbForum;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.helper.WebServicingHelper;
import net.brilliance.manager.contact.ContactProfileManager;
import net.brilliance.manager.vbb.VbbForumManager;
import net.brilliance.manager.vbb.VirtualBulletinBoardHelper;
import net.brilliance.model.Bucket;
import net.brilliance.model.SelectItem;
import net.brilliance.service.helper.GlobalDataServiceHelper;
import net.brilliance.service.helper.GlobalRepositoryHelper;
import net.brilliance.util.ImageUtil;
import net.brilliance.util.Message;

@RequestMapping("/" + ControllerConstants.REQUEST_URI_FORUM)
@Controller
public class VbbForumController extends BaseController {
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "vbb/forum";
	private static final String DEFAULT_PAGED_REDIRECT = REDIRECT + ControllerConstants.REQUEST_URI_FORUM + "/list/1";

	@Inject
	private VbbForumManager serviceManager;

	@Inject
	private ContactProfileManager contactManager;

	@Inject
	private VirtualBulletinBoardHelper virtualBulletinBoardHelper;

	@Inject
	private GlobalRepositoryHelper globalRepositoryHelper;

	@Inject
	private GlobalDataServiceHelper globalDataRepositoryHelper;

	/**
	 * List all forums.
	 */
	@RequestMapping(value = { "/list", "" }, method = RequestMethod.GET)
	public String onListing(Model model) {
		cLog.info("Enter listing forums ...");
		try {
			virtualBulletinBoardHelper.setupDefaultData();
		} catch (Exception e) {
			cLog.error(CommonUtility.getStackTrace(e));
		}
		cLog.info("Leave listing forums ...");
		return DEFAULT_PAGED_REDIRECT;
	}

	@RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
	public String listByPage(@PathVariable Integer pageNumber, Model model) {
		cLog.info("Listing forums for page: ", pageNumber, ". At: ", Calendar.getInstance().getTime());

		Page<VbbForum> page = serviceManager.getList(pageNumber);
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE);
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, page.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, page);
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		
		return PAGE_CONTEXT + "Browse";
	}

	/**
	 * Import employee objects.
	 */
	@Override
	protected String performImport(Model model, HttpServletRequest request) {
		InputStream inputStream = null;
		Bucket forumDataBucket = null;
		String datasheet = "Sheet1";
		try {
			virtualBulletinBoardHelper.importForums();
			/*inputStream = CommonUtility.getClassPathResourceInputStream(globalRepositoryHelper.getDataDirectory() + "forum-structure.xlsx");
			forumDataBucket = this.globalDataRepositoryHelper.readSpreadsheetData(inputStream, new String[]{datasheet});

			serviceManager.importBusinessObjects(forumDataBucket, datasheet, 1);*/
		} catch (Exception e) {
			cLog.error(CommonUtility.getStackTrace(e));
		} finally{
			try {
				CommonUtility.closeInputStream(inputStream);
			} catch (Exception e2) {
				cLog.error(CommonUtility.getStackTrace(e2));
			}
		}
		return PAGE_CONTEXT + "employeeBrowse";
	}

	/**
	 * Retrieve the forum with the specified id.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Getting forum with id: " + id);

		VbbForum forum = serviceManager.get(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, forum);

		return PAGE_CONTEXT + "Show";
	}

	/**
	 * Retrieve the forum with the specified id.
	 */
	@RequestMapping(value = "/listTopics/{forumId}", method = RequestMethod.GET)
	public String showTopics(@PathVariable("forumId") Long forumId, Model model) {
		cLog.info("Getting topic with forum id: " + forumId);

		VbbForum fetchedObject = serviceManager.get(forumId);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedObject);

		return PAGE_CONTEXT + "Topics";
	}

	/**
	 * Retrieve the forum with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		cLog.info("Edit forum with id: " + id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, serviceManager.get(id));
		loadDependencies(model);
		return PAGE_CONTEXT + "Edit";
	}

	/**
	 * Create a new forum and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, new Store());
		loadDependencies(model);
		return PAGE_CONTEXT + "Edit";
	}

	/**
	 * Create/update a forum.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String onSave(@Valid VbbForum fetchedDataObject, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale, @RequestParam(value = "file", required = false) MultipartFile file) {

		if (bindingResult.hasErrors()) {
			model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedDataObject);
			return PAGE_CONTEXT + "Edit";
		}

		cLog.info("Creating/updating forum");

		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("forum_save_success", new Object[] {}, locale)));

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

				fetchedDataObject.setPhoto(imageString);

			} catch (IOException ex) {
				cLog.error("Error saving uploaded file");
				fetchedDataObject.setPhoto(ImageUtil.smallNoImage());
			}
		} else { // File is improper type or no file was uploaded.

			// If forum already exists, load its image into the 'forum' object.
			if (fetchedDataObject.getId() != null) {
				VbbForum savedProduct = serviceManager.get(fetchedDataObject.getId());
				fetchedDataObject.setPhoto(savedProduct.getPhoto());

			} else {// Else set to default no-image picture.
				fetchedDataObject.setPhoto(ImageUtil.smallNoImage());
			}
		}

		//Push back dependencies data
		if (null != fetchedDataObject.getParent() && null != fetchedDataObject.getParent().getName() && fetchedDataObject.getParent().getId()==null){
			VbbForum parent = this.serviceManager.getByName(fetchedDataObject.getParent().getName());
			fetchedDataObject.setParent(parent);
		}

		/*if (null != forum.getAuthor() && null != forum.getAuthor().getCode() && null==forum.getAuthor().getId()){
			Contact coordinator = this.contactManager.getByCode(forum.getAuthor().getCode());
			forum.setAuthor(coordinator);
		}*/

		serviceManager.save(fetchedDataObject);

		return REDIRECT + "forum/"+fetchedDataObject.getId().toString();
		//return "redirect:/" + UrlUtil.encodeUrlPathSegment(forum.getId().toString(), httpServletRequest);
	}

	/**
	 * Returns the photo for the forum with the specified id.
	 */
	@RequestMapping(value = "/photo/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] downloadPhoto(@PathVariable("id") Long id) {
		byte[] imageBytes = null;
		VbbForum forum = serviceManager.get(id);
		if (CommonUtility.isNotEmpty(forum.getPhoto())){
			cLog.info("Downloading photo for id: {} with size: {}", forum.getId(), forum.getPhoto().length());

			// Convert String image into byte[]
			imageBytes = ImageUtil.decode(forum.getPhoto());
		}else{
			imageBytes = new byte[0];
		}
		return imageBytes;
	}

	/**
	 * Deletes the forum with the specified id.
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Long id, Model model, Locale locale) {
		cLog.info("Deleting forum with id: " + id);
		VbbForum forum = serviceManager.get(id);

		if (forum != null) {
			serviceManager.delete(forum);
			cLog.info("Forum deleted successfully");

			model.addAttribute("message", new Message("success", messageSource.getMessage("forum_delete_success", new Object[] {}, locale)));
		}

		return DEFAULT_PAGED_REDIRECT;
	}

	//Default method for suggest parent object
	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		Page<VbbForum> searchResults =  serviceManager.search(keyword, null);
		return buildCategorySelectedItems(searchResults.getContent());
	}

	@RequestMapping(value = "/suggestCoordinator", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggestCoordinator(@RequestParam("term") String keyword, HttpServletRequest request) {
		cLog.info("Enter keyword for coordinator: " + keyword);
		Page<ContactProfile> suggestedContacts = contactManager.search(keyword, null);
		return buildCategorySelectedItems(suggestedContacts.getContent(), "id", "code", "fullName");
	}

	/**
	 * Search forums base on input values from search section.
   */
	@RequestMapping(value={"/search/{searchPattern}", "/search"}, method = RequestMethod.GET)
	public String search(@PathVariable Map<String, String> pathVariables, Model model) {
		cLog.info("Searching forums ......");
		Page<VbbForum> pageContentData = null;
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

	@Override
	protected void onPostConstruct() {
		postConstructData("Setup forums");
	}

	@Override
	protected void constructData() {
		//vBulletinBoardHelper.setupDefaultData();
	}
}
