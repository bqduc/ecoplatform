/**
 * 
 */
package net.brilliance.repository.specification.general;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.framework.specifications.BrillianceSpecifications;
import net.brilliance.repository.specification.model.ContactListRequest;

/**
 * @author ducbq
 *
 */
@Component
public class ContactListSpecification extends BrillianceSpecifications<Contact, ContactListRequest> {
	private Specification<Contact> firstNameContains(String firstName) {
		return userAttributeContains(ContactListRequest.fieldFirstName, firstName);
	}

	@Override
	public Specification<Contact> getFilter(ContactListRequest userRequest) {
		return (root, query, cb) -> {
			query.distinct(true); // Important because of the join in the addressAttribute specifications
			return Specifications.where(
					Specifications.where(firstNameContains(userRequest.getFirstName())).and(lastNameContains(userRequest.getLastName()))
					)
					.toPredicate(root, query, cb);
		};
	}

	private Specification<Contact> lastNameContains(String lastName) {
		return userAttributeContains(ContactListRequest.fieldLastName, lastName);
	}

	private Specification<Contact> userAttributeContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}

			return cb.like(cb.lower(root.get(attribute)), containsLowerCase(value));
		};
	}
}
