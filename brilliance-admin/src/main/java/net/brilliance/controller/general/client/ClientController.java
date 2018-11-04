package net.brilliance.controller.general.client;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.brilliance.common.CommonConstants;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.domain.entity.contact.ClientProfile;
import net.brilliance.framework.model.SearchCondition;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.manager.contact.ClientProfileManager;

@Controller
@RequestMapping("client")
//@Secured
public class ClientController extends BaseController{
	private static final String PAGE_CONTEXT = ControllerConstants.CONTEXT_WEB_PAGES + "general/clients/";

	private static final String CONTEXT_DIR = "pages/general/clients/";
	private static final String PAGE_PREFIX = "clientProfile";
	private static final String PAGE_CONTEXT_PREFIX = CONTEXT_DIR + PAGE_PREFIX;
	private static final String CONTROLLER_REDIRECT = "redirect:/client";
	
	
	@Inject
	private ClientProfileManager clientService;

	@RequestMapping(value = "/")
	public String index() {
		if (clientService.getAll().size()<1) {
			clientService.restoreDefaultClients();
		}
		return PAGE_CONTEXT_PREFIX + PAGE_POSTFIX_BROWSE;
		//return CONTROLLER_REDIRECT + "/list/1";
	}

	@RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
	public String list(@PathVariable Integer pageNumber, Model model) {
		Page<ClientProfile> page = clientService.getList(pageNumber);
		int current = page.getNumber() + 1; 
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE); 
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, page.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, page); 
		model.addAttribute("beginIndex", begin); 
		model.addAttribute("endIndex", end); 
		model.addAttribute("currentIndex", current);
		return PAGE_CONTEXT_PREFIX + PAGE_POSTFIX_BROWSE;
	}

	/**
	 * Search client profiles.
   */
	@RequestMapping(value={"/search/{searchPattern}", "/search"}, method = RequestMethod.GET)
	public String search(@PathVariable Map<String, String> pathVariables, Model model, @PageableDefault Pageable pageable) {
		cLog.info("Searching all measure units");
		Page<ClientProfile> pageContentData = null;
		if (pathVariables.containsKey("searchPattern")){
			SearchCondition searchCondition = SearchCondition.getInstance(pageable, pathVariables);
			pageContentData = clientService.search(searchCondition);
			/*System.out.println("Number of items: " + pageContentData.getContent());
			model.addAttribute("searchKeyword", pathVariables.get("searchPattern"));
			cLog.info("Searching measure units with keyword: " + pathVariables.containsKey("searchPattern"));
			Short pageNumber = pathVariables.containsKey("pageNumber")?Short.valueOf(pathVariables.get("pageNumber")):(short)1;
			pageContentData = clientService.search(WebServicingHelper.createSearchParameters(pathVariables.get("searchPattern"), pageNumber, null));*/
		}else{
			pageContentData = clientService.getList(1);
		}

		int current = pageContentData.getNumber() + 1; 
		int begin = Math.max(1, current - CommonConstants.DEFAULT_PAGE_SIZE); 
		int end = Math.min(begin + CommonConstants.DEFAULT_PAGE_SIZE, pageContentData.getTotalPages());
		model.addAttribute(ControllerConstants.FETCHED_OBJECTS, pageContentData); 
		model.addAttribute("beginIndex", begin); 
		model.addAttribute("endIndex", end); 
		model.addAttribute("currentIndex", current);

		return PAGE_CONTEXT + "clientProfileBrowse :: resultsList";
	}	

	@RequestMapping("/add")
	public String add(Model model) {
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, ClientProfile.getInstance());
		return PAGE_CONTEXT_PREFIX + "Form";

	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute(ControllerConstants.FETCHED_OBJECT, clientService.get(id));
		return PAGE_CONTEXT_PREFIX + "Form";

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ClientProfile client, final RedirectAttributes ra) {
		clientService.save(client);
		ra.addFlashAttribute("successFlash", "ClientProfile was saved success.");
		return CONTROLLER_REDIRECT;

	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		clientService.delete(id);
		return CONTROLLER_REDIRECT;
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}
