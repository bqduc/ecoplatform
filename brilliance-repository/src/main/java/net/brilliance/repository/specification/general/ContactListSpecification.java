/**
 * 
 */
package net.brilliance.repository.specification.general;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import net.brilliance.domain.entity.common.AddressPart;
import net.brilliance.domain.entity.contact.ContactClass;
import net.brilliance.framework.specifications.BrillianceSpecifications;
import net.brilliance.repository.specification.model.ContactListRequest;

/**
 * @author ducbq
 *
 */
@Component
public class ContactListSpecification extends BrillianceSpecifications<ContactClass, ContactListRequest> {
	private Specification<ContactClass> firstNameContains(String firstName) {
		return userAttributeContains(ContactListRequest.fieldFirstName, firstName);
	}

	@Override
	public Specification<ContactClass> getFilter(ContactListRequest userRequest) {
		return (root, query, cb) -> {
			query.distinct(true); // Important because of the join in the addressAttribute specifications
			return Specifications.where(
					Specifications.where(firstNameContains(userRequest.getFirstName())).and(lastNameContains(userRequest.getLastName()))
					)
					.toPredicate(root, query, cb);
		};
	}

	private Specification<ContactClass> lastNameContains(String lastName) {
		return userAttributeContains(ContactListRequest.fieldLastName, lastName);
	}

	private Specification<ContactClass> emailContains(String email) {
		return userAttributeContains(ContactListRequest.fieldEmail, email);
	}

	private Specification<ContactClass> userAttributeContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}

			return cb.like(cb.lower(root.get(attribute)), containsLowerCase(value));
		};
	}

	private Specification<ContactClass> cityContains(String city) {
		return addressAttributeContains("city", city);
	}

	private Specification<ContactClass> streetContains(String street) {
		return addressAttributeContains("street", street);
	}

	private Specification<ContactClass> addressAttributeContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}

			ListJoin<ContactClass, AddressPart> addresses = root.joinList("addresses", JoinType.INNER);

			return cb.like(cb.lower(addresses.get(attribute)), containsLowerCase(value));
		};
	}

}
