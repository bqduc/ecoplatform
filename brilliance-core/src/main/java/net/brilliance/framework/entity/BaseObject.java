package net.brilliance.framework.entity;

import java.beans.Transient;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseObject implements BaseEntity {
	private static final long serialVersionUID = -6323358535657100144L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private java.lang.Long id;

	@Column(name="activated")
	private java.lang.Boolean activated = Boolean.FALSE;

	/**
	 * Set/Get the value related to the column: VISIBLE
	 */
	@Column(name = "visible")
	private java.lang.Boolean visible = false;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="ID"
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *          the new ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public java.lang.Boolean isVisible() {
		return visible;
	}

	public void setVisible(java.lang.Boolean visible) {
		this.visible = visible;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		BaseObject other = (BaseObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;

		} else if (!id.equals(other.id))
			return false;

		return true;
	}

	public int compareTo(Object obj) {
		if (obj.hashCode() > hashCode())
			return 1;
		else if (obj.hashCode() < hashCode())
			return -1;
		else
			return 0;
	}

	public String toString() {
		return super.toString();
	}

	public Boolean isActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	@Transient
	public boolean isSelected(Long id){
		if (null != id){
			return this.getId().equals(id);
		}
		return false;
	}

	public static BaseObject buildObject(List<String> data) {
		return null;
	}
}