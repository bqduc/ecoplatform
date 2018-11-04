package net.brilliance.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

public class UrlUtil {
	public static String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {

		String enc = httpServletRequest.getCharacterEncoding();

		if (enc == null)
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;

		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}

	public static String addParamToURL(String url, String param, String value, boolean replace) {
		if (replace == true)
			url = removeParamFromURL(url, param);
		return url + ((url.indexOf("?") == -1) ? "?" : "&") + param + "=" + value;
	}

	public static String removeParamFromURL(String url, String param) {
		String sep = "&";
		int startIndex = url.indexOf(sep + param + "=");
		boolean firstParam = false;
		if (startIndex == -1) {
			startIndex = url.indexOf("?" + param + "=");
			if (startIndex != -1) {
				startIndex++;
				firstParam = true;
			}
		}

		if (startIndex != -1) {
			String startUrl = url.substring(0, startIndex);
			String endUrl = "";
			int endIndex = url.indexOf(sep, startIndex + 1);
			if (firstParam && endIndex != 1) {
				// remove separator from remaining url
				endUrl = url.substring(endIndex + 1);
			}
			return startUrl + endUrl;
		}

		return url;
	}
}
