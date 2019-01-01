/**
 * 
 */
package net.brilliance.domain.entity.general;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.common.ListUtility;
import net.brilliance.domain.model.GeneralStatus;
import net.brilliance.framework.entity.ObjectBase;

/**
 * @author ducbq
 *
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name="document")
public class Document extends ObjectBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3317142524028477899L;

	@Column(name="name", length=20)
	private String name;

	@Column(name="issue_date")
	private Date issueDate;

	@Column(name="issue_place", length=100)
	private String issuePlace;

	@Column(name="expired_date")
	private Date expiredDate;

	@Column(name="status")
  @Enumerated(EnumType.ORDINAL)
	private GeneralStatus status;

	@Builder.Default
	@OneToMany(mappedBy="document", cascade = CascadeType.ALL)
	private Set<DocumentAttachment> attachments = ListUtility.newHashSet();

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getIssuePlace() {
		return issuePlace;
	}

	public void setIssuePlace(String issuePlace) {
		this.issuePlace = issuePlace;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Set<DocumentAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<DocumentAttachment> attachments) {
		this.attachments = attachments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GeneralStatus getStatus() {
		return status;
	}

	public void setStatus(GeneralStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
