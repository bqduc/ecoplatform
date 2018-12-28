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

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.audit.AuditLog;
import net.brilliance.domain.entity.config.Item;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A catalog sub type.
 * 
 * @author ducbq
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "catalogue_subtype")
@EqualsAndHashCode(callSuper = true)
public class CatalogueSubtype extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8694018855853561542L;

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

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private CatalogueSubtype parent;

	/*@Column(name="level")
	private Byte level; */
	
	@ManyToOne
	@JoinColumn(name = "level_id")
	private Item level;

	/**
	 * 10: sub type for local level of catalog
	 * 11: sub type for secondary level of catalog
	 * 12: sub type for main level of catalog
	 * 20: sub type for local level of group catalog
	 * 21: sub type for secondary level of group catalog
	 * 22: sub type for main level of group catalog
	 * 30: sub type for local level of goods category
	 * 31: sub type for secondary level of goods category
	 * 32: sub type for main level of goods category
	 * 40: sub type for local level of goods department
	 * 41: sub type for secondary level of goods department
	 * 42: sub type for main level of goods department
	 * ........
	 */

  @AssociationOverrides( {
    @AssociationOverride(name = "createdBy", joinColumns = @JoinColumn(name = "created_user_id")),
    @AssociationOverride(name = "lastModifiedBy", joinColumns = @JoinColumn(name = "modified_user_id")) })
  private AuditLog auditLog;
}
