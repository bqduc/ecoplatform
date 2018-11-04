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
import net.brilliance.domain.entity.vbb.VbbPost;
import net.brilliance.domain.entity.vbb.VbbThread;
import net.brilliance.domain.entity.vbb.VbbTopic;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.helper.WebServicingHelper;
import net.brilliance.manager.contact.ContactProfileManager;
import net.brilliance.manager.vbb.VbbPostManager;
import net.brilliance.manager.vbb.VbbThreadManager;
import net.brilliance.manager.vbb.VbbTopicManager;
import net.brilliance.model.SelectItem;
import net.brilliance.util.ImageUtil;
import net.brilliance.util.Message;

@RequestMapping("/" + ControllerConstants.REQUEST_URI_THREAD)
@Controller
public class VbbThreadController extends BaseController {
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "vbb/" + ControllerConstants.REQUEST_URI_THREAD;
	private static final String DEFAULT_PAGED_REDIRECT = REDIRECT + ControllerConstants.REQUEST_URI_THREAD + "/list/1";

	@Inject
	private VbbTopicManager topicManager;

	@Inject
	private VbbThreadManager serviceManager;

	@Inject
	private VbbPostManager postManager;

	@Inject
	private ContactProfileManager contactManager;

	/**
	 * List all threads.
	 */
	@RequestMapping(value = { "/listTopicThreads/{topicId}"}, method = RequestMethod.GET)
	public String listThreadsOfTopic(@PathVariable Long topicId, Model model) {
		cLog.info("Listing threads of topics: " + topicId);
		//return DEFAULT_PAGED_REDIRECT;
		return "redirect:/thread/list/" + topicId +"/1";
	}

	/**
	 * List all threads.
	 */
	@RequestMapping(value = { "/list", "" }, method = RequestMethod.GET)
	public String list(Model model) {
		cLog.info("Listing threads ...");
		return DEFAULT_PAGED_REDIRECT;
	}

	@RequestMapping(value = "/list/{topicId}/{pageNumber}", method = RequestMethod.GET)
	public String listByPage(@PathVariable Long topicId, @PathVariable Integer pageNumber, Model model) {
		cLog.info("Listing threads for page: ", pageNumber, ". At: ", Calendar.getInstance().getTime());

		VbbTopic topic = topicManager.get(topicId);
		model.addAttribute(CommonConstants.FETCHED_MASTER_OBJECT, topic);

		Page<VbbThread> page = serviceManager.getTopicThreads(topicId, null);
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
	 * Retrieve the thread with the specified id.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		cLog.info("Getting thread with id: " + id);

		VbbThread thread = serviceManager.get(id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, thread);

		return PAGE_CONTEXT + "Show";
	}

	/**
	 * Retrieve the thread with the specified id.
	 */
	@RequestMapping(value = "/postOfThread{threadId}", method = RequestMethod.GET)
	public String showPostsOfThread(@PathVariable("threadId") Long threadId, Model model) {
		cLog.info("Getting posts of thread id: " + threadId);

		VbbThread thread = serviceManager.get(threadId);
		model.addAttribute(ControllerConstants.FETCHED_MASTER_OBJECT, thread);

		Page<VbbPost> page = postManager.getThreadPosts(threadId, null);
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE);
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, page.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, page);
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		return PAGE_CONTEXT + "Posts";
	}

	/**
	 * Retrieve the thread with the specified id for the update form.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		cLog.info("Edit thread with id: " + id);
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, serviceManager.get(id));
		loadDependencies(model);
		return PAGE_CONTEXT + "Edit";
	}

	/**
	 * Create a new thread and place in Model attribute.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, new Store());
		loadDependencies(model);
		return PAGE_CONTEXT + "Edit";
	}

	/**
	 * Create/update a thread.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String onSave(@Valid VbbThread fetchedDataObject, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale, @RequestParam(value = "file", required = false) MultipartFile file) {

		if (bindingResult.hasErrors()) {
			model.addAttribute(ControllerConstants.FETCHED_OBJECT, fetchedDataObject);
			return PAGE_CONTEXT + "Edit";
		}

		cLog.info("Creating/updating thread");

		model.asMap().clear();
		//redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("thread_save_success", new Object[] {}, locale)));

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

			// If thread already exists, load its image into the 'thread' object.
			if (fetchedDataObject.getId() != null) {
				VbbThread savedProduct = serviceManager.get(fetchedDataObject.getId());
				fetchedDataObject.setPhoto(savedProduct.getPhoto());

			} else {// Else set to default no-image picture.
				fetchedDataObject.setPhoto(ImageUtil.smallNoImage());
			}
		}

		//Push back dependencies data
		if (null != fetchedDataObject.getParent() && null != fetchedDataObject.getParent().getName() && fetchedDataObject.getParent().getId()==null){
			VbbThread parent = this.serviceManager.getByName(fetchedDataObject.getParent().getName());
			fetchedDataObject.setParent(parent);
		}

		serviceManager.save(fetchedDataObject);

		return REDIRECT + "thread/"+fetchedDataObject.getId().toString();
	}

	/**
	 * Returns the photo for the thread with the specified id.
	 */
	@RequestMapping(value = "/photo/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] downloadPhoto(@PathVariable("id") Long id) {
		byte[] imageBytes = null;
		VbbThread thread = serviceManager.get(id);
		if (CommonUtility.isNotEmpty(thread.getPhoto())){
			cLog.info("Downloading photo for id: {} with size: {}", thread.getId(), thread.getPhoto().length());

			// Convert String image into byte[]
			imageBytes = ImageUtil.decode(thread.getPhoto());
		}else{
			imageBytes = new byte[0];
		}
		return imageBytes;
	}

	/**
	 * Deletes the thread with the specified id.
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Long id, Model model, Locale locale) {
		cLog.info("Deleting thread with id: " + id);
		VbbThread thread = serviceManager.get(id);

		if (thread != null) {
			serviceManager.delete(thread);
			cLog.info("Thread deleted successfully");

			model.addAttribute("message", new Message("success", messageSource.getMessage("thread_delete_success", new Object[] {}, locale)));
		}

		return DEFAULT_PAGED_REDIRECT;
	}

	//Default method for suggest parent object
	@Override
	protected List<SelectItem> suggestItems(String keyword) {
		Page<VbbThread> searchResults =  serviceManager.search(keyword, null);
		return buildCategorySelectedItems(searchResults.getContent());
	}

	@RequestMapping(value = "/suggestCoordinator", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggestCoordinator(@RequestParam("term") String keyword, HttpServletRequest request) {
		cLog.info("Enter keyword for coordinator: " + keyword);
		Page<ContactProfile> suggestedContacts = contactManager.search(keyword, null);
		return buildCategorySelectedItems(suggestedContacts.getContent(), "id", "code", "fullName");
	}

	/**
	 * Search threads base on input values from search section.
   */
	@RequestMapping(value={"/search/{searchPattern}", "/search"}, method = RequestMethod.GET)
	public String search(@PathVariable Map<String, String> pathVariables, Model model) {
		cLog.info("Searching threads ......");
		Page<VbbThread> pageContentData = null;
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
