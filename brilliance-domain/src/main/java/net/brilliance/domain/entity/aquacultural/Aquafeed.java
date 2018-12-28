/**
 * 
 */
package net.brilliance.domain.entity.aquacultural;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.brilliance.domain.entity.general.Category;
import net.brilliance.domain.entity.general.MeasureUnit;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * Aquafeed. Food for aquafarming
 * @author ducbq
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "aquafeed")
public class Aquafeed extends BizObjectBase {
	@Column(name = "code", unique = true)
	private String code;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "unit_id")
	private MeasureUnit unit;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "comments")
	private String comments;

	public Aquafeed(){
	}

	public Aquafeed(String code, String name){
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MeasureUnit getUnit() {
		return unit;
	}

	public void setUnit(MeasureUnit unit) {
		this.unit = unit;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}