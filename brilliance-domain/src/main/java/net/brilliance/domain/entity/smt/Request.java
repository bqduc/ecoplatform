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
package net.brilliance.domain.entity.smt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.contact.Contact;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.framework.entity.BaseObject;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A user.
 * 
 * @author ducbq
 */
@Builder
@Data
@Entity
@Table(name = "sm_request")
@EqualsAndHashCode(callSuper = true)
public class Request extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2032489908746753365L;

	@NotNull
	@Size(min = 3, max=GlobalConstants.SIZE_SERIAL)
	@Column(unique = true)
	private String number;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Contact company;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Contact customer;

	@ManyToOne
	@JoinColumn(name = "contact_id")
	private Contact contact;

	////////////
	@Lob
	@Column(name = "notes", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String notes;
	
	@Column(name="template")
	private String template;

	@Lob
	@Column(name = "summary", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String summary;

	///////////////////////////
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "service_id")
	private Catalogue service;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "configure_item_id")
	private Catalogue configureItem;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "target_date")
	private Date targetDate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "impact_id")
	private Catalogue impact;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "urgency_id")
	private Catalogue urgency;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "priority_id")
	private Catalogue priority;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "request_type_id")
	private Catalogue requestType;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "reported_source_id")
	private Catalogue reportedSource;

	///////////////////////////////////////////////////////////////
	@ManyToOne
	@JoinColumn(name = "assigned_group_id")
	private Contact assignedGroup;

	@ManyToOne
	@JoinColumn(name = "assignee_id")
	private Contact assignee;

	@ManyToOne
	@JoinColumn(name = "vendor_group_id")
	private Contact vendorGroup;

	@Size(max = 15)
	@Column(name="vendor_request_number")
	private String vendorRequestNumber;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "status_id")
	private Catalogue status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "status_reason_id")
	private Catalogue statusReason;

	@Lob
	@Column(name = "resolution", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String resolution;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "issue_date")
	private Date issueDate;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "creation_date")
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Request parent;
}
