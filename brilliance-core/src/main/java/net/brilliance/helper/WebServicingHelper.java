/**
 * 
 */
package net.brilliance.helper;

import java.util.HashMap;
import java.util.Map;

import net.brilliance.common.CommonConstants;

/**
 * @author ducbq
 *
 */
public class WebServicingHelper {
	public static Map<String, Object> createSearchParameters(String keyword, Short page, Short pageSize) {
		Map<String, Object> defaultSearchParameters = new HashMap<>();
		defaultSearchParameters.put(CommonConstants.PARAM_KEYWORD, keyword);
		defaultSearchParameters.put(CommonConstants.PARAM_PAGE, (null != page)?page:1);
		defaultSearchParameters.put(CommonConstants.PARAM_PAGE_SIZE, (null != pageSize)?pageSize:CommonConstants.DEFAULT_PAGE_SIZE);
		return defaultSearchParameters;
	}

}
