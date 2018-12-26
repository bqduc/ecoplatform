/**
 * 
 */
package net.brilliance.repository.specification.admin;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.admin.BusinessUnit;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class BusinessUnitRepositorySpec extends /*BaseSpecifications*/BrillianceSpecifications<BusinessUnit, SearchRequest>{
	public static Specification<BusinessUnit> buildSpecification(final SearchParameter searchParameter) {
		return BusinessUnitRepositorySpec
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
