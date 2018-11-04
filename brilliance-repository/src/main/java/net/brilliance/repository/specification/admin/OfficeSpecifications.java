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
import net.brilliance.domain.entity.admin.Office;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.repository.specification.base.BaseSpecifications;

/**
 * @author ducbq
 *
 */
public class OfficeSpecifications extends BaseSpecifications {
	protected final static Logger logger = LoggerFactory.getLogger(OfficeSpecifications.class);

	private final static String fieldFirstName = "firstName";
	private final static String fieldLastName = "lastName";
	private final static String fieldDateOfBirthFrom = "dateOfBirthFrom";
	private final static String fieldDateOfBirthTo = "dateOfBirthTo";

  /**
	 * Specification used to construct dynamic query based on JPA Criteria API.
	 * 
	 * @param searchParameter
	 *          Demanded search parameters (must be bigger than 0 to be taken for the query)
	 * @param name
	 *          Office's name (must be not null to be taken for the query)
	 * @return Specification to use with JpaSpecificationExecutor
	 */
	public static Specification<Office> buildSpecification(final SearchParameter searchParameter) {
		return new Specification<Office>() {
			@Override
			public Predicate toPredicate(Root<Office> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (CommonUtility.isEmpty(searchParameter.getParameterMap())) {
					throw new IllegalStateException("At least one parameter should be provided to construct complex query");
				}

				List<Predicate> predicates = ListUtility.createArrayList();
				if (!searchParameter.getParameterMap().isEmpty()){
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
						if (CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldIssuedDateFrom)) 
								&& CommonUtility.isNotEmpty(searchParameter.getParameterMap().get(fieldIssuedDateTo))){
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
