/**
 * 
 */
package net.brilliance.repository.specification.system;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.system.DigitalDashboard;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchSpecRequestBase;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class DashboardSpecifications extends BrillianceSpecifications<DigitalDashboard, SearchSpecRequestBase>{
	public static Specification<DigitalDashboard> buildSpecification(final SearchParameter searchParameter) {
		return DashboardSpecifications
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
