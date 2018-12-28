/**
 * 
 */
package net.brilliance.domain.entity.aquacultural;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "pond")
public class Pond extends BizObjectBase {
	@Column(name = "code", unique = true)
	private String code;

	@Column(name = "location")
	private String location;

	@Column(name = "area")
	private Float area;

	@Column(name = "length")
	private Float length;

	@Column(name = "width")
	private Float width;

	@Column(name = "depth")
	private Float depth;

	@Column(name = "dimensions")
	private String dimensions;

	@Column(name = "comments")
	private String comments;

	@Column(name = "status")
	private String status;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "photo", columnDefinition="TEXT")
	private String photo;

	public Pond(String code, String location, Float area, String status, String comments) {
		super();
		this.code = code;
		this.location = location;
		this.area = area;
		this.comments = comments;
		this.status = status;
	}

	public Pond() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Float getLength() {
		return length;
	}

	public void setLength(Float length) {
		this.length = length;
	}

	public Float getWidth() {
		return width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}

	public Float getDepth() {
		return depth;
	}

	public void setDepth(Float depth) {
		this.depth = depth;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
