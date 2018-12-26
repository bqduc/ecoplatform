/**
 * 
 */
package net.brilliance.repository.bpm;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.bpm.BpmActivity;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class BpmActivityRepoSpecification extends BrillianceSpecifications<BpmActivity, SearchRequest>{
	public static Specification<BpmActivity> buildSpecification(final SearchParameter searchParameter) {
		return BpmActivityRepoSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
