package net.brilliance.domain.entity.admin;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.common.Address;
import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * An office or POS.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "enterprise_unit")
@Data
@EqualsAndHashCode(callSuper=false)
public class EnterpriseUnit extends BizObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6746233516438191628L;

	@Version
	@Column(name = "version")
	private Integer version;

	@Size(min = 5, max=GlobalConstants.SIZE_SERIAL)
	@Column(name = "code")
	private String code;

	@Size(min = 5, max = 200)
	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "publisher_id")
	private UserAccount publisher;

	@ManyToOne
	@JoinColumn(name = "issue_user_id")
	private UserAccount issuedBy;

	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private EnterpriseUnit parent;

	@Column(name = "date_of_publication")
	private Date dateOfPublication;

	@Column(name = "date_of_issue")
	private Date dateOfIssue;

	@Size(min = 5, max = 35)
	@Column(name = "phones")
	private String phones;

  @Embedded
	@AttributeOverrides({
	  @AttributeOverride(name="address", column=@Column(name="address_primary")),
		@AttributeOverride(name="city", column=@Column(name="address_city")),
		@AttributeOverride(name="state", column=@Column(name="address_state")),
		@AttributeOverride(name="postalCode", column=@Column(name="address_postal_code")),
		@AttributeOverride(name="country", column=@Column(name="address_country"))
	})
	private Address address;
	
	@JsonIgnore
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "photo", columnDefinition="TEXT")
	private String photo;

	@JsonIgnore
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "photo_2", columnDefinition="TEXT")
	private String photo2;

	@JsonIgnore
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "photo_3", columnDefinition="TEXT")
	private String photo3;

	@ManyToOne
	@JoinColumn(name = "spoc_user_id")
	private UserAccount spoc; //Single Point Of Contact

	@ManyToOne
	@JoinColumn(name = "managing_user_id")
	private UserAccount manager;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

	public String getAddress() {
		return this.address.getAddress();
	}

	public void setAddress(String primary) {
		this.address.setAddress(primary);
	}

	public String getCity() {
		return this.address.getCity();
	}

	public void setCity(String city) {
		this.address.setCity(city);
	}

	public String getState() {
		return this.address.getState();
	}

	public void setState(String state) {
		this.address.setState(state);
	}

	public String getPostalCode() {
		return this.address.getPostalCode();
	}

	public void setPostalCode(String postalCode) {
		this.address.setPostalCode(postalCode);
	}

	public String getCountry() {
		return this.address.getCountry();
	}

	public void setCountry(String country) {
		this.address.setCountry(country);
	}
}
