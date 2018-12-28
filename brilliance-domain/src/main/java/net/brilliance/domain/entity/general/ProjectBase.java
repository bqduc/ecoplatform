package net.brilliance.domain.entity.general;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * A Project base.
 */
@Entity
public abstract class ProjectBase extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2653718713854873151L;

	@Column(name = "registration_area")
	private String registrationArea;

	@Column(name = "investment_certificate_number")
	private String investmentCertificateNumber;

	@Column(name = "establishment_date")
	@DateTimeFormat(iso = ISO.DATE, pattern="dd/MM/yyyy")
	private Date establishmentDate;

	@Column(name = "main_business_line", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String mainBusinessLine;

	@Column(name = "other_business_lines", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String otherBusinessLines;

	@Column(name = "registered_capital")
	private BigDecimal registeredCapital;

	public String getRegistrationArea() {
		return registrationArea;
	}

	public void setRegistrationArea(String registrationArea) {
		this.registrationArea = registrationArea;
	}

	public String getInvestmentCertificateNumber() {
		return investmentCertificateNumber;
	}

	public void setInvestmentCertificateNumber(String investmentCertificateNumber) {
		this.investmentCertificateNumber = investmentCertificateNumber;
	}

	public Date getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public String getMainBusinessLine() {
		return mainBusinessLine;
	}

	public void setMainBusinessLine(String mainBusinessLine) {
		this.mainBusinessLine = mainBusinessLine;
	}

	public String getOtherBusinessLines() {
		return otherBusinessLines;
	}

	public void setOtherBusinessLines(String otherBusinessLines) {
		this.otherBusinessLines = otherBusinessLines;
	}

	public BigDecimal getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(BigDecimal registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	
	
}
