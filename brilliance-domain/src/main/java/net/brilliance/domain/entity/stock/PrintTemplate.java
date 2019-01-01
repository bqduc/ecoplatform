/**
 * 
 */
package net.brilliance.domain.entity.stock;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.domain.model.doc.DocumentType;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "print_template")
@EqualsAndHashCode(callSuper = true)
public class PrintTemplate extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6343470338078848570L;

	@Column(name = "code", nullable=false, unique=true, length=15)
  private String code;

	@Column(name = "name", nullable=false, length=200)
  private String name;

	@Column(name = "note", columnDefinition="TEXT")
  private String note;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private PrintTemplate parent;

	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "DOC_TYPE")
	@Enumerated(EnumType.ORDINAL)
	private DocumentType documentType;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "issue_date")
	private Date issueDate;

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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public PrintTemplate getParent() {
		return parent;
	}

	public void setParent(PrintTemplate parent) {
		this.parent = parent;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

}
