package net.brilliance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.general.City;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * An region or CRX.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "brx_station")
@EqualsAndHashCode(callSuper=false)
public class Station extends BizObjectBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6054419044088292754L;

	@Size(min = 5, max = 200)
	@Column(name = "name", nullable = false, unique=true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;
	
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

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
