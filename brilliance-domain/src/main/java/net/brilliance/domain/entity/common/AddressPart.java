/**
 * 
 */
package net.brilliance.domain.entity.common;

import javax.persistence.Embeddable;

import lombok.Builder;

/**
 * @author ducbq
 *
 */
@Builder
@Embeddable 
public class AddressPart {
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
	public AddressPart setPrimary(String line1) {
		this.primary = line1;
		return this;
	}
	public String getSecondary() {
		return secondary;
	}
	public AddressPart setSecondary(String line2) {
		this.secondary = line2;
		return this;
	}
	public String getStreet() {
		return street;
	}
	public AddressPart setStreet(String street) {
		this.street = street;
		return this;
	}
	public String getWard() {
		return ward;
	}
	public AddressPart setWard(String ward) {
		this.ward = ward;
		return this;
	}
	public String getDistrict() {
		return district;
	}
	public AddressPart setDistrict(String district) {
		this.district = district;
		return this;
	}
	public String getCity() {
		return city;
	}
	public AddressPart setCity(String city) {
		this.city = city;
		return this;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public AddressPart setStateProvince(String state) {
		this.stateProvince = state;
		return this;
	}
	public String getCountry() {
		return country;
	}
	public AddressPart setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
