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

import net.brilliance.framework.entity.BaseObject;

/**
 * A Contact.
 */

@Entity
@Table(name = "project")
public class Project extends BaseObject {
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

	@Column(name = "description", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String description;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Project parent;

	public Project() {
	}

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

	public static Project instance(String license, String name){
		Project instance = new Project();
		instance.setCode(license);
		instance.setName(name);
		return instance;
	}

	public static Project instance(
			String license,
			String name,
			String investor,
			Date issueDate,
			String investmentModel,
			String investorCountry,
			String businessDomain,
			BigDecimal investmentCapital,
			BigDecimal charteredCapital,
			String implementAddress,
			int implementDuration,
			Date implementDueDate,
			String implementComments,
			String contactAddress,
			Date dateOfLicence,
			String description){
		Project instance = new Project();
		instance.setCode(license);
		instance.setName(name);
		instance.setIssueDate(issueDate);
		instance.setInvestor(investor);
		instance.setInvestmentModel(investmentModel);
		instance.setInvestorCountry(investorCountry);
		instance.setBusinessDomain(businessDomain);
		instance.setInvestmentCapital(investmentCapital);
		instance.setCharteredCapital(charteredCapital);
		instance.setImplementAddress(implementAddress);
		instance.setImplementDuration(implementDuration);
		instance.setImplementDueDate(implementDueDate);
		instance.setImplementComments(implementComments);
		instance.setContactAddress(contactAddress);
		instance.setDateOfLicence(dateOfLicence);
		instance.setDescription(description);

		return instance;
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
}
