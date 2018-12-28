package net.brilliance.domain.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.contact.ContactProc;

/**
 * Data Transfer Object for Contact. It is bound to the UI for validation
 * 
 * @author ducbq
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ContactDTO extends BaseDto {
	private String login;

	private String password;

	private String firstName;

	private String lastName;

	private String email;

	private String phones;

	private String address;

	private Boolean activated = Boolean.FALSE;

	public ContactProc createContact(PasswordEncoder encoder) {
		ContactProc retObject = new ContactProc();
		/*retObject.setLogin(login);
		retObject.setPassword(encoder.encode(password));*/
		retObject.setFullName(firstName);
		retObject.setEmail(email);
		retObject.setActivated(activated);

		return retObject;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
