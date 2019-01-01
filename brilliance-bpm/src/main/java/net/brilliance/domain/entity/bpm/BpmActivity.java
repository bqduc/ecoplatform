/*
* Copyright 2017, Bui Quy Duc
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.brilliance.domain.entity.bpm;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A user.
 * 
 * @author ducbq
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bpm_activity")
public class BpmActivity extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 572678779754716429L;

	@Size(max = 100)
	@Column(name="title")
	private String title;
	
	@Lob
	@Column(name = "notes", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String notes;

	@Column(name = "received_date")
	private Date receivedDate;

	@Column(name = "due_date")
	private Date dueDate;

	@Column(name = "action_date")
	private Date actionDate;

	@ManyToOne
	@JoinColumn(name = "handle_user_id")
	private UserAccount handleUser;

	@ManyToOne
	@JoinColumn(name = "parent_activity_id")
	private BpmActivity parentActivity;

	@ManyToOne
	@JoinColumn(name = "process_id")
	private BpmProcess process;

	@ManyToOne
	@JoinColumn(name = "escalation_user_id")
	private UserAccount escalationUser;

	@Column(name = "host_business_object_id")
	private Long hostBusinessObjectId;

	@Size(max = 100)
	@Column(name="host_business_object_type")
	private String hostBusinessObjectType;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "attachment", columnDefinition="TEXT")
	private String attachment;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public UserAccount getHandleUser() {
		return handleUser;
	}

	public void setHandleUser(UserAccount handleUser) {
		this.handleUser = handleUser;
	}

	public BpmProcess getProcess() {
		return process;
	}

	public void setProcess(BpmProcess process) {
		this.process = process;
	}

	public UserAccount getEscalationUser() {
		return escalationUser;
	}

	public void setEscalationUser(UserAccount escalationUser) {
		this.escalationUser = escalationUser;
	}

	public Long getHostBusinessObjectId() {
		return hostBusinessObjectId;
	}

	public void setHostBusinessObjectId(Long hostBusinessObjectId) {
		this.hostBusinessObjectId = hostBusinessObjectId;
	}

	public String getHostBusinessObjectType() {
		return hostBusinessObjectType;
	}

	public void setHostBusinessObjectType(String hostBusinessObjectType) {
		this.hostBusinessObjectType = hostBusinessObjectType;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public BpmActivity getParentActivity() {
		return parentActivity;
	}

	public void setParentActivity(BpmActivity parentActivity) {
		this.parentActivity = parentActivity;
	}
}
