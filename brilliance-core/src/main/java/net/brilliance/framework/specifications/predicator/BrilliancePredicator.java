/**
 * 
 */
package net.brilliance.framework.specifications.predicator;

import org.springframework.data.jpa.domain.Specification;

import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.specifications.predicator.base.RepositoryPredicator;

/**
 * @author ducbq
 *
 */
public abstract class BrilliancePredicator<T> extends RepositoryPredicator<T>{
	public Specification<T> buildSpecification(final SearchParameter searchParameter){
		return predicateSpecification(searchParameter);
	}
}
