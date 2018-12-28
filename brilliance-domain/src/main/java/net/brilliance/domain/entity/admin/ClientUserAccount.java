/**
 * 
 */
package net.brilliance.domain.entity.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_user_account")
@EqualsAndHashCode(callSuper = true)
public class ClientUserAccount extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -78954046194922264L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "user_account_id")
	private UserAccount userAccount;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "last_approved_user_id")
	private UserAccount lastApprovedBy;

	@Column(name = "last_approved_date")
	private Date lastApprovedDate;

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public UserAccount getLastApprovedBy() {
		return lastApprovedBy;
	}

	public void setLastApprovedBy(UserAccount lastApprovedBy) {
		this.lastApprovedBy = lastApprovedBy;
	}

	public Date getLastApprovedDate() {
		return lastApprovedDate;
	}

	public void setLastApprovedDate(Date lastApprovedDate) {
		this.lastApprovedDate = lastApprovedDate;
	}

}
