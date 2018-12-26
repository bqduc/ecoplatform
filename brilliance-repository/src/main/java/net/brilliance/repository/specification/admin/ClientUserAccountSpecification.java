/**
 * 
 */
package net.brilliance.repository.specification.admin;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.admin.ClientUserAccount;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class ClientUserAccountSpecification extends BrillianceSpecifications<ClientUserAccount, SearchRequest>{
	public static Specification<ClientUserAccount> buildSpecification(final SearchParameter searchParameter) {
		return ClientUserAccountSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
