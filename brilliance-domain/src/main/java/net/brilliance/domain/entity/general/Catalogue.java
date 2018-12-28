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
package net.brilliance.domain.entity.general;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A user.
 * 
 * @author ducbq
 */
@Builder
@Data
@Entity
@Table(name = "catalogue")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Catalogue extends BizObjectBase {
	private static final long serialVersionUID = -2698272402571269128L;

	@NotNull
	@Size(min = 3, max=GlobalConstants.SIZE_SERIAL)
	@Column(unique = true)
	private String code;

	@Size(max = GlobalConstants.SIZE_NAME)
	@Column(name="name")
	private String name;
	
	@Size(max = GlobalConstants.SIZE_NAME)
	@Column(name="translated_name")
	private String translatedName;

	@Lob
	@Column(name = "description", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String description;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "issue_date")
	private Date issueDate;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Catalogue parent;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "subtype_id")
	private CatalogueSubtype catalogSubtype;

	@Transient
	@Builder.Default
	private int numberOfDepartments = 0;
}
