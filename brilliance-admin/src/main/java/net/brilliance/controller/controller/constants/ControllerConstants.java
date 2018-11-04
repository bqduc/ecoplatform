/**
 * 
 */
package net.brilliance.controller.controller.constants;

import net.brilliance.common.CommonConstants;

/**
 * @author ducbq
 *
 */
public final class ControllerConstants implements CommonConstants{
	public static final String DEAULT_PAGE_CONTEXT_PREFIX = "/";

	public static final String PARAM_CREATE_OTHER = "continuedCreateOther";

	public static final String REQUEST_CREATE = "/create";
	public static final String REQUEST_UPDATE = "/update";
	public static final String REQUEST_SEARCH = "/search";
	public static final String REQUEST_IMPORT = "/import";
	
	public static final String REDIRECT_PREFIX = "redirect:/";

	public static final String AUTHORITY_MAP = "authorityMap";
	public static final String CONTEXT_WEB_PAGES = "pages/";

	//Agricultural service requests
	public static final String REQUEST_MAPPING_AQUAFEEDS = "/aquafeed";
	public static final String REQUEST_MAPPING_LIVESTOCK_FEED = "/livestockFeed";
	public static final String REQUEST_MAPPING_POND = "/pond";
	public static final String REQUEST_REST_API_POND = REST_API + "pond";

	//System request mappings
	public static final String REQUEST_URI_SYSTEM = "/system";
	public static final String REQUEST_URI_DATA_ADMIN = "/dataAdmin";

	public static final String REQUEST_MAPPING_MEASURE_UNIT = "/measureUnit";
	public static final String REQUEST_MAPPING_CATALOG_SUBTYPE = "/catalogSubtype";
	public static final String REQUEST_MAPPING_CATALOG = "/catalog";
	public static final String REQUEST_MAPPING_DEPARTMENT = "/department";
	public static final String REQUEST_MAPPING_CATEGORY = "/category";
	public static final String REQUEST_MAPPING_PROJECT = "/project";

	public static final String REQUEST_MAPPING_DASHBOARD = "dashboard";
	public static final String REQUEST_MAPPING_USER = "/user";
	public static final String REQUEST_MAPPING_ROLE = "/role";
	public static final String REQUEST_MAPPING_CLIENT = "/client";

	public static final String REQUEST_CONTEXT_GENERAL = "general";

	public static final String REQUEST_MAPPING_STORE = "store";
	public static final String REST_API_STORE = REST_API + "store";

	public static final String REQUEST_URI_FORUM = "forum";
	public static final String REQUEST_URI_TOPIC = "topic";
	public static final String REQUEST_URI_THREAD = "thread";
	public static final String REQUEST_URI_POST = "post";

	public static final String REQUEST_URI_EMPLOYEE = "/employee";
	public static final String REQUEST_URI_CONTACT = "/contact";

	public static final String REQUEST_URI_PRODUCT = "/product";
	public static final String REQUEST_URI_OFFICE = "/office";
	public static final String REQUEST_URI_BUSINESS_UNIT = "/businessUnit";

	public static final String REQUEST_URI_BACKGROUND_JOB = "/backjob";
	public static final String REQUEST_URI_JOB_CATEGORY = "/jobCategory";

	public static final String REQUEST_ADMIN_DASHBOARD = "adminDashboard";
	public static final String REQUEST_ADMIN_DASHLET = "/adminDashlet";

	public static final String REQ_MAPPING_ENTERPRISE = "/enterprise";
}
