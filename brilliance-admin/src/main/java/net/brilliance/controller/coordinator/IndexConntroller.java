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
package net.brilliance.controller.coordinator;

import java.util.Enumeration;
import java.util.Map;

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
import net.brilliance.domain.entity.sales.Account;
import net.brilliance.manager.dashboard.DashboardManager;
import net.brilliance.service.api.dashboard.DashletService;

@Controller
public class IndexConntroller {
	private Logger log = GlobalLoggerFactory.getLogger(this.getClass());

	private final static String DASHBOARD_DIR = "pages/public/dashboard/";

	private final static String CACHE_OBJECTS_KEY = "cachedDashboardData";

	public enum DashboardPages {
		master(DASHBOARD_DIR + "master"),
		crm(DASHBOARD_DIR + "crm"),
		inventory(DASHBOARD_DIR + "inventory"),
		distribution(DASHBOARD_DIR + "distribution"),
		backOffice(DASHBOARD_DIR + "backOffice");

		private DashboardPages(String page){
			this.page = page;
		}
		public String getPage() {
			return page;
		}

		public static DashboardPages findPage(String requestUri) {
			int lastSeparator = requestUri.lastIndexOf("/");
			if (-1 == lastSeparator)
				return DashboardPages.master;

			return DashboardPages.valueOf(requestUri.substring(lastSeparator+1));
		}

		private String page;
	};

	@Inject 
	private DashletService dashletService;

	@Inject
	private DashboardManager dashboardManager;

	@Inject
	private PasswordEncoder passwordEncoder;

	@GetMapping({ /* "/", */"", "/index", "/index2" })
	public String viewIndex(Model model, HttpServletRequest request) {
		if (null==request.getSession().getAttribute(AuthenticationConntroller.IS_AUTHENTICATED_ATTRIBUTE)){
			request.getSession().setAttribute(AuthenticationConntroller.IS_AUTHENTICATED_ATTRIBUTE, AuthenticationConntroller.SHOW_LOGIN);
		}

		/*model.addAttribute("totalClientProfiles", dashboardManager.getTotalClientProfiles());
		model.addAttribute("totalForums", dashboardManager.getTotalForums());*/
		dashboardManager.syncData();
		model.addAttribute("dashboardManager", dashboardManager);
		
		model.addAttribute("dashlets", dashletService.getObjects());
		log.info("Rendering index page...");
		
		log.info("Account: " + Account.createInstance());
		return DashboardPages.master.getPage();
		//return "pages/public/dashboard";
	}

	private void prepareDashboardData(Model model, HttpServletRequest request){
		Map<?, ?> syncData = (Map<?, ?>)request.getSession().getAttribute(CACHE_OBJECTS_KEY);
		if (null==syncData){
			syncData = dashboardManager.syncData();
			request.getSession().setAttribute(CACHE_OBJECTS_KEY, syncData);
		} 

		model.addAttribute("dashboardManager", dashboardManager);
		model.addAttribute(CACHE_OBJECTS_KEY, syncData);
	}

	@GetMapping({ "/dashboard/**"})
	public String requestDashboardPage(Model model, HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		log.debug("Request URI: " + requestURI);

		prepareDashboardData(model, request);
		DashboardPages dashboardPage = DashboardPages.findPage(requestURI);
		return dashboardPage.getPage();
	}

	@GetMapping({ "/pages/**"})
	public String viewPage(HttpServletRequest request) {
		String viewUri = getViewPage(request);
		log.info("-----------------------view page uri: " + viewUri + "-----------------------");
		return viewUri;
	}

	private String getViewPage(HttpServletRequest request) {
		String encodedPwd = passwordEncoder.encode("vP3@x5");
		System.out.println(encodedPwd);
		
		System.out.println("Come to view pafe");
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
	
	@RequestMapping("404")
	String get404ErrorPage(Model model, HttpServletRequest request) {
		StringBuilder sbBuffer = null;
		String key = null;
		Object[] values;
		Enumeration<String> enums = request.getSession().getAttributeNames();
		while (enums.hasMoreElements()){
			sbBuffer = new StringBuilder();
			key = (String)enums.nextElement();
			try {
				values = (Object[])request.getSession().getAttribute(key);
				for (Object currObject :values){
					sbBuffer.append(currObject).append("#");
				}
				sbBuffer.deleteCharAt(sbBuffer.length()-1);
			} catch (Exception e) {
				sbBuffer.append(request.getSession().getAttribute(key));
			}
			model.addAttribute(key, sbBuffer.toString());
		}
		model.addAttribute("trace", "An error have to be trace");
		return "pages/error/error404Page";
	}

	@RequestMapping("401")
	String get401ErrorPage() {
	    return "401";
	}	
}
