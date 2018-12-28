package net.brilliance.framework.entity;

import java.beans.Transient;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ObjectBase implements EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5174493359368640877L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private java.lang.Long id;

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

		ObjectBase other = (ObjectBase) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}

		return true;
	}

	public int compareTo(Object obj) {
		if (obj.hashCode() > hashCode())
			return 1;
		else if (obj.hashCode() < hashCode())
			return -1;

		return 0;
	}

	public String toString() {
		return super.toString();
	}

	@Transient
	public boolean isSelected(Long id) {
		if (null != id) {
			return this.getId().equals(id);
		}
		return false;
	}
}