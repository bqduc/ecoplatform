package net.brilliance.domain.entity.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dmx_audit_object")
public class AuditObject extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1496377313755338213L;

	/**
	 * Set/Get the value related to the column: BUSINESS_OBJECT_SERIAL
	 */
	@Column(name="business_object_serial")
	private java.lang.String businessObjectSerial;

	/**
	 * Set/Get the value related to the column: USER_ACCOUNT
	 */
	@Column(name = "user_account", length=GlobalConstants.SIZE_SERIAL)
	private java.lang.String userAccount;

	/**
	 * Set/Get the value related to the column: ISSUE_DATE
	 */
	@Column(name = "issue_date")
	private java.util.Date issueDate;

	/**
	 * Set/Get the value related to the column: USER_ACTION
	 */
	@Column(name = "user_action", length=150)
	private java.lang.String userAction;

	public java.lang.String getBusinessObjectSerial() {
		return businessObjectSerial;
	}

	public void setBusinessObjectSerial(java.lang.String businessObjectSerial) {
		this.businessObjectSerial = businessObjectSerial;
	}

	public java.lang.String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(java.lang.String userAccount) {
		this.userAccount = userAccount;
	}

	public java.util.Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(java.util.Date issueDate) {
		this.issueDate = issueDate;
	}

	public java.lang.String getUserAction() {
		return userAction;
	}

	public void setUserAction(java.lang.String userAction) {
		this.userAction = userAction;
	}
}