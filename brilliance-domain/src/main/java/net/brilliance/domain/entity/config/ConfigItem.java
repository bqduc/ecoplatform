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
package net.brilliance.domain.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.framework.entity.BaseObject;
import net.brilliance.framework.global.GlobalConstants;

/**
 * An item.
 * 
 * @author ducbq
 */
@Builder
@Data
@Entity
@Table(name = "config_item")
@EqualsAndHashCode(callSuper = true)
public class ConfigItem extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7965749773392139946L;

	@NotNull
	@Size(min = GlobalConstants.SIZE_CODE_MIN, max = GlobalConstants.SIZE_CODE)
	@Column(unique = true)
	private String code;

	@Size(max = GlobalConstants.SIZE_NAME)
	@Column(name="subtype")
	private String name;

	@ManyToOne
	@JoinColumn(name = "item_type_id")
	private Item itemType;
}
