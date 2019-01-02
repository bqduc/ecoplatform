package net.brilliance.domain.entity.crx;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.domain.entity.common.Address;
import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.domain.model.enums.CRXGeneralStage;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * An order or CRX.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "biz_order")
@EqualsAndHashCode(callSuper=false)
public class BizOrder extends BizObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4559667558473846206L;

	@Column(name="code", length=GlobalConstants.SIZE_SERIAL, unique=true)
	private String code;

	@Column(name = "name", nullable = false, length=200)
	private String name;

	@ManyToOne
	@JoinColumn(name = "opportunity_id")
	private Opportunity opportunity;

	@Column(name="order_stage_id")
  @Enumerated(EnumType.ORDINAL)
	private CRXGeneralStage orderStage;

	@Column(name = "purchase_order_num", length=20)
	private String purchaseOrderNum;
	
  @Column(name="due_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date dueDate;

  @Column(name="shipped_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date shippedDate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "main_account_id")
	private Account mainAccount;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "main_contact_id")
	private Contact mainContact;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sub_account_id")
	private Account subAccount;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sub_contact_id")
	private Contact subContact;

	@Column(name = "payment_terms")
	private String paymentTerms;//(i.e. Cod, Due on receipt, Net 7 days, Net 15 days)
	
	@ManyToOne
	@JoinColumn(name = "quotation_id")
	private Quotation quotation;

	@Embedded
  @AttributeOverrides({
    @AttributeOverride(name="address", column=@Column(name="shipping_address")),
    @AttributeOverride(name="city", column=@Column(name="shipping_city")),
    @AttributeOverride(name="state", column=@Column(name="shipping_state")),
    @AttributeOverride(name="postalCode", column=@Column(name="shipping_postal_code")),
    @AttributeOverride(name="country", column=@Column(name="shipping_country"))
  })
  private Address shippingAddress;

	@Embedded
  @AttributeOverrides({
    @AttributeOverride(name="address", column=@Column(name="billing_address")),
    @AttributeOverride(name="city", column=@Column(name="billing_city")),
    @AttributeOverride(name="state", column=@Column(name="billing_state")),
    @AttributeOverride(name="postalCode", column=@Column(name="billing_postal_code")),
    @AttributeOverride(name="country", column=@Column(name="billing_country"))
  })
  private Address billingAddress;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	private UserAccount assignedTo;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "team_id")
	private Team team;

	@Builder.Default
	@OneToMany(mappedBy="bizOrder", cascade = CascadeType.ALL)
	private Set<BizOrderDetail> orderDetails = new HashSet<>();
	
	@Column(name = "description", columnDefinition="TEXT")
	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserAccount getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(UserAccount assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
	}

	public CRXGeneralStage getOrderStage() {
		return orderStage;
	}

	public void setOrderStage(CRXGeneralStage orderStage) {
		this.orderStage = orderStage;
	}

	public String getPurchaseOrderNum() {
		return purchaseOrderNum;
	}

	public void setPurchaseOrderNum(String purchaseOrderNum) {
		this.purchaseOrderNum = purchaseOrderNum;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public Account getMainAccount() {
		return mainAccount;
	}

	public void setMainAccount(Account mainAccount) {
		this.mainAccount = mainAccount;
	}

	public Contact getMainContact() {
		return mainContact;
	}

	public void setMainContact(Contact mainContact) {
		this.mainContact = mainContact;
	}

	public Account getSubAccount() {
		return subAccount;
	}

	public void setSubAccount(Account subAccount) {
		this.subAccount = subAccount;
	}

	public Contact getSubContact() {
		return subContact;
	}

	public void setSubContact(Contact subContact) {
		this.subContact = subContact;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public Quotation getQuotation() {
		return quotation;
	}

	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<BizOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<BizOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
