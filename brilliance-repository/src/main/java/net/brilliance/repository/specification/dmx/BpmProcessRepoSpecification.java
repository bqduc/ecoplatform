/**
 * 
 */
package net.brilliance.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.config.Configuration;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchSpecRequestBase;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class BpmProcessRepoSpecification extends BrillianceSpecifications<Configuration, SearchSpecRequestBase>{
	public static Specification<Configuration> buildSpecification(final SearchParameter searchParameter) {
		return BpmProcessRepoSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
