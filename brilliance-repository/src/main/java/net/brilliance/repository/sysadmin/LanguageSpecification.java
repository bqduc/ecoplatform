/**
 * 
 *//*
package net.brilliance.repository.sysadmin;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import net.brilliance.domain.entity.config.Language;
import net.brilliance.framework.model.SearchCriterion;

*//**
 * @author ducbq
 *
 *//*
public class LanguageSpecification implements Specification<Language> {
	private SearchCriterion criteria;

	@Override
	public Predicate toPredicate(Root<Language> languageRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (criteria.getOperand().equalsIgnoreCase(">")) {
			return builder.greaterThanOrEqualTo(languageRoot.<String> get(criteria.getKey()), criteria.getValue().toString());
		} else if (criteria.getOperation().equalsIgnoreCase("<")) {
			return builder.lessThanOrEqualTo(languageRoot.<String> get(criteria.getKey()), criteria.getValue().toString());
		} else if (criteria.getOperation().equalsIgnoreCase(":")) {
			if (languageRoot.get(criteria.getKey()).getJavaType() == String.class) {
				return builder.like(languageRoot.<String> get(criteria.getKey()), "%" + criteria.getValue() + "%");
			} else {
				return builder.equal(languageRoot.get(criteria.getKey()), criteria.getValue());
			}
		}
		return null;
	}

}
*/