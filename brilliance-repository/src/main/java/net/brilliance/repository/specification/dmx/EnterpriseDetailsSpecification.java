/**
 * 
 */
package net.brilliance.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.dmx.EnterpriseDetail;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class EnterpriseDetailsSpecification extends BrillianceSpecifications<EnterpriseDetail, SearchRequest>{
	public static Specification<EnterpriseDetail> buildSpecification(final SearchParameter searchParameter) {
		return EnterpriseDetailsSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
