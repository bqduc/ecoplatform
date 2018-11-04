/**
 * 
 */
package net.brilliance.domain.entity.dmx;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.contact.ContactProfile;
import net.brilliance.framework.entity.BaseObject;

/**
 * @author ducbq
 *
 */
@Builder
@Entity
@Table(name = "dmx_enterprise")
@EqualsAndHashCode(callSuper = true)
public class Enterprise extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1273396219086361842L;

	@Column(name = "code", nullable=false, unique=true, length=15)
  private String code;

	@Column(name = "name", nullable=false, length=200)
  private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Enterprise parent;

	@ManyToOne
	@JoinColumn(name = "coordinator_id")
	private ContactProfile coordinator;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "issued_date")
	private Date issuedDate;

	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	//@formatter:off
	@JoinTable(
			name = "dmx_enterprise_policy", 
			inverseJoinColumns = {@JoinColumn(name = "policy_id")},
			joinColumns = {@JoinColumn(name = "enterprise_id")}
	)
	//@formatter:on
	private Set<Policy> activities;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Enterprise getParent() {
		return parent;
	}

	public void setParent(Enterprise parent) {
		this.parent = parent;
	}

	public ContactProfile getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(ContactProfile coordinator) {
		this.coordinator = coordinator;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

}
