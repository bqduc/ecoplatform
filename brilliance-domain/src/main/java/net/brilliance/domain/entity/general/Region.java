package net.brilliance.domain.entity.general;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * An region or CRX.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "region")
@EqualsAndHashCode(callSuper=false)
public class Region extends BizObjectBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7468751835306200560L;

	@Size(min = 5, max = 200)
	@Column(name = "name", nullable = false, unique=true)
	private String name;

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
