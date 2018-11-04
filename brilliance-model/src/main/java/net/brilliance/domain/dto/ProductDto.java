package net.brilliance.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.stock.Product;

/**
 * Data Transfer Object for Contact. It is bound to the UI for validation
 * 
 * @author ducbq
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ProductDto extends BaseDto {
	private String code;

	private String longName;

	private String shortName;

	private Boolean activated = Boolean.FALSE;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public Product createProduct(){
		Product obj = new Product();
		
		return obj;
	}
}
