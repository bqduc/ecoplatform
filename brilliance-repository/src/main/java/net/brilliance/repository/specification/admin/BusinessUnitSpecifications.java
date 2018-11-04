/**
 * 
 */
package net.brilliance.repository.specification.admin;

import java.text.ParseException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.DateTimeUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.domain.entity.admin.BusinessUnit;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.repository.specification.base.BaseSpecifications;

/**
 * @author ducbq
 *
 */
public class BusinessUnitSpecifications extends BaseSpecifications{
	protected final static Logger logger = LoggerFactory.getLogger(BusinessUnitSpecifications.class);

  /**
	 * Specification used to construct dynamic query based on JPA Criteria API.
	 * 
	 * @param searchParameter
	 *          Demanded search parameters (must be bigger than 0 to be taken for the query)
	 * @param name
	 *          BusinessUnit's name (must be not null to be taken for the query)
	 * @return Specification to use with JpaSpecificationExecutor
	 */
	public static Specification<BusinessUnit> buildSpecification(final SearchParameter searchParameter) {
		return new Specification<BusinessUnit>() {
			@Override
			public Predicate toPredicate(Root<BusinessUnit> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (CommonUtility.isEmpty(searchParameter.getParameterMap())) {
					throw new IllegalStateException("At least one parameter should be provided to construct complex query");
				}

				List<Predicate> predicates = ListUtility.createArrayList();
				if (!searchParameter.getParameterMap().isEmpty()){
					predicates.add(builder.and(builder.like(root.get(fieldName), containsWildcard((String)searchParameter.getParameterMap().get(fieldName)))));
					if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldLocalName))){
						predicates.add(builder.and(builder.like(root.get(fieldLocalName), containsWildcard((String)searchParameter.getParameterMap().get(fieldLocalName)))));
					}

					if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldCode))){
						predicates.add(builder.and(builder.like(root.get(fieldCode), containsWildcard((String)searchParameter.getParameterMap().get(fieldCode)))));
					}

					if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldPhones))){
						predicates.add(builder.and(builder.like(root.get(fieldPhones), containsWildcard((String)searchParameter.getParameterMap().get(fieldPhones)))));
					}

					try {
						if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldPublishedDateFrom)) && 
								CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldPublishedDateTo))){
							predicates.add(builder.and(builder.between(root.get(fieldPublishedDate), 
									DateTimeUtility.getDateInstance((String)searchParameter.getParameterMap().get(fieldPublishedDateFrom)), 
									DateTimeUtility.getDateInstance((String)searchParameter.getParameterMap().get(fieldPublishedDateTo)))
								)
							);
						}

						//Issue date
						if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldIssuedDateFrom)) && 
								CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldIssuedDateTo))){
							predicates.add(builder.and(builder.between(root.get(fieldIssuedDate), 
									DateTimeUtility.getDateInstance((String)searchParameter.getParameterMap().get(fieldIssuedDateFrom)), 
									DateTimeUtility.getDateInstance((String)searchParameter.getParameterMap().get(fieldIssuedDateTo)))
								)
							);
						}
					} catch (ParseException e) {
						logger.error("OfficeSpecifications::toPredicate", e);
					}
				}
				Predicate[] predicatesArray = new Predicate[predicates.size()];
				return builder.and(predicates.toArray(predicatesArray));
			}
		};
	}
}
