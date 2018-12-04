/**
 * 
 */
package net.brilliance.common;

import java.math.BigDecimal;

/**
 * @author ducbq
 *
 */
public interface CommonConstants {
	final static int DUMMY_LARGE_COUNT = 5000000;
	final static String TRUE_STRING = Boolean.TRUE.toString();
	final static String PROPERTY_KEY = "id";
	final static String PROPERTY_CODE = "code";
	final static String PROPERTY_NAME = "name";
	final static String PROPERTY_NAME_LOCAL = "nameLocal";

	final static String SEPARATOR = ";";
	final static String SEQUENCE_SEPARATOR = ".";
	final static String ZERO_PATTERN = "000000000000000";

	final static String OPTION_ENABLED = "on";
	final static String OPTION_DISABLED = "off";

	final static String DEFAULT_MODULE_ID = "main";

	final static Object EMPTY_DATA = null;

	final static int VARIANCE_SIZE = 41599; // Equivalent to 40KB

	final static int DECIMAL_DIGITS = 1;
	final static int SUCCESS_STATE_VALUE = 1;
	final static int FAILURE_STATE_VALUE = 0;

	/*final static byte		LENGTH_OBJECT_SERIAL = 15;
	final static byte		LENGTH_OBJECT_CODE = 20;//Including the backup part
	final static byte		LENGTH_CURRENCY_CODE = 5;
	final static byte		LENGTH_ZIP_POSTAL_CODE = 7;
	final static short	LENGTH_OBJECT_NAME = 250;*/

	final static String UNFINISHED_PATTERN = "unfinished";
	final static String REST_API_SEPARATOR = "/";

	final static String STRING_WILDCARD = "%";

	final static String STRING_SPACE = " ";
	final static String STRING_BLANK = "";
	final static String SEPARATOR_PHONE = "-";
	final static String SEPARATOR_ADDRESS = ", ";
	
	final static String SEMICOLON = ";";

	final static String LEFT_BRACKET = "[";
	final static String RIGHT_BRACKET = "]";
	final static String STRING_BLANK_CAPTION = LEFT_BRACKET + RIGHT_BRACKET;

	final static String PERSPECTIVE_DEFAULT_EN = "en";
	final static String PERSPECTIVE_DEFAULT_VI = "vi";
	
	final static int ENTITY_REFERENCE_SIZE = 15;

	final static int NUMBER_OF_IMAGE_COPIES = 256;
	final static String AVATAR_JPG = "avatar.jpg";
	final static int AVATAR_SIZE = 80;
	final static int DEFAULT_IMAGE_SIZEVALUE = 120;
	final static String SLASH = "/";
	final static String DOT = ".";
	final static String JPEG = "jpeg";
	final static String JPG = "JPG";
	final static String UPLOAD_ROOT_COMPONENT_NAME = "uploadRoot";
	final static String UPLOAD_ROOT_PATH_COMPONENT_NAME = "uploadRootPath";
	final static int INITIAL_DELAY = 4000;
	final static int DELAY = 3000;
	final static String DEFAULT_PICTURE = "default/noimage_small200.jpg";
	final static String DEFAULT_ORIGINAL_PICTURE = "default/noimage.jpg";
	final static int DEFAULT_BUFFER_SIZE = 20480;//20KB //8192;
	final static String UPLOAD = "upload";
	final static String FEMALE = "Female";
	final static String MALE = "Male";
	final static String TEMP_DIR = "java.io.tmpdir";
	final static String WEB_INF = "WEB-INF";
	final static String IMAGE_FOLDER = "/Upload";
	final static String PHOTOALBUM_FOLDER = "richfaces_photoalbum";

	//Persistence constants
	static final String BEAN_PROPERTY_OBJECT_ID = "id";
	static final String BEAN_PROPERTY_CODE = "code";
	static final String BEAN_PROPERTY_NAME = "name";
	static final String BEAN_PROPERTY_ACTIVE = "active";
	static final String BEAN_PROPERTY_SYSTEM = "system";
	static final String BEAN_PROPERTY_MODULE = "module";
	static final String BEAN_PROPERTY_TYPE = "type";

	//Service -constants
	final static String USER_EXIST_QUERY = "user-exist";
	final static String USER_LOGIN_QUERY = "user-login";
	final static String LOGIN_PARAMETER = "login";
	final static String PASSWORD_PARAMETER = "password";
	final static String USERNAME_PARAMETER = "username";
	final static String USER_PARAMETER = "user";
	final static String DATE_PARAMETER = "date";
	final static String ALBUM_PARAMETER = "album";
	final static String COMMA = ",";
	final static String PERCENT = "%";
	final static String TAG_SUGGEST_QUERY = "tag-suggest";
	final static String TAG_POPULAR_QUERY = "tag-popular";
	final static String TAG_PARAMETER = "tag";
	final static String TAG_BY_NAME_QUERY = "tag-byName";
	final static String SEARCH_QUERY_SHARED_ADDON = " and sh.shared=true";
	final static String SEARCH_QUERY_MY_ADDON = " and sh.owner.login=:login";
	final static String SEARCH_SHELVES_QUERY = "from Shelf sh where (lower(sh.name) like :name or lower(sh.description) like :name) ";
	final static String SEARCH_METATAG_QUERY = "from MetaTag t where lower(t.tag) like :name";
	final static String SEARCH_USERS_QUERY = "select  u from User u where (lower(u.login) like :name or lower(u.firstName) like :name or lower(u.secondName) like :name) ";
	final static String SEARCH_IMAGE_SHARED_ADDON = " and i.album.shelf.shared=true";
	final static String SEARCH_IMAGE_MY_ADDON = " and i.album.shelf.owner.login=:login";
	final static String SEARCH_IMAGE_QUERY = "from Image i where (lower(i.name) like :name or lower(i.description) like :name or lower(i.cameraModel) like :name) ";
	final static String SHARED_PARAMETER = "shared";
	final static String NAME_PARAMETER = "name";
	final static String SEARCH_ALBUM_SHARED_ADDON = " and a.shelf.shared=true";
	final static String SEARCH_ALBUM_MY_ADDON = " and a.shelf.owner.login=:login";
	final static String SEARCH_ALBUM_QUERY = "from Album a where (lower(a.name) like :name or lower(a.description) like :name)";
	final static String USER_SHELVES_QUERY = "user-shelves";
	final static String SHELF_PARAMETER = "shelf";
	final static String PATH_PARAMETER = "path";
	final static String IMAGE_PATH_EXIST_QUERY = "image-exist";
	final static String IMAGE_IDENTICAL_QUERY = "image-countIdenticalImages";
	final static String SEARCH_NO_OPTIONS_ERROR = "You must select at least one search option";
	final static String TREE_ID = "treeform";
	final static String USER_COMMENTS_QUERY = "user-comments";
	final static String AUTHOR_PARAMETER = "author";
	final static String EMAIL_EXIST_QUERY = "email-exist";
	final static String EMAIL_PARAMETER = "email";
	
	final static String ENTITY_MANAGER_CACHED = "entity.manager";

	final static String CLAUSE_SELECT = " SELECT ";
	final static String CLAUSE_FROM = " FROM ";
	final static String CLAUSE_AND = " AND ";
	final static String CLAUSE_WHERE = " WHERE ";
	final static String CLAUSE_ORDER_BY = " ORDER BY ";

	final static String COMPONENT_ENTITY_MANAGER = "entityManager";
	final static String COMPONENT_ORGANIZATION_MANAGER = "organizationManager";
	final static String CONTEXT_INVENTORY_SERVICE = "contextInventoryService";
	final static String CONTEXT_EXECUTION = "contextExecution";
	final static String CONTEXT_CAST_MARHSALLER = "contextCastMarshaller";
	
	
	static String globalCurrencyCode = "VND";//"USD";

	final static String REMOTE_API_PATTERN = "/rest/";

	final static String REST_API_REQUEST_URL = "apiUrl";
	final static String REST_API_REQUEST_METHOD = "method";
	final static String REST_API_REQUEST_CONTENT_TYPE = "contentType";

	final static String AUTHENTICATED_ATTRIBUTE = "authInfo";
	
	final static int REST_API_STATUS_OK = 200;
	final static int REST_API_STATUS_FAILED = 201;
	final static int REST_API_STATUS_INVALID_TOKEN = 498;
	final static int REST_API_TOKEN_LENGTH = 12;

	final static String REST_API_EXECUTE_SUCCESS = "SUCCESS";
	final static String REST_API_EXECUTE_FAILURE = "FAILURE";
	
	final static BigDecimal HUNDRED = BigDecimal.valueOf(100);
	
	
	
	//TODO: Have to refactor
	static final String SPRING_PROFILE_DEVELOPMENT = "dev";
	static final String SPRING_PROFILE_PRODUCTION = "prod";

	static final String REST_API = "/rapi/";

	static final String LIST_CATEGORY = "categoryList";
	static final String LIST_DEPARTMENT = "departmentList";

	static final String FETCHED_MASTER_OBJECT = "fetchedMasterObject";

	static final String FETCHED_OBJECT = "fetchedObject";
	static final String FETCHED_OBJECTS = "fetchedObjects";

	static final String REALM = "MY_TEST_REALM";

	static final String REQUEST_CLERK = "requestClerk";
	static final String REQUEST_CLIENT = "requestClient";

	static final Integer DEFAULT_PAGE_BEGIN = 0;
	static final Integer DEFAULT_PAGE_SIZE = 20;

	static final String HEADER_AUTHORIZED = "Authorization";
	

	//Controllers 
	static final String CONTROLLER_MODULE = "module";
	static final String CONTROLLER_AUTHORITY = "authority";

	static final String CACHED_PAGE_MODEL = "cachedModel";
	
	final static int PAGE_SIZE_INFINITELY = -1;
	
	final static int MAX_DUMMY_OBJECTS = 297;

	
	final static String PARAM_SEARCH_PATTERN = "searchPattern";
	final static String PARAM_KEYWORD = "keyword";
	final static String PARAM_PAGE = "page";
	final static String PARAM_PAGE_SIZE = "size";

	final static String PARAM_PAGEABLE = "pageable";

	final static String CONFIGURED_CONTROLLER = "configuredController";

	final static String AUTHORITY_LIST = "authorityList";
}
