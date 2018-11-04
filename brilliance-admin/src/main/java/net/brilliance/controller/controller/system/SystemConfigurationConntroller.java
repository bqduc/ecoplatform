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
package net.brilliance.controller.controller.system;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.controller.auth.AuthenticationConntroller;
import net.brilliance.controller.controller.constants.ControllerConstants;
import net.brilliance.controller.coordinator.PagesMapping;
import net.brilliance.manager.dashboard.DashboardManager;

@Controller
@RequestMapping(ControllerConstants.REQUEST_URI_SYSTEM)
public class SystemConfigurationConntroller {
	private Logger log = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private DashboardManager dashboardManager;

	@Inject
	private PasswordEncoder passwordEncoder;

	@GetMapping({"", "/index", "/index2" })
	public String viewIndex(Model model, HttpServletRequest request) {
		if (null==request.getSession().getAttribute(AuthenticationConntroller.IS_AUTHENTICATED_ATTRIBUTE)){
			request.getSession().setAttribute(AuthenticationConntroller.IS_AUTHENTICATED_ATTRIBUTE, AuthenticationConntroller.SHOW_LOGIN);
		}

		/*model.addAttribute("totalClientProfiles", dashboardManager.getTotalClientProfiles());
		model.addAttribute("totalForums", dashboardManager.getTotalForums());*/
		dashboardManager.syncData();
		model.addAttribute("dashboardManager", dashboardManager);
		log.info("Rendering index page...");
		return "pages/public/dashboard";
		//return "dashboard/index";
	}

	@GetMapping({ "/pages/**"})
	public String viewPage(HttpServletRequest request) {
		String viewUri = getViewPage(request);
		log.info("-----------------------view page uri: " + viewUri + "-----------------------");
		return viewUri;
	}

	@GetMapping({ "/*"})
	public String viewAll(HttpServletRequest request) {
		log.info("-----------------------viewAll-----------------------");
		String viewUri = getViewPage(request);
		log.info("-----------------------viewAll: " + viewUri + "-----------------------");
		return viewUri;
	}

	private String getViewPage(HttpServletRequest request) {
		String encodedPwd = passwordEncoder.encode("vP3@x5");
		System.out.println(encodedPwd);
		
		
		String requestURI = request.getRequestURI();
		log.debug("Request URI: " + requestURI);
		/*if (requestURI.endsWith("clientLogin"))
			return "pages/auth/clientLogin";*///"pages/examples/clientLogin";

		if ("/login2".equalsIgnoreCase(requestURI))
			return "pages/examples/login2";//"signin";//return CommonConstants.loginViewName;//"signin";

		if ("/login".equalsIgnoreCase(requestURI))
			return "pages/examples/login";//"signin";//return CommonConstants.loginViewName;//"signin";

		if ("/generalBizShowcase".equalsIgnoreCase(requestURI))
			return "general/biz-showcase";

		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		log.debug("Path: " + path);
		if (path.endsWith(".html")){
			path = path.substring(1, path.lastIndexOf(".html"));
		}
		log.debug("Rendering page with path: " + path);

		if (path.startsWith("/")){
			path = path.substring(1);
		}
		String mappedView = PagesMapping.getView(path);
		if (mappedView.trim().length() > 2)
			return mappedView;

		return path;//"index";
	}
}
