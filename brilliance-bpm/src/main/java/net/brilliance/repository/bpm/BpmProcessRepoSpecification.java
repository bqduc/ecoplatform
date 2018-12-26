/**
 * 
 */
package net.brilliance.repository.bpm;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.bpm.BpmProcess;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class BpmProcessRepoSpecification extends BrillianceSpecifications<BpmProcess, SearchRequest>{
	public static Specification<BpmProcess> buildSpecification(final SearchParameter searchParameter) {
		return BpmProcessRepoSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
