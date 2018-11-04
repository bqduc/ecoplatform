/**
 * 
 */
package net.brilliance.repository.specification.system;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.system.DigitalDashlet;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchSpecRequestBase;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class DashletSpecifications extends BrillianceSpecifications<DigitalDashlet, SearchSpecRequestBase>{
	public static Specification<DigitalDashlet> buildSpecification(final SearchParameter searchParameter) {
		return DashletSpecifications
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
