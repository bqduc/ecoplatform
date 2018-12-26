/**
 * 
 */
package net.brilliance.repository.specification.system;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.system.DigitalDashboard;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class DashboardSpecifications extends BrillianceSpecifications<DigitalDashboard, SearchRequest>{
	public static Specification<DigitalDashboard> buildSpecification(final SearchParameter searchParameter) {
		return DashboardSpecifications
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
