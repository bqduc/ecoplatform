/**
 * 
 */
package net.brilliance.framework.repository;

import java.util.Date;

import javax.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author ducbq
 *
 */
public class SpecificationFactory {
	public static Specification containsLike(String attribute, String value) {
		return (root, query, cb) -> cb.like(root.get(attribute), "%" + value + "%");
	}

	public static Specification isBetween(String attribute, int min, int max) {
		return (root, query, cb) -> cb.between(root.get(attribute), min, max);
	}

	public static Specification isBetween(String attribute, Date minDate, Date maxDate) {
		return (root, query, cb) -> cb.between(root.get(attribute), minDate, maxDate);
	}

	public static Specification isBetween(String attribute, double min, double max) {
		return (root, query, cb) -> cb.between(root.get(attribute), min, max);
	}

	public static <T extends Enum<T>> Specification enumMatcher(String attribute, T queriedValue) {
		return (root, query, cb) -> {
			Path<T> actualValue = root.get(attribute);

			if (queriedValue == null) {
				return null;
			}

			return cb.equal(actualValue, queriedValue);
		};
	}
}
