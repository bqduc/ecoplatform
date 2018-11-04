/**
 * 
 */
package net.brilliance.controller.coordinator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ducbq
 *
 */
public class PagesMapping {
	private static Map<String, String> mappedPages = new HashMap<>();

	static{
		mappedPages.put("login", "pages/examples/login");

		mappedPages.put("clientLogin", "pages/auth/clientLogin");
		mappedPages.put("register", "pages/auth/register");
		//mappedPages.put("clientLogin", "pages/examples/clientLogin");

		mappedPages.put("profile", "pages/examples/profile");
		mappedPages.put("invoice", "pages/examples/invoice");
		/*mappedPages.put("dashboard1", "dashboard/dashboard-1");
		mappedPages.put("dashboard2", "dashboard/dashboard-2");*/
		mappedPages.put("dashboard1", "pages/public/dashboard");
		mappedPages.put("dashboard2", "pages/public/dashboard2");


		mappedPages.put("calendar", "pages/public/calendar");
		//mappedPages.put("/calendar", "dashboard/calendar");
	}

	public static String getView(String uri){
		if (mappedPages.containsKey(uri))
			return mappedPages.get(uri);
		
		return "/";
	}
}
