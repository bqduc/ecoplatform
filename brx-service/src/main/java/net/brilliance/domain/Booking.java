package net.brilliance.domain;

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
import net.brilliance.domain.model.GeneralStatus;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A Campaign or CRX.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "brx_booking")
@EqualsAndHashCode(callSuper=false)
public class Booking extends BizObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2868315943151901632L;

	@ManyToOne(targetEntity=Contact.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "passenger_id", insertable=false, updatable=false)
	private Contact passenger;

	@ManyToOne(targetEntity=UserAccount.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	private UserAccount assignedTo;

	@Column(name="status")
  @Enumerated(EnumType.ORDINAL)
	private GeneralStatus status;

	@ManyToOne(targetEntity=Route.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "Route_id", insertable=false, updatable=false)
	private Route route;

  @Column(name="departure_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date departureDate;

  @Column(name="return_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date returnDate;

	@ManyToOne(targetEntity=BusinessPackage.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "business_package_id", insertable=false, updatable=false)
	private BusinessPackage businessPackage;

	@Digits(integer=12, fraction=2)
	@Column(name = "sub_total")
	private BigDecimal subTotal;

	@Digits(integer=12, fraction=2)
	@Column(name = "tax_1")
	private BigDecimal tax1;

	@Digits(integer=12, fraction=2)
	@Column(name = "tax_2")
	private BigDecimal tax2;

	@Digits(integer=12, fraction=2)
	@Column(name = "tax_3")
	private BigDecimal tax3;

	@Digits(integer=12, fraction=2)
	@Column(name = "total")
	private BigDecimal total;

	@Digits(integer=12, fraction=2)
	@Column(name = "deposit")
	private BigDecimal deposit;

	@Column(name = "payment_method", length=50)
	private String paymentMethod;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

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

	public Contact getPassenger() {
		return passenger;
	}

	public void setPassenger(Contact passenger) {
		this.passenger = passenger;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public BusinessPackage getBusinessPackage() {
		return businessPackage;
	}

	public void setBusinessPackage(BusinessPackage businessPackage) {
		this.businessPackage = businessPackage;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTax1() {
		return tax1;
	}

	public void setTax1(BigDecimal tax1) {
		this.tax1 = tax1;
	}

	public BigDecimal getTax2() {
		return tax2;
	}

	public void setTax2(BigDecimal tax2) {
		this.tax2 = tax2;
	}

	public BigDecimal getTax3() {
		return tax3;
	}

	public void setTax3(BigDecimal tax3) {
		this.tax3 = tax3;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
