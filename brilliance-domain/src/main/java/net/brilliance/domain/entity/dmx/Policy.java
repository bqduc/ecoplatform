/**
 * 
 */
package net.brilliance.domain.entity.dmx;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.EqualsAndHashCode;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@Entity
@Table(name = "dmx_policy")
@EqualsAndHashCode(callSuper = true)
public class Policy extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3386015881263644672L;

	@Column(name = "code", nullable=false, unique=true, length=15)
  private String code;

	@Column(name = "name", nullable=false, length=200)
  private String name;

	@Column(name = "type", length=8)
  private String type;

	@Column(name = "note", columnDefinition="TEXT")
  private String note;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "issued_date")
	private Date issuedDate;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

}
