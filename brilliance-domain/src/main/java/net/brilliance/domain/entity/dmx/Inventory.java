package net.brilliance.domain.entity.dmx;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.domain.entity.general.Category;
import net.brilliance.domain.entity.general.Department;
import net.brilliance.domain.model.BindingType;
import net.brilliance.domain.model.DustJacketType;
import net.brilliance.domain.model.InventoryCodeType;
import net.brilliance.domain.model.InventoryConditionType;
import net.brilliance.domain.model.InventoryType;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A Book.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventory_item")
public class Inventory extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3825826326422460206L;

	@Version
	@Column(name = "version")
	private Integer version;

	@Size(min = 5, max=GlobalConstants.SIZE_SERIAL)
	@Column(name = "code")
	private String code;

	@Size(min = 5, max = 200)
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "author", length=150)
	private String author;

	@Column(name = "publisher", length=150)
	private String publisher;

	@Column(name = "date_of_publication")
	private Date dateOfPublication;

  @Column(name="binding_type")
  @Enumerated(EnumType.ORDINAL)
  private BindingType bindingType;
	
  @Column(name="inventory_type")
  @Enumerated(EnumType.ORDINAL)
  private InventoryType inventoryType;

	@Column(name = "illustrator", length=50)
	private String illustrator;

	@Column(name = "edition", length=50)
	private String edition;

  @Column(name="code_type")
  @Enumerated(EnumType.ORDINAL)
  private InventoryCodeType inventoryCodeType;

  @Column(name="condition_type")
  @Enumerated(EnumType.ORDINAL)
  private InventoryConditionType inventoryConditionType;
	
	@Column(name = "fulfillment_center_id", length=40)
	private String fulfillmentCenterId;

	@Column(name = "sku", length=40)
	private String sku;

  @Column(name="dust_jacket_type")
  @Enumerated(EnumType.ORDINAL)
  private DustJacketType dustJacketType;

  @Column(name = "signed_by", length=40)
	private String signedBy;

  /**
   * Select the option corresponding to your choice: "y" if you are offering expedited shipping to your customers; "n" otherwise.
   * 
   * "n" = Expedited shipping not available	
   * "y" = Expedited shipping available"
   */
  @Column(name = "expedited_shipping", length=2)
	private String expeditedShipping;

  /**
   * Select the code corresponding to your choice: "1" or "n": Will ship within US only; "2" or "y": Will ship to international locations
   * 
   * "1" or "n" = Will ship in US only
   * "2" or "y" = Will ship to international locations
   */
  @Column(name = "will_ship_internationally", length=2)
	private String willShipInternationally;
  
	@Column(name = "notes", columnDefinition="TEXT")
	private String notes;

	@Column(name = "condition", columnDefinition="TEXT")
	private String condition;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "photo", columnDefinition="TEXT")
	private String photo;

	@ManyToOne
	@JoinColumn(name = "main_category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "sub_category_id")
	private Category subCategory;

	@ManyToOne
	@JoinColumn(name = "supplementary_category_id")
	private Category supplementaryCategory;

	@ManyToOne
	@JoinColumn(name = "extra_category_id")
	private Category extraCategory;

	@ManyToOne
	@JoinColumn(name = "business_group_id")
	private Department businessGroup;

	@Column(name = "online_series", length=20)
	private String 	onlineSeries;

	@Column(name = "sales_model_subscription", length=20)
	private String salesModelSubscription;

	@Column(name = "sales_model_one_time", length=20)
	private String salesModelOneTime;

	@Column(name = "capacity")
	private Double capacity;

	@Column(name = "online_availability_date")
	private Date onlineAvailabilityDate;

	@Column(name = "production_publication_date")
	private Date productionPublicationDate;

	@Column(name = "url_online_library", length=100)
	private String urlOnlineLibrary;

	@Column(name = "oclc", length=20)
	private String oclc;

	@ManyToOne
	@JoinColumn(name = "specialized_subject_area_id")
	private Department specializedSubjectArea;

	@ManyToOne
	@JoinColumn(name = "main_subject_category_id")
	private Category mainSubjectCategory;

	@Column(name = "isbn10", length=10)
	private String isbn10;

	@Column(name = "o_isbn10", length=10)
	private String oIsbn10;	

	@Column(name = "e_isbn10", length=10)
	private String eIsbn10;

	@Column(name = "isbn13", length=13)
	private String isbn13;

	@Column(name = "o_isbn13", length=13)
	private String oIsbn13;	

	@Column(name = "e_isbn13", length=13)
	private String eIsbn13;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Inventory parent;

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(
			name = "inventory_entry_vendor", 
			inverseJoinColumns = {@JoinColumn(name = "vendor_id")},
			joinColumns = {@JoinColumn(name = "inventory_entry_id")}
	)
	private Set<Contact> vendors;

	@ManyToOne
	@JoinColumn(name = "imprint_contact_id")
	private Contact imprint;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getDateOfPublication() {
		return dateOfPublication;
	}

	public void setDateOfPublication(Date dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Inventory inventoryEntry = (Inventory) o;

		if (!Objects.equals(getId(), inventoryEntry.getId()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BindingType getBindingType() {
		return bindingType;
	}

	public void setBindingType(BindingType bindingType) {
		this.bindingType = bindingType;
	}

	public String getIllustrator() {
		return illustrator;
	}

	public void setIllustrator(String illustrator) {
		this.illustrator = illustrator;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public InventoryCodeType getInventoryCodeType() {
		return inventoryCodeType;
	}

	public void setInventoryCodeType(InventoryCodeType inventoryCodeType) {
		this.inventoryCodeType = inventoryCodeType;
	}

	public InventoryConditionType getInventoryConditionType() {
		return inventoryConditionType;
	}

	public void setInventoryConditionType(InventoryConditionType inventoryConditionType) {
		this.inventoryConditionType = inventoryConditionType;
	}

	public String getFulfillmentCenterId() {
		return fulfillmentCenterId;
	}

	public void setFulfillmentCenterId(String fulfillmentCenterId) {
		this.fulfillmentCenterId = fulfillmentCenterId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public DustJacketType getDustJacketType() {
		return dustJacketType;
	}

	public void setDustJacketType(DustJacketType dustJacketType) {
		this.dustJacketType = dustJacketType;
	}

	public String getSignedBy() {
		return signedBy;
	}

	public void setSignedBy(String signedBy) {
		this.signedBy = signedBy;
	}

	public String getExpeditedShipping() {
		return expeditedShipping;
	}

	public void setExpeditedShipping(String expeditedShipping) {
		this.expeditedShipping = expeditedShipping;
	}

	public String getWillShipInternationally() {
		return willShipInternationally;
	}

	public void setWillShipInternationally(String willShipInternationally) {
		this.willShipInternationally = willShipInternationally;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Category subCategory) {
		this.subCategory = subCategory;
	}

	public Category getSupplementaryCategory() {
		return supplementaryCategory;
	}

	public void setSupplementaryCategory(Category supplementaryCategory) {
		this.supplementaryCategory = supplementaryCategory;
	}

	public Category getExtraCategory() {
		return extraCategory;
	}

	public void setExtraCategory(Category extraCategory) {
		this.extraCategory = extraCategory;
	}

	public Inventory getParent() {
		return parent;
	}

	public void setParent(Inventory parent) {
		this.parent = parent;
	}

	public Set<Contact> getVendors() {
		return vendors;
	}

	public void setVendors(Set<Contact> vendors) {
		this.vendors = vendors;
	}

	public Department getBusinessGroup() {
		return businessGroup;
	}

	public void setBusinessGroup(Department businessGroup) {
		this.businessGroup = businessGroup;
	}

	public String getOnlineSeries() {
		return onlineSeries;
	}

	public void setOnlineSeries(String onlineSeries) {
		this.onlineSeries = onlineSeries;
	}

	public String getSalesModelSubscription() {
		return salesModelSubscription;
	}

	public void setSalesModelSubscription(String salesModelSubscription) {
		this.salesModelSubscription = salesModelSubscription;
	}

	public String getSalesModelOneTime() {
		return salesModelOneTime;
	}

	public void setSalesModelOneTime(String salesModelOneTime) {
		this.salesModelOneTime = salesModelOneTime;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public Date getOnlineAvailabilityDate() {
		return onlineAvailabilityDate;
	}

	public void setOnlineAvailabilityDate(Date onlineAvailabilityDate) {
		this.onlineAvailabilityDate = onlineAvailabilityDate;
	}

	public Date getProductionPublicationDate() {
		return productionPublicationDate;
	}

	public void setProductionPublicationDate(Date productionPublicationDate) {
		this.productionPublicationDate = productionPublicationDate;
	}

	public String getUrlOnlineLibrary() {
		return urlOnlineLibrary;
	}

	public void setUrlOnlineLibrary(String urlOnlineLibrary) {
		this.urlOnlineLibrary = urlOnlineLibrary;
	}

	public String getOclc() {
		return oclc;
	}

	public void setOclc(String oclc) {
		this.oclc = oclc;
	}

	public Department getSpecializedSubjectArea() {
		return specializedSubjectArea;
	}

	public void setSpecializedSubjectArea(Department specializedSubjectArea) {
		this.specializedSubjectArea = specializedSubjectArea;
	}

	public Category getMainSubjectCategory() {
		return mainSubjectCategory;
	}

	public void setMainSubjectCategory(Category mainSubjectCategory) {
		this.mainSubjectCategory = mainSubjectCategory;
	}

	public String getIsbn10() {
		return isbn10;
	}

	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	public String getoIsbn10() {
		return oIsbn10;
	}

	public void setoIsbn10(String oIsbn10) {
		this.oIsbn10 = oIsbn10;
	}

	public String geteIsbn10() {
		return eIsbn10;
	}

	public void seteIsbn10(String eIsbn10) {
		this.eIsbn10 = eIsbn10;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String getoIsbn13() {
		return oIsbn13;
	}

	public void setoIsbn13(String oIsbn13) {
		this.oIsbn13 = oIsbn13;
	}

	public String geteIsbn13() {
		return eIsbn13;
	}

	public void seteIsbn13(String eIsbn13) {
		this.eIsbn13 = eIsbn13;
	}

	public Contact getImprint() {
		return imprint;
	}

	public void setImprint(Contact imprint) {
		this.imprint = imprint;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public InventoryType getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(InventoryType inventoryType) {
		this.inventoryType = inventoryType;
	}
}
