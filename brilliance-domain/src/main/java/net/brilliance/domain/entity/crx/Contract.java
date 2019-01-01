package net.brilliance.domain.entity.crx;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A contract or CRX.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "contract")
@EqualsAndHashCode(callSuper=false)
public class Contract extends BizObjectBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2185824208770219101L;

	@Column(name="code", length=GlobalConstants.SIZE_SERIAL, unique=true)
	private String code;

	@Column(name = "name", nullable = false, unique=true, length=200)
	private String name;

	@ManyToOne(targetEntity=UserAccount.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "assigned_user_id")
	private UserAccount assignedTo;

	@Column(name="status")
  @Enumerated(EnumType.ORDINAL)
	private GeneralStatus status;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "type_id")
	private ContractType contractType;

	@Digits(integer=12, fraction=2)
	@Column(name = "contract_value")
	private BigDecimal contractValue;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "opportunity_id")
	private Opportunity opportunity;

	@ManyToOne(targetEntity=Team.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "team_id")
	private Team team;

  @Column(name="company_signed_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date companySignedDate;

  @Column(name="customer_signed_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date customerSignedDate;

  @Column(name="expiration_notice_date")
	@DateTimeFormat(iso = ISO.DATE_TIME)
  private Date expirationNoticeDate;

  @Column(name="start_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date startDate;

  @Column(name="end_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date endDate;

	@ManyToOne(targetEntity=Currency.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	private Currency currency;

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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ContractType getContractType() {
		return contractType;
	}

	public void setContractType(ContractType contractType) {
		this.contractType = contractType;
	}

	public BigDecimal getContractValue() {
		return contractValue;
	}

	public void setContractValue(BigDecimal contractValue) {
		this.contractValue = contractValue;
	}

	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
	}

	public Date getCompanySignedDate() {
		return companySignedDate;
	}

	public void setCompanySignedDate(Date companySignedDate) {
		this.companySignedDate = companySignedDate;
	}

	public Date getCustomerSignedDate() {
		return customerSignedDate;
	}

	public void setCustomerSignedDate(Date customerSignedDate) {
		this.customerSignedDate = customerSignedDate;
	}

	public Date getExpirationNoticeDate() {
		return expirationNoticeDate;
	}

	public void setExpirationNoticeDate(Date expirationNoticeDate) {
		this.expirationNoticeDate = expirationNoticeDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
