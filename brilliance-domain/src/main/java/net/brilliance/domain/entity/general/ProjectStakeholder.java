/*
* Copyright 2018, Bui Quy Duc
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
package net.brilliance.domain.entity.general;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.domain.model.StakeholderType;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A project shareholder definition.
 * 
 * @author ducbq
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_stakeholder")
public class ProjectStakeholder extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -952265401424820238L;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne
	@JoinColumn(name = "stakeholder_id")
	private Contact stakeholder;

	@ManyToOne
	@JoinColumn(name = "main_contact_id")
	private Contact mainContact;

	@Column(name="stakeholder_type")
  @Enumerated(EnumType.ORDINAL)
  private StakeholderType stakeholderType;

	@Column(name = "opened_date")
	private Date openedDate;

	@Column(name = "closed_date")
	private Date closedDate;

	@Column(name = "shareholder_ratio")
	private Float shareholderRatio;

	@Lob
	@Column(name = "description", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String description;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Contact getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(Contact stakeholder) {
		this.stakeholder = stakeholder;
	}

	public Contact getMainContact() {
		return mainContact;
	}

	public void setMainContact(Contact mainContact) {
		this.mainContact = mainContact;
	}

	public StakeholderType getStakeholderType() {
		return stakeholderType;
	}

	public void setStakeholderType(StakeholderType stakeholderType) {
		this.stakeholderType = stakeholderType;
	}

	public Date getOpenedDate() {
		return openedDate;
	}

	public void setOpenedDate(Date openedDate) {
		this.openedDate = openedDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getShareholderRatio() {
		return shareholderRatio;
	}

	public void setShareholderRatio(Float shareholderRatio) {
		this.shareholderRatio = shareholderRatio;
	}

}
