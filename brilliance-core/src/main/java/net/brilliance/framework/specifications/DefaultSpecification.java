/**
 * 
 */
package net.brilliance.framework.specifications;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.framework.model.SearchParameter;

/**
 * @author ducbq
 *
 */
@Builder
public class DefaultSpecification<UserType, UserRequest> extends BrillianceSpecifications<UserType, UserRequest>{
	public Specification<UserType> buildSpecification(final SearchParameter searchParameter) {
		return super.buildSpecifications(searchParameter);
	}
}
