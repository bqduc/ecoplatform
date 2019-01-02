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
import net.brilliance.domain.entity.crx.contact.Contact;
import net.brilliance.domain.entity.general.Currency;
import net.brilliance.domain.model.enums.CRXGeneralStage;
import net.brilliance.domain.model.enums.CRXGeneralType;
import net.brilliance.domain.model.enums.CRXLeadSource;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * An Opportunity or CRX.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "opportunity")
@EqualsAndHashCode(callSuper=false)
public class Opportunity extends BizObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2085232964319407674L;

	@Column(name = "name", nullable = false, unique=true, length=200)
	private String name;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "currency_id", insertable=false, updatable=false)
	private Currency currency;

	@Digits(integer=12, fraction=2)
	@Column(name = "amount")
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@Column(name="type_id")
  @Enumerated(EnumType.ORDINAL)
	private CRXGeneralType type;

	@Column(name="lead_source_id")
  @Enumerated(EnumType.ORDINAL)
	private CRXLeadSource leadSource;
	
	@Column(name="sales_stage_id")
  @Enumerated(EnumType.ORDINAL)
	private CRXGeneralStage salesStage;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	private UserAccount assignedTo;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "team_id")
	private Team team;

  @Column(name="closed_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date closedDate;

	@Column(name = "next_step", length=100)
	private String nextStep;

	@Digits(integer=12, fraction=2)
	@Column(name = "probability")
	private Float probability;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "b2c_contact_id")
	private Contact b2cContact;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Opportunity parent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Opportunity getParent() {
		return parent;
	}

	public void setParent(Opportunity parent) {
		this.parent = parent;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CRXGeneralType getType() {
		return type;
	}

	public void setType(CRXGeneralType type) {
		this.type = type;
	}

	public CRXLeadSource getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(CRXLeadSource leadSource) {
		this.leadSource = leadSource;
	}

	public CRXGeneralStage getSalesStage() {
		return salesStage;
	}

	public void setSalesStage(CRXGeneralStage salesStage) {
		this.salesStage = salesStage;
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

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public Float getProbability() {
		return probability;
	}

	public void setProbability(Float probability) {
		this.probability = probability;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public Contact getB2cContact() {
		return b2cContact;
	}

	public void setB2cContact(Contact b2cContact) {
		this.b2cContact = b2cContact;
	}
}
