/**
 * 
 */
package net.brilliance.domain.entity.general;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "unit")
public class MeasureUnit extends BizObjectBase {
	@Column(name = "code", unique = true)
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "comments")
	private String comments;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private MeasureUnit parent;

	public String getCode() {
		return code;
	}

	public MeasureUnit setCode(String code) {
		this.code = code;
		return this;
	}

	public String getName() {
		return name;
	}

	public MeasureUnit setName(String name) {
		this.name = name;
		return this;
	}

	public String getComments() {
		return comments;
	}

	public MeasureUnit setComments(String comments) {
		this.comments = comments;
		return this;
	}

	public MeasureUnit getParent() {
		return parent;
	}

	public void setParent(MeasureUnit parent) {
		this.parent = parent;
	}

	public static MeasureUnit createInstance(String code, String name, String comments){
		return new MeasureUnit()
				.setCode(code)
				.setName(name)
				.setComments(comments);
	}
}
