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
package net.brilliance.domain.entity.dmx;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.contact.ContactProc;
import net.brilliance.domain.entity.stock.Store;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A user.
 * 
 * @author ducbq
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enterprise_store")
@EqualsAndHashCode(callSuper = true)
public class EnterpriseStore extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3943460791194289252L;

	@ManyToOne(targetEntity=Enterprise.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "enterprise_detail_id")
	private EnterpriseDetail enterpriseDetail;

	@ManyToOne(targetEntity=Store.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "store_id")
	private Store store;

	@ManyToOne
	@JoinColumn(name = "coordinator_id")
	private ContactProc coordinator;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "commencement_date")
	private Date commencementDate;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public ContactProc getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(ContactProc coordinator) {
		this.coordinator = coordinator;
	}

	public Date getCommencementDate() {
		return commencementDate;
	}

	public void setCommencementDate(Date commencementDate) {
		this.commencementDate = commencementDate;
	}

	public EnterpriseDetail getEnterpriseDetail() {
		return enterpriseDetail;
	}

	public void setEnterpriseDetail(EnterpriseDetail enterpriseDetail) {
		this.enterpriseDetail = enterpriseDetail;
	}

}
