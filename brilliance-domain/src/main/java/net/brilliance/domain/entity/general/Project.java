package net.brilliance.domain.entity.general;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.crx.contact.Contact;

/**
 * A Contact.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project")
public class Project extends ProjectBase {
	private static final long serialVersionUID = 3428052362186176103L;

	@Column(name = "license", length = 20, nullable = false)
	private String code;

	@Column(name = "name", length = 250, nullable = false)
	private String name;

	@Column(name = "investor")
	private String investor;

	@Column(name = "issue_date")
	@DateTimeFormat(iso = ISO.DATE, pattern="dd/MM/yyyy")
	private Date issueDate;

	@Column(name = "invesment_model")
	private String investmentModel;

	@Column(name = "investor_country")
	private String investorCountry;

	@Column(name = "business_domain", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String businessDomain;

	@Column(name = "investment_capital")
	private BigDecimal investmentCapital;

	@Column(name = "chartered_capital")
	private BigDecimal charteredCapital;

	@Column(name = "implement_address", columnDefinition = "TEXT")
	private String implementAddress;

	@Column(name = "implement_duration")
	private int implementDuration;// By months

	@Column(name = "implement_due_date")
	@DateTimeFormat(iso = ISO.DATE, pattern="dd/MM/yyyy")
	private Date implementDueDate;

	@Column(name = "implement_comments", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String implementComments;

	@Column(name = "contact_address", columnDefinition = "TEXT")
	private String contactAddress;

	@Column(name = "date_of_licence")
	//@DateTimeFormat(iso = ISO.DATE, pattern="dd/MM/yyyy")
	//@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(iso = ISO.DATE)
	private Date dateOfLicence;

	@Column(name = "started_date")
	private Date startedDate;

	@Column(name = "closed_date")
	private Date closedDate;

	@Column(name = "description", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String description;

	@ManyToOne
	@JoinColumn(name = "manager_id")
	private Contact manager;

	@ManyToOne
	@JoinColumn(name = "assistant_manager_id")
	private Contact assistantManager;

	@ManyToOne
	@JoinColumn(name = "sub_assistant_manager_id")
	private Contact subAssistantManager;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Project parent;

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

	public Date getDateOfLicence() {
		return dateOfLicence;
	}

	public void setDateOfLicence(Date dateOfLicence) {
		this.dateOfLicence = dateOfLicence;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String license) {
		this.code = license;
	}

	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getInvestmentModel() {
		return investmentModel;
	}

	public void setInvestmentModel(String invesmentModel) {
		this.investmentModel = invesmentModel;
	}

	public String getInvestorCountry() {
		return investorCountry;
	}

	public void setInvestorCountry(String investorCountry) {
		this.investorCountry = investorCountry;
	}

	public String getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(String businessDomain) {
		this.businessDomain = businessDomain;
	}

	public BigDecimal getInvestmentCapital() {
		return investmentCapital;
	}

	public void setInvestmentCapital(BigDecimal investmentCapital) {
		this.investmentCapital = investmentCapital;
	}

	public BigDecimal getCharteredCapital() {
		return charteredCapital;
	}

	public void setCharteredCapital(BigDecimal charteredCapital) {
		this.charteredCapital = charteredCapital;
	}

	public String getImplementAddress() {
		return implementAddress;
	}

	public void setImplementAddress(String implementAddress) {
		this.implementAddress = implementAddress;
	}

	public int getImplementDuration() {
		return implementDuration;
	}

	public void setImplementDuration(int implementDuration) {
		this.implementDuration = implementDuration;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public Project getParent() {
		return parent;
	}

	public void setParent(Project parent) {
		this.parent = parent;
	}

	public Date getImplementDueDate() {
		return implementDueDate;
	}

	public void setImplementDueDate(Date implementDueDate) {
		this.implementDueDate = implementDueDate;
	}

	public String getImplementComments() {
		return implementComments;
	}

	public void setImplementComments(String implementComments) {
		this.implementComments = implementComments;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}

	public Contact getManager() {
		return manager;
	}

	public void setManager(Contact manager) {
		this.manager = manager;
	}

	public Contact getAssistantManager() {
		return assistantManager;
	}

	public void setAssistantManager(Contact assistantManager) {
		this.assistantManager = assistantManager;
	}

	public Contact getSubAssistantManager() {
		return subAssistantManager;
	}

	public void setSubAssistantManager(Contact subAssistantManager) {
		this.subAssistantManager = subAssistantManager;
	}
}
