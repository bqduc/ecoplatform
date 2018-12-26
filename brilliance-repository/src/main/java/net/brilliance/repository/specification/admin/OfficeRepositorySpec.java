/**
 * 
 */
package net.brilliance.repository.specification.admin;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.admin.Office;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class OfficeRepositorySpec extends BrillianceSpecifications<Office, SearchRequest> {
	public static Specification<Office> buildSpecification(final SearchParameter searchParameter) {
		return OfficeRepositorySpec
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
