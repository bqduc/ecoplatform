/**
 * 
 */
package net.brilliance.repository.specification.system;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.system.SystemSequence;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class SystemSequenceSpecifications extends BrillianceSpecifications<SystemSequence, SearchRequest>{
	public static Specification<SystemSequence> buildSpecification(final SearchParameter searchParameter) {
		return SystemSequenceSpecifications
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
