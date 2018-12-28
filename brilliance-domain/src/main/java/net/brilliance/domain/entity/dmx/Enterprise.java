/**
 * 
 */
package net.brilliance.domain.entity.dmx;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.contact.ContactProc;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dmx_enterprise")
@EqualsAndHashCode(callSuper = true)
public class Enterprise extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1273396219086361842L;

	@Column(name = "code", nullable=false, unique=true, length=15)
  private String code;

	@Column(name = "name", nullable=false, length=200)
  private String name;

	@Column(name = "name_local", nullable=false, length=200)
  private String nameLocal;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Enterprise parent;

	@ManyToOne
	@JoinColumn(name = "coordinator_id")
	private ContactProc coordinator;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "issued_date")
	private Date issuedDate;

	@Column(name = "main_specialized", columnDefinition="TEXT")
	private String mainSpecialized;

	@Column(name = "sub_specialized", columnDefinition="TEXT")
	private String subSpecialized;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;
	
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

	public ContactProc getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(ContactProc coordinator) {
		this.coordinator = coordinator;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getNameLocal() {
		return nameLocal;
	}

	public void setNameLocal(String nameLocal) {
		this.nameLocal = nameLocal;
	}

	/*public Set<Policy> getPolicies() {
		return policies;
	}

	public void setPolicies(Set<Policy> policies) {
		this.policies = policies;
	}*/

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
