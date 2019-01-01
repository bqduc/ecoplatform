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
package net.brilliance.domain.entity.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.crx.Team;
import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A contact team connection class.
 * 
 * @author Bui Quy Duc
 */
@Entity
@Table(name = "contact_team")
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class ContactTeam extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3319277097652737469L;

	@ManyToOne
	@JoinColumn(name = "contact_id")
	private Contact contact;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@Column(name="designation")
	private String designation;

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

}
