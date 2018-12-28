/**
 * 
 */
package net.brilliance.domain.entity.stock;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.contact.ContactProc;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@Data
@Entity
@Table(name = "stk_store")
@EqualsAndHashCode(callSuper = true)
public class Store extends BizObjectBase {
	private static final long serialVersionUID = 4967658673292624286L;

	@Column(name = "code", nullable=false, unique=true, length=15)
  private String code;

	@Column(name = "name", nullable=false, length=200)
  private String name;

	@Column(name = "address", length=250)
  private String address;

	@Column(name = "hot_line", length=30)
  private String hotLine;

	@Column(name = "note", columnDefinition="TEXT")
  private String note;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Store parent;

	@ManyToOne
	@JoinColumn(name = "coordinator_id")
	private ContactProc coordinator;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "photo", columnDefinition="TEXT")
	private String photo;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Store getParent() {
		return parent;
	}

	public void setParent(Store parent) {
		this.parent = parent;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public ContactProc getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(ContactProc coordinator) {
		this.coordinator = coordinator;
	}

	public String getHotLine() {
		return hotLine;
	}

	public void setHotLine(String hotLine) {
		this.hotLine = hotLine;
	}

	public static Store getInstance(String code, String name, String address, String note){
		Store instance = new Store();
		instance.setCode(code);
		instance.setName(name);
		instance.setAddress(address);
		instance.setNote(note);
		return instance;
	}
}
