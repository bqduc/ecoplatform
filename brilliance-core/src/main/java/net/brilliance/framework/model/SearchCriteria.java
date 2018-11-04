/**
 * 
 */
package net.brilliance.framework.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.common.ListUtility;

/**
 * @author ducbq
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
	public List<SearchCriterion> getSearchParameters() {
		return searchParameters;
	}

	public void setSearchParameters(List<SearchCriterion> searchParameters) {
		this.searchParameters = searchParameters;
	}

	@Builder.Default
	private List<SearchCriterion> searchParameters = ListUtility.createArrayList();
}
