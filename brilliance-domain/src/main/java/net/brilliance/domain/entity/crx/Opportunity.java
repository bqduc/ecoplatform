package net.brilliance.domain.entity.crx;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.general.Currency;
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

	@Size(min = 5, max = 200)
	@Column(name = "name", nullable = false, unique=true)
	private String name;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
