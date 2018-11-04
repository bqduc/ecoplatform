/*
* Copyright 2017, Bui Quy Duc
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.brilliance.controller.auth;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.controller.base.BaseController;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.dispatch.SecruityServiceACL;
import net.brilliance.domain.dto.UserDTO;
import net.brilliance.domain.entity.admin.Authority;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.domain.entity.contact.ClientProfile;
import net.brilliance.exceptions.AuthenticationException;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.manager.auth.AuthenticationServiceManager;
import net.brilliance.service.helper.ClientServicesHelper;
import net.brilliance.util.Message;

@Controller
@RequestMapping("auth")
public class AuthenticationConntroller extends BaseController {
	private Logger log = GlobalLoggerFactory.getLogger(this.getClass());

	protected static String PAGE_CONTEXT_CLIENTS_PREFIX = "pages/general/clients/";
	protected static String PAGE_CONTEXT_PREFIX = "pages/auth/";

	public static final String SHOW_LOGIN = "show login";
	public static final String SHOW_LOGOUT = "show logout";
	public static final String IS_AUTHENTICATED_ATTRIBUTE = "authenticated";
	public static final String AUTHENTICATED_ATTRIBUTE = "authenticated";

	@Inject
	private ClientServicesHelper clientServicesHelper;

	@Inject
	private AuthenticationServiceManager authenticationServiceManager;

	@Inject
	private SecruityServiceACL secruityServiceACL;

	@GetMapping({ "/myClientProfile" })
	public String viewClientProfilePage(Model model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ClientProfile myClientProfile = null;
		try {
			if (CommonUtility.isEmpty(auth)){
				//Return to the error page here.
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return PAGE_CONTEXT_CLIENTS_PREFIX + "myClientProfileShow";
			}

			if (auth.getPrincipal() instanceof UserAccount){
				myClientProfile = clientServicesHelper.getClient((UserAccount)auth.getPrincipal());
			}else if (auth.getPrincipal() instanceof String){
				myClientProfile = clientServicesHelper.getClient((String)auth.getPrincipal());
			}
			model.addAttribute(ControllerConstants.FETCHED_OBJECT, myClientProfile);
		} catch (Exception e) {
			log.error("viewClientProfilePage", e);
		}
		return PAGE_CONTEXT_CLIENTS_PREFIX + "myClientProfileShow";
	}

	@GetMapping({ "/clientLogin" })
	public String doViewLoginPage(Model model) {
		UserAccount user = new UserAccount();
		model.addAttribute("user", user);
		return PAGE_CONTEXT_PREFIX + "clientLogin";
	}

	@GetMapping({ "/clientLogout" })
	public String onLogout(HttpServletRequest request, Model model) {
		try {
      HttpSession session = request.getSession(false);
      if (session != null) {
          session.invalidate();
      }
			SecurityContextHolder.clearContext();
      for(Cookie cookie : request.getCookies()) {
        cookie.setMaxAge(0);
      }
      request.setAttribute(IS_AUTHENTICATED_ATTRIBUTE, "false");
      request.getSession().setAttribute(IS_AUTHENTICATED_ATTRIBUTE, SHOW_LOGIN);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "redirect:/";//dashboard/index";
	}

	@PostMapping({ "/login" })
	public String onLogin(@ModelAttribute("user") UserAccount user, BindingResult result, Model model, HttpServletRequest request) {
		UserAccount authUser = null;
		String response = "";
		int errorCode = AuthenticationException.ERROR_NONE;
		try {
			authUser = authenticationServiceManager.authenticate(user.getSsoId(), user.getPassword());
			if (null != authUser) {
				Authentication auth = new UsernamePasswordAuthenticationToken(authUser, authUser.getPassword(), authUser.getUserDetails().getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
		    request.getSession().setAttribute(IS_AUTHENTICATED_ATTRIBUTE, SHOW_LOGOUT);

		    request.getSession().setAttribute("authenticatedUser", authUser);

		    Map<String, Authority> mappedAuthority = secruityServiceACL.populateAuthorities();
				request.getSession().setAttribute(ControllerConstants.AUTHORITY_MAP, mappedAuthority);
			}
		} catch (AuthenticationException e) {
			log.error("processLoginForm", e);
			errorCode = e.getErrorCode();
		}
		if (authUser != null){
			response = "redirect:/";
		}else{
			response = PAGE_CONTEXT_PREFIX + "clientLogin";
			model.addAttribute("loginFail", Boolean.TRUE);
			switch(errorCode){
  			case AuthenticationException.ERROR_INVALID_PRINCIPAL:
  				model.addAttribute("messageType", "label.invalidPrincipal");
  				break;
  			case AuthenticationException.ERROR_INVALID_CREDENTIAL:
  				model.addAttribute("messageType", "label.invalidCedential");
  				break;
  			case AuthenticationException.ERROR_INACTIVE:
  				model.addAttribute("messageType", "label.invalidStatus");
  				break;
  			case AuthenticationException.ERROR_INVALID_PERMISSION:
  				model.addAttribute("messageType", "label.invalidPermissions");
  				break;
			}
		}
		return response;
	}

	@GetMapping({ "/register" })
	public String viewRegisterPage(Model model) {
		System.out.println("-----------------------Render register page-----------------------");
		
		return PAGE_CONTEXT_PREFIX + "register";
	}

	@GetMapping({ "/confirm/{token}" })
	public String confirm(@PathVariable("token") String token, Model model) {
		log.info("Confirm profile: " + token);
		try {
			ClientProfile confirmedClientProfile = clientServicesHelper.confirmClient(token);
			System.out.println(confirmedClientProfile);			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "dashboard/index";
	}

	@PostMapping({ "/register" })
	public String processRegisterForm(@ModelAttribute("user") UserDTO user, BindingResult result, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, Locale locale) {
		try {
			if (!"on".equalsIgnoreCase(request.getParameter("agreeTerms"))){
				Message errorMsg = new Message("error", messageSource.getMessage("label.general.saveError", new String[] {messageSource.getMessage("label.object.client", null, locale)}, locale));
				redirectAttributes.addFlashAttribute("message", errorMsg);
				return PAGE_CONTEXT_PREFIX + "register";
			}

			if (!user.getPassword().contentEquals(request.getParameter("passwordConfirm"))){
				Message errorMsg = new Message("error", messageSource.getMessage("label.general.saveError", new String[] {messageSource.getMessage("label.object.client", null, locale)}, locale));
				redirectAttributes.addFlashAttribute("message", errorMsg);
				return PAGE_CONTEXT_PREFIX + "register";
			}

			clientServicesHelper.registerBasicClient(user, locale);
		} catch (Exception e) {
			log.error(CommonUtility.getStackTrace(e));
		}
		return "dashboard/index";
	}

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}
