/**
 * 
 */
package net.brilliance.repository.specification.base;

import net.brilliance.common.CommonUtility;

/**
 * @author ducbq
 *
 */
public abstract class BaseSpecifications {
	public final static String suffixFrom = "From";
	public final static String suffixTo = "To";

	public final static String fieldCode = "code";
	public final static String fieldName = "name";
	public final static String fieldLocalName = "localName";
	public final static String fieldPhones = "phones";

	public final static String fieldIssuedDate = "issuedDate";
	public final static String fieldPublishedDate = "publishedDate";

	public final static String fieldIssuedDateFrom = fieldIssuedDate + suffixFrom;
	public final static String fieldIssuedDateTo = fieldIssuedDate + suffixTo;

	public final static String fieldPublishedDateFrom = fieldPublishedDate + suffixFrom;
	public final static String fieldPublishedDateTo = fieldPublishedDate + suffixTo;

	public final static String wildcard = "%";

	protected static String containsWildcard(String searchField) {
		if (CommonUtility.isEmpty(searchField))
			return "";

		return wildcard + searchField + wildcard;
	}

	protected String containsLowerCase(String searchField) {
		return wildcard + searchField.toLowerCase() + wildcard;
	}

}
