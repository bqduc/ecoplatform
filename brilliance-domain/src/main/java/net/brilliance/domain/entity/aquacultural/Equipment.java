package net.brilliance.domain.entity.aquacultural;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.brilliance.domain.entity.general.Category;
import net.brilliance.domain.entity.general.MeasureUnit;
import net.brilliance.framework.entity.BizObjectBase;

@SuppressWarnings("serial")
@Entity
@Table(name = "equipment")
public class Equipment extends BizObjectBase{
	@Column(name = "code", nullable = false, unique = true)
	private String code;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "type")
	private String type; //This value is query from inventory dictionary. It can be a normal equipment, live stock feed, ....

	@ManyToOne
	@JoinColumn(name = "unit_id")
	private MeasureUnit unit;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "description")
	private String description;

	public Equipment() {
	}

	public Equipment(String name, String description) {
		this.name = name;
		this.setDescription(description);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Equipment[code=" + code + ", name=" + name + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MeasureUnit getUnit() {
		return unit;
	}

	public void setUnit(MeasureUnit unit) {
		this.unit = unit;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
