/**
 * 
 */
package net.brilliance.repository.specification.inventory;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.domain.entity.stock.Product;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;

/**
 * @author ducbq
 *
 */
@Builder
public class ProductSpecification extends BrillianceSpecifications<Product, SearchRequest>{
	public static Specification<Product> buildSpecification(final SearchParameter searchParameter) {
		return ProductSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}

  /**
	 * Specification used to construct dynamic query based on JPA Criteria API.
	 * 
	 * @param searchParameter
	 *          Demanded search parameters (must be bigger than 0 to be taken for the query)
	 * @param name
	 *          Product's name (must be not null to be taken for the query)
	 * @return Specification to use with JpaSpecificationExecutor
	 */
	/*@Override
	protected Specification<Product> buildSpecifications(SearchParameter searchParameter) {
		return new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				CommonBeanUtils.getBeanPropertyNames(beanClass)
				List<Predicate> predicates = ListUtility.createArrayList();
				if (CommonUtility.isNotEmpty(searchParameter) && !searchParameter.getParameterMap().isEmpty()){
					predicates.add(builder.and(builder.like(root.get(fieldFirstName), containsWildcard((String)searchParameter.getParameterMap().get(fieldFirstName)))));
					if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldLastName))){
						predicates.add(builder.and(builder.like(root.get(fieldLastName), containsWildcard((String)searchParameter.getParameterMap().get(fieldLastName)))));
					}

					if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldCode))){
						predicates.add(builder.and(builder.like(root.get(fieldCode), containsWildcard((String)searchParameter.getParameterMap().get(fieldCode)))));
					}

					if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldPhones))){
						predicates.add(builder.and(builder.like(root.get(fieldPhones), containsWildcard((String)searchParameter.getParameterMap().get(fieldPhones)))));
					}

					try {
						if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldDateOfBirthFrom)) && CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldDateOfBirthTo))){
							predicates.add(builder.and(builder.between(root.get("dateOfBirth"), 
									DateTimeUtility.getDateInstance((String)searchParameter.getParameterMap().get(fieldDateOfBirthFrom)), 
									DateTimeUtility.getDateInstance((String)searchParameter.getParameterMap().get(fieldDateOfBirthTo)))
								)
							);
						}

						//Issue date
						if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldDateOfIssueFrom)) 
								&& CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldDateOfIssueTo))){
							predicates.add(builder.and(builder.between(root.get("issuedDate"), 
									DateTimeUtility.getDateInstance((String)searchParameter.getParameterMap().get(fieldDateOfIssueFrom)), 
									DateTimeUtility.getDateInstance((String)searchParameter.getParameterMap().get(fieldDateOfIssueTo)))
								)
							);
						}
					} catch (ParseException e) {
						cLog.error("ProductSpecifications::toPredicate", e);
					}
				}
				Predicate[] predicatesArray = new Predicate[predicates.size()];
				return builder.and(predicates.toArray(predicatesArray));
			}
		};
	}*/
}
