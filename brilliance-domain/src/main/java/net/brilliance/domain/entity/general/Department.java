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

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A user.
 * 
 * @author ducbq
 */
@Data
@Entity
@Table(name = "department")
@EqualsAndHashCode(callSuper = true)
public class Department extends BizObjectBase {
	private static final long serialVersionUID = -2698272402571269128L;

	@NotNull
	@Size(min = 3, max=GlobalConstants.SIZE_SERIAL)
	@Column(unique = true)
	private String code;

	@Size(max = 150)
	@Column(name="name")
	private String name;
	
	@Size(max = 150)
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
	private Department parent;

	@ManyToOne
	@JoinColumn(name = "catalog_id")
	private Catalogue catalog;

	@Transient
	private int numberOfCategories = 0;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTranslatedName() {
		return translatedName;
	}

	public void setTranslatedName(String translatedName) {
		this.translatedName = translatedName;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date dateOfPublication) {
		this.issueDate = dateOfPublication;
	}

	public int getNumberOfCategories() {
		return numberOfCategories;
	}

	public void setNumberOfCategories(int numberOfCategories) {
		this.numberOfCategories = numberOfCategories;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Catalogue getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalogue catalog) {
		this.catalog = catalog;
	}

	public static Department getInstance(String code, String name){
		Department fetchedObject = new Department();
		fetchedObject.setCode(code);
		fetchedObject.setName(name);
		return fetchedObject;
	}
}
