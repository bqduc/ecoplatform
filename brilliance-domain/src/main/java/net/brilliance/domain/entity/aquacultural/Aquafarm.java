/**
 * 
 */
package net.brilliance.domain.entity.aquacultural;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.brilliance.domain.entity.contact.ClientProfile;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "aquafarm")
public class Aquafarm extends BizObjectBase {
	@Column(name = "code", unique = true)
	private String code;

	@Column(name = "location")
	private String location;

	@Column(name = "address")
	private String address;

	@ManyToOne
	@JoinColumn(name = "aquaculturist_id")
	private ClientProfile aquaculturist;

	@Column(name = "comments")
	private String comments;

	@Column(name = "status")
	private String status;

	public Aquafarm(String code, String location, String address, String status, String comments) {
		super();
		this.code = code;
		this.location = location;
		this.address = address;
		this.comments = comments;
		this.status = status;
	}

	public Aquafarm() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ClientProfile getAquaculturist() {
		return aquaculturist;
	}

	public void setAquaculturist(ClientProfile aquaculturist) {
		this.aquaculturist = aquaculturist;
	}
}
