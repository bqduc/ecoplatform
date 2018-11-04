/**
 * 
 */
package net.brilliance.framework.specifications;

import javax.inject.Inject;

import org.springframework.data.jpa.domain.Specification;

import net.brilliance.common.CommonUtility;
import net.brilliance.framework.logging.CommonLoggingService;
import net.brilliance.framework.model.SearchParameter;

/**
 * @author ducbq
 *
 */
public abstract class BaseSpecification<UserType, UserRequest> {
  public abstract Specification<UserType> getFilter(UserRequest request);

  @Inject
  protected CommonLoggingService cLog;

  protected String containsLowerCase(String searchField) {
  	return new StringBuilder()
  	.append(CommonUtility.STRING_WILDCARD)
  	.append(CommonUtility.getApplicableString(searchField))
  	.append(CommonUtility.STRING_WILDCARD)
  	.toString();
  }

	protected String containsWildcard(String searchField) {
		if (CommonUtility.isEmpty(searchField))
			return CommonUtility.STRING_BLANK;

		return CommonUtility.STRING_WILDCARD + searchField + CommonUtility.STRING_WILDCARD;
  }

	protected abstract Specification<UserType> buildSpecifications(final SearchParameter searchParameter);
}