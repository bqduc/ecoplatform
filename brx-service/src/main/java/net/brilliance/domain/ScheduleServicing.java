package net.brilliance.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.domain.entity.general.Currency;
import net.brilliance.domain.model.GeneralStatus;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A schedule servicing or BRX.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "brx_schedule_servicing")
@EqualsAndHashCode(callSuper=false)
public class ScheduleServicing extends BizObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8960435938527339307L;

	@ManyToOne(targetEntity=BusinessPackage.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "business_package_id", insertable=false, updatable=false)
	private BusinessPackage businessPackage;

	@Column(name="status")
  @Enumerated(EnumType.ORDINAL)
	private GeneralStatus status;

	@ManyToOne(targetEntity=Contact.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "service_contact_id", insertable=false, updatable=false)
	private Contact serviceContact;

	@ManyToOne(targetEntity=Currency.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "currency_id", insertable=false, updatable=false)
	private Currency currency;

	@Digits(integer=12, fraction=2)
	@Column(name = "budget")
	private BigDecimal budget;

	@Digits(integer=12, fraction=2)
	@Column(name = "actual_cost")
	private BigDecimal actualCost;

	@Digits(integer=12, fraction=2)
	@Column(name = "deposit_cost")
	private BigDecimal depositCost;

	@Digits(integer=12, fraction=2)
	@Column(name = "expected_cost")
	private BigDecimal expectedCost;

	@Column(name = "comments", columnDefinition="TEXT")
	private String comments;

	public BusinessPackage getBusinessPackage() {
		return businessPackage;
	}

	public void setBusinessPackage(BusinessPackage businessPackage) {
		this.businessPackage = businessPackage;
	}

	public GeneralStatus getStatus() {
		return status;
	}

	public void setStatus(GeneralStatus status) {
		this.status = status;
	}

	public Contact getServiceContact() {
		return serviceContact;
	}

	public void setServiceContact(Contact serviceContact) {
		this.serviceContact = serviceContact;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public BigDecimal getActualCost() {
		return actualCost;
	}

	public void setActualCost(BigDecimal actualCost) {
		this.actualCost = actualCost;
	}

	public BigDecimal getDepositCost() {
		return depositCost;
	}

	public void setDepositCost(BigDecimal depositCost) {
		this.depositCost = depositCost;
	}

	public BigDecimal getExpectedCost() {
		return expectedCost;
	}

	public void setExpectedCost(BigDecimal expectedCost) {
		this.expectedCost = expectedCost;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


}
