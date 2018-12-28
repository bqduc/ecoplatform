package net.brilliance.domain.entity.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * An office or business unit.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "business_unit")
public class BusinessUnit extends BizObjectBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1396860561985366652L;

	@Version
	@Column(name = "version")
	private Integer version;

	@Size(min = 5, max=GlobalConstants.SIZE_SERIAL)
	@Column(name = "code")
	private String code;

	@Size(min = 5, max = 200)
	@Column(name = "name_local", nullable = false)
	private String nameLocal;

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
	@JoinColumn(name = "parent_id")
	private BusinessUnit parent;

	@Column(name = "issued_date")
	private Date issuedDate;

	@Column(name = "published_date")
	private Date publishedDate;

	@ManyToOne
	@JoinColumn(name = "spoc_user_id")
	private UserAccount spoc; //Single Point Of Contact

	@ManyToOne
	@JoinColumn(name = "managing_user_id")
	private UserAccount manager;

	@ManyToOne
	@JoinColumn(name = "level_id")
	private Catalogue level;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

	@Override
	public String toString() {
		return "Business unit {" 
				+ "id:" + getId() 
				+ ", version: " + version 
				+ ", name: " + name 
				+ ", local name: " + nameLocal 
				+ ", publisher: " + publisher 
				+ ", published date: " + publishedDate 
				+ ", issued date: " + issuedDate
				+ '}';
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getNameLocal() {
		return nameLocal;
	}


	public void setNameLocal(String nameLocal) {
		this.nameLocal = nameLocal;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public UserAccount getPublisher() {
		return publisher;
	}


	public void setPublisher(UserAccount publisher) {
		this.publisher = publisher;
	}


	public UserAccount getIssuedBy() {
		return issuedBy;
	}


	public void setIssuedBy(UserAccount issuedBy) {
		this.issuedBy = issuedBy;
	}


	public BusinessUnit getParent() {
		return parent;
	}


	public void setParent(BusinessUnit parent) {
		this.parent = parent;
	}


	public Date getIssuedDate() {
		return issuedDate;
	}


	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}


	public Date getPublishedDate() {
		return publishedDate;
	}


	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}


	public UserAccount getSpoc() {
		return spoc;
	}


	public void setSpoc(UserAccount spoc) {
		this.spoc = spoc;
	}


	public UserAccount getManager() {
		return manager;
	}


	public void setManager(UserAccount manager) {
		this.manager = manager;
	}


	public Catalogue getLevel() {
		return level;
	}


	public void setLevel(Catalogue level) {
		this.level = level;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
}
