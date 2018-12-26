/**
 * 
 */
package net.brilliance.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.general.Project;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class ProjectRepositorySpec extends BrillianceSpecifications<Project, SearchRequest>{
	public static Specification<Project> buildSpecification(final SearchParameter searchParameter) {
		return ProjectRepositorySpec
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
