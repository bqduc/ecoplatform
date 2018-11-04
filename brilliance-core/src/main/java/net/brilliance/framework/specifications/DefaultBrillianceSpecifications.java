/**
 * 
 */
package net.brilliance.framework.specifications;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.framework.model.SearchParameter;

/**
 * @author ducbq
 *
 */
@Builder
public class DefaultBrillianceSpecifications<BaseObject, DefaultSearchRequest> extends BrillianceSpecifications<BaseObject, DefaultSearchRequest>{
	@Override
	public Specification<BaseObject> getFilter(DefaultSearchRequest userRequest) {
		return null;
	}

	protected Specification<BaseObject> buildSpecifications(final SearchParameter searchParameter){
		return new Specification<BaseObject>() {
			@Override
			public Predicate toPredicate(Root<BaseObject> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (CommonUtility.isEmpty(searchParameter.getParameterMap())) {
					throw new IllegalStateException("At least one parameter should be provided to construct complex query");
				}

				List<Predicate> predicates = ListUtility.createArrayList();
				if (!searchParameter.getParameterMap().isEmpty()){
					for (String searchParam :searchParameter.getParameterMap().keySet()){
						Object searchValue = searchParameter.getParameterMap().get(searchParam);
						if (CommonUtility.isNotEmpty(searchValue)){
							if (searchValue instanceof String){
								predicates.add(builder.and(builder.like(root.get(searchParam), containsWildcard((String)searchParameter.getParameterMap().get(searchParam)))));
							}else if (searchValue instanceof java.util.Date){
								predicates.add(builder.and(builder.between(root.get(searchParam), (java.util.Date)searchValue, (java.util.Date)searchValue)));
							}
						}
					}
				}
				Predicate[] predicatesArray = new Predicate[predicates.size()];
				return builder.and(predicates.toArray(predicatesArray));
			}
		};
	}
}
