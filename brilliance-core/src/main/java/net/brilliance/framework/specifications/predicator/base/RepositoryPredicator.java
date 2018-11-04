/**
 * 
 */
package net.brilliance.framework.specifications.predicator.base;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.framework.model.SearchParameter;

/**
 * @author ducbq
 *
 */
public abstract class RepositoryPredicator<T> {
  protected String containsLowerCase(String searchField) {
  	return new StringBuilder()
  	.append(CommonUtility.STRING_WILDCARD)
  	.append(CommonUtility.getApplicableString(searchField))
  	.append(CommonUtility.STRING_WILDCARD)
  	.toString();
  }

	protected String containsWildcard(String searchField) {
		if (CommonUtility.isEmpty(searchField))
			return CommonUtility.STRING_BLANK;

		return CommonUtility.STRING_WILDCARD + searchField + CommonUtility.STRING_WILDCARD;
  }

	protected abstract Specification<T> buildSpecification(final SearchParameter searchParameter);

	protected Specification<T> predicateSpecification(final SearchParameter searchParameter){
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Object searchValue = null;
				List<Predicate> predicates = ListUtility.createArrayList();
				if (CommonUtility.isNotEmpty(searchParameter.getParameterMap())){
					for (String searchParam :searchParameter.getParameterMap().keySet()){
						searchValue = searchParameter.getParameterMap().get(searchParam);
						if (null != searchValue){
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
