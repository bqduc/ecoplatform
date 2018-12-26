/**
 * 
 */
package net.brilliance.repository.specification.model;

import net.brilliance.framework.model.specifications.SearchRequest;

/**
 * @author ducbq
 *
 */
public class ContactListRequest extends SearchRequest{
	public final static String fieldCode = "code";
	public final static String fieldFirstName = "firstName";
	public final static String fieldLastName = "lastName";
	public final static String fieldEmail = "email";
	public final static String fieldPhones = "phones";

	private String code;
	private String firstName;
	private String lastName;
	private String email;
	private String phones;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	
}
