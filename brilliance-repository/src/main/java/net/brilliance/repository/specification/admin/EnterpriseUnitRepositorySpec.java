/**
 * 
 */
package net.brilliance.repository.specification.admin;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.admin.EnterpriseUnit;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchSpecRequestBase;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class EnterpriseUnitRepositorySpec extends BrillianceSpecifications<EnterpriseUnit, SearchSpecRequestBase> {
	public static Specification<EnterpriseUnit> buildSpecification(final SearchParameter searchParameter) {
		return EnterpriseUnitRepositorySpec
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
