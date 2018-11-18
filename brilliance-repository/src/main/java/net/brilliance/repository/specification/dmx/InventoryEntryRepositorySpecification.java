/**
 * 
 */
package net.brilliance.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.dmx.InventoryEntry;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchSpecRequestBase;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class InventoryEntryRepositorySpecification extends BrillianceSpecifications<InventoryEntry, SearchSpecRequestBase>{
	public static Specification<InventoryEntry> buildSpecification(final SearchParameter searchParameter) {
		return InventoryEntryRepositorySpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
