/**
 * 
 */
package net.brilliance.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchSpecRequestBase;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class EnterpriseSpecification extends BrillianceSpecifications<Enterprise, SearchSpecRequestBase>{
	public static Specification<Enterprise> buildSpecification(final SearchParameter searchParameter) {
		return EnterpriseSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
