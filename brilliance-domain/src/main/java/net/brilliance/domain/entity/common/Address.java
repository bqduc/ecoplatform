/**
 * 
 */
package net.brilliance.domain.entity.common;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author ducbq
 *
 */
@Embeddable 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class Address {
	private String primary;
  private String secondary;
  private String street; 
  private String ward; 
  private String district; 
  private String city; 
  private String stateProvince;
  private String postalCode;
  private String country;

	public String getPrimary() {
		return primary;
	}
	public Address setPrimary(String line1) {
		this.primary = line1;
		return this;
	}
	public String getSecondary() {
		return secondary;
	}
	public Address setSecondary(String line2) {
		this.secondary = line2;
		return this;
	}
	public String getStreet() {
		return street;
	}
	public Address setStreet(String street) {
		this.street = street;
		return this;
	}
	public String getWard() {
		return ward;
	}
	public Address setWard(String ward) {
		this.ward = ward;
		return this;
	}
	public String getDistrict() {
		return district;
	}
	public Address setDistrict(String district) {
		this.district = district;
		return this;
	}
	public String getCity() {
		return city;
	}
	public Address setCity(String city) {
		this.city = city;
		return this;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public Address setStateProvince(String state) {
		this.stateProvince = state;
		return this;
	}
	public String getCountry() {
		return country;
	}
	public Address setCountry(String country) {
		this.country = country;
		return this;
	}

	public static Address instance() {
		return new Address();
	}
	
	public static Address instance(String primary, String country) {
		return instance()
				.setPrimary(primary)
				.setCountry(country);
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
