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
package net.brilliance.domain.entity.stock;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.common.ListUtility;
import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.domain.entity.general.Currency;
import net.brilliance.domain.entity.general.MeasureUnit;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A product.
 * 
 * @author ducbq
 */
@Data
@Entity
@Table(name = "product")
@EqualsAndHashCode(callSuper = true)
public class Product extends BizObjectBase {
	private static final long serialVersionUID = -2698272402571269128L;

	@NotNull
	@Size(min = 3, max=GlobalConstants.SIZE_SERIAL)
	@Column(unique = true)
	private String code;

	@Size(max = 150)
	@Column(name="long_name")
	private String longName;

	@Size(max = 500)
	@Column(name="name")
	private String name;

	@Size(max = 150)
	@Column(name="translated_name")
	private String translatedName;

	@Column(name="concentration", length=150)
	private String concentration;

	@Column(name="active_principle", length=150)
	private String activePrinciple;	

	@Column(name="processing_type", length=50)
	private String processingType;

	@Column(name="packaging", length=50)
	private String packaging;

	@Column(name="standard", length=50)
	private String standard;

	@Column(name="expectation_of_life", length=50)
	private String expectationOfLife;

	@Column(name="production_company", length=150)
	private String productionCompany;

	@Column(name="production_country", length=50)
	private String productionCountry;

	@Column(name="production_address", length=250)
	private String productionAddress;

	@Column(name="registration_company", length=80)
	private String registrationCompany;

	@Column(name="registrationCountry", length=50)
	private String registrationCountry;

	@Column(name="registration_address", length=250)
	private String registrationAddress;

	@Column(name="root")
	private String root;

	@Column(name="circular_no")
	private String nationalCircularNo;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "photo", columnDefinition="TEXT")
	private String photo;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "manufacturing_date")
	private Date manufacturingDate;

	@Column(name="maintenance_level")
	private String maintenanceLevel;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "issue_date")
	private Date issueDate;

	@Column(name="reset_date")
	private ZonedDateTime resetDate = null;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "available_date")
	private Date availableDate;

	@ManyToOne(targetEntity=Contact.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "vendor_id")
	private Contact vendor;

	@Column(name="vendor_part_number", length=GlobalConstants.SIZE_CODE)
	private String vendorPartNumber;

	@ManyToOne(targetEntity=Contact.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "manufacturer_id")
	private Contact manufacturer;

	@Column(name="manufacturer_part_number", length=GlobalConstants.SIZE_CODE)
	private String manufacturerPartNumber;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Product parent;

	@Column(name="minimum_options")
	private Integer minimumOptions;

	@Column(name="maximum_options")
	private Integer maximumOptions;

	@Transient
	private Long categoryId;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Catalogue category;

	@ManyToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;

	@ManyToOne(targetEntity=MeasureUnit.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "measure_unit_id")
	private MeasureUnit measureUnit;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<ProductCatalog> productCatalogues = ListUtility.createArrayList();

	@Transient
	private List<Catalogue> catalogues = ListUtility.createArrayList();

	public String getCode() {
		return code;
	}

	public Product setCode(String code) {
		this.code = code;
		return this;
	}

	public String getLongName() {
		return longName;
	}

	public Product setLongName(String longName) {
		this.longName = longName;
		return this;
	}

	public String getName() {
		return name;
	}

	public Product setName(String shortName) {
		this.name = shortName;
		return this;
	}

	public ZonedDateTime getResetDate() {
		return resetDate;
	}

	public Product setResetDate(ZonedDateTime resetDate) {
		this.resetDate = resetDate;
		return this;
	}

	public String getPhoto() {
		return photo;
	}

	public Product setPhoto(String photo) {
		this.photo = photo;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Product setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getTranslatedName() {
		return translatedName;
	}

	public Product setTranslatedName(String translatedName) {
		this.translatedName = translatedName;
		return this;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public Product setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
		return this;
	}

	/*public Category getCategory() {
		return category;
	}

	public Product setCategory(Category category) {
		this.category = category;
		return this;
	}*/

	public String getConcentration() {
		return concentration;
	}

	public Product setConcentration(String concentration) {
		this.concentration = concentration;
		return this;
	}

	public String getActivePrinciple() {
		return activePrinciple;
	}

	public Product setActivePrinciple(String activePrinciple) {
		this.activePrinciple = activePrinciple;
		return this;
	}

	public String getProcessingType() {
		return processingType;
	}

	public Product setProcessingType(String processingType) {
		this.processingType = processingType;
		return this;
	}

	public String getPackaging() {
		return packaging;
	}

	public Product setPackaging(String packaging) {
		this.packaging = packaging;
		return this;
	}

	public String getStandard() {
		return standard;
	}

	public Product setStandard(String standard) {
		this.standard = standard;
		return this;
	}

	public String getExpectationOfLife() {
		return expectationOfLife;
	}

	public Product setExpectationOfLife(String expectationOfLife) {
		this.expectationOfLife = expectationOfLife;
		return this;
	}

	public String getProductionCompany() {
		return productionCompany;
	}

	public Product setProductionCompany(String productionCompany) {
		this.productionCompany = productionCompany;
		return this;
	}

	public String getProductionCountry() {
		return productionCountry;
	}

	public Product setProductionCountry(String productionCountry) {
		this.productionCountry = productionCountry;
		return this;
	}

	public String getProductionAddress() {
		return productionAddress;
	}

	public Product setProductionAddress(String productionAddress) {
		this.productionAddress = productionAddress;
		return this;
	}

	public String getRegistrationCompany() {
		return registrationCompany;
	}

	public Product setRegistrationCompany(String registrationCompany) {
		this.registrationCompany = registrationCompany;
		return this;
	}

	public String getRegistrationCountry() {
		return registrationCountry;
	}

	public Product setRegistrationCountry(String registrationCountry) {
		this.registrationCountry = registrationCountry;
		return this;
	}

	public String getRegistrationAddress() {
		return registrationAddress;
	}

	public Product setRegistrationAddress(String registrationAddress) {
		this.registrationAddress = registrationAddress;
		return this;
	}

	public String getRoot() {
		return root;
	}

	public Product setRoot(String root) {
		this.root = root;
		return this;
	}

	public String getNationalCircularNo() {
		return nationalCircularNo;
	}

	public Product setNationalCircularNo(String circularNo) {
		this.nationalCircularNo = circularNo;
		return this;
	}

	public Product getParent() {
		return parent;
	}

	public void setParent(Product parent) {
		this.parent = parent;
	}

	/*public List<ProductCatalog> getCategories() {
		return categories;
	}

	public void setCategories(List<ProductCatalog> categories) {
		this.categories = categories;
	}

	public List<ProductDepartment> getDepartments() {
		return departments;
	}

	public void setDepartments(List<ProductDepartment> departments) {
		this.departments = departments;
	}*/

	public MeasureUnit getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(MeasureUnit measureUnit) {
		this.measureUnit = measureUnit;
	}

	public static Product getInstance(String code, String longName, String shortName){
		Product fetchedObject = new Product();
		fetchedObject.setCode(code);
		fetchedObject.setLongName(longName);
		fetchedObject.setName(shortName);
		return fetchedObject;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<ProductCatalog> getProductCatalogues() {
		return productCatalogues;
	}

	public void setProductCatalogues(List<ProductCatalog> productCatalogues) {
		this.productCatalogues = productCatalogues;
	}
}
