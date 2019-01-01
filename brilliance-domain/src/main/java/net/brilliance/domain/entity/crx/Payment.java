package net.brilliance.domain.entity.crx;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

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
import javax.validation.constraints.Digits;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.domain.entity.general.Currency;
import net.brilliance.domain.model.GeneralStatus;
import net.brilliance.domain.model.enums.CRXGeneralType;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A Payment for CRX.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "payment")
@EqualsAndHashCode(callSuper=false)
public class Payment extends BizObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8271976867916463136L;

	@Column(name = "name", nullable = false, unique=true, length=200)
	private String name;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	private UserAccount assignedTo;

	@Column(name="status")
  @Enumerated(EnumType.ORDINAL)
	private GeneralStatus status;

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(
			name = "payment_team", 
			inverseJoinColumns = {@JoinColumn(name = "team_id")},
			joinColumns = {@JoinColumn(name = "payment_id")}
	)
	private Set<Team> paymentTeams;

  @Column(name="payment_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date paymentDate;

	@Column(name="payment_type_id")
  @Enumerated(EnumType.ORDINAL)
	private CRXGeneralType paymentType;

	@ManyToOne(targetEntity=Currency.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "currency_id", insertable=false, updatable=false)
	private Currency currency;

	@Digits(integer=12, fraction=2)
	@Column(name = "amount")
	private BigDecimal amount;

	@Digits(integer=12, fraction=2)
	@Column(name = "bank_fee")
	private BigDecimal bankFee;

	@Column(name = "customer_reference", length=120)
	private String customerReference;

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

	public GeneralStatus getStatus() {
		return status;
	}

	public void setStatus(GeneralStatus status) {
		this.status = status;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<Team> getPaymentTeams() {
		return paymentTeams;
	}

	public void setPaymentTeams(Set<Team> paymentTeams) {
		this.paymentTeams = paymentTeams;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public CRXGeneralType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(CRXGeneralType paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBankFee() {
		return bankFee;
	}

	public void setBankFee(BigDecimal bankFee) {
		this.bankFee = bankFee;
	}

	public String getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
