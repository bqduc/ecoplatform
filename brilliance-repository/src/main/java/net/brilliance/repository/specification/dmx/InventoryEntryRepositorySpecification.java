/**
 * 
 */
package net.brilliance.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.dmx.Inventory;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class InventoryEntryRepositorySpecification extends BrillianceSpecifications<Inventory, SearchRequest>{
	public static Specification<Inventory> buildSpecification(final SearchParameter searchParameter) {
		return InventoryEntryRepositorySpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
