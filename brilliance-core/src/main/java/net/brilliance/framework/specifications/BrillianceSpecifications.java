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

import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.framework.model.SearchCriteria;
import net.brilliance.framework.model.SearchCriterion;
import net.brilliance.framework.model.SearchOperand;
import net.brilliance.framework.model.SearchParameter;

/**
 * @author ducbq
 *
 */
public abstract class BrillianceSpecifications<CustomUserType, CustomUserRequest> extends BaseSpecification<CustomUserType, CustomUserRequest>{
	@Override
	public Specification<CustomUserType> getFilter(CustomUserRequest userRequest) {
		return null;
	}

	protected Specification<CustomUserType> buildSpecifications(final SearchParameter searchParameter){
		return new Specification<CustomUserType>() {
			@Override
			public Predicate toPredicate(Root<CustomUserType> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				/*if (CommonUtility.isEmpty(searchParameter.getParameterMap())) {
					throw new IllegalStateException("At least one parameter should be provided to construct complex query");
				}*/

				Predicate[] predicatesArray = null;
				Object searchValue = null;
				List<Predicate> predicates = ListUtility.createArrayList();
				//predicates = buildPredicates(searchParameter, root, builder);
				if (CommonUtility.isNotEmpty(searchParameter.getParameterMap())){
					for (String searchParam :searchParameter.getParameterMap().keySet()){
						searchValue = searchParameter.getParameterMap().get(searchParam);
						if (CommonUtility.isNotEmpty(searchValue)){
							if (searchValue instanceof String){
								predicates.add(builder.and(builder.like(root.get(searchParam), containsWildcard((String)searchParameter.getParameterMap().get(searchParam)))));
							}else if (searchValue instanceof java.util.Date){
								predicates.add(builder.and(builder.between(root.get(searchParam), (java.util.Date)searchValue, (java.util.Date)searchValue)));
							}
						}
					}
				}
				predicatesArray = new Predicate[predicates.size()];
				return builder.and(predicates.toArray(predicatesArray));
			}
		};
	}

	private List<Predicate> buildPredicates(final SearchCriteria searchCriteria, Root<CustomUserType> root, CriteriaBuilder builder){
		List<Predicate> predicates = ListUtility.createArrayList();
		for(SearchCriterion searchCriterion :searchCriteria.getSearchParameters()){
			if (SearchOperand.equal.equals(searchCriterion.getOperand())){
				
			} else if (SearchOperand.lesserOrEqual.equals(searchCriterion.getOperand())){
				
			} else if (SearchOperand.greaterOrEquals.equals(searchCriterion.getOperand())){
				
			}
		}
		return predicates;
	}

	public Specification<CustomUserType> buildRepoSpecification(final SearchParameter searchParameter) {
		return this.buildSpecifications(searchParameter);
	}
}
