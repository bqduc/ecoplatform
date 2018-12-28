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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A localized item.
 * 
 * @author ducbq
 */
@Data
@Entity
@Table(name = "sys_localized_item")
@EqualsAndHashCode(callSuper = true)
public class LocalizedItem extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6421591723857343365L;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "language_id")
	private Language language;

	@Size(max = 300)
	@Column(name="value")
	private String value;

	@Lob
	@Column(name = "description", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String description;

	public String getValue() {
		return value;
	}

	public LocalizedItem setValue(String value) {
		this.value = value;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public LocalizedItem setDescription(String description) {
		this.description = description;
		return this;
	}

	public Item getItem() {
		return item;
	}

	public LocalizedItem setItem(Item item) {
		this.item = item;
		return this;
	}

	public Language getLanguage() {
		return language;
	}

	public LocalizedItem setLanguage(Language language) {
		this.language = language;
		return this;
	}

	public static LocalizedItem instance(Item item, Language language, String value, String desc){
		return new LocalizedItem()
				.setItem(item)
				.setLanguage(language)
				.setValue(value)
				.setDescription(desc);
	}
}
