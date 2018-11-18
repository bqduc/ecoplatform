package net.brilliance.domain.entity.dmx;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import net.brilliance.framework.entity.BaseObject;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A Book.
 */

@Entity
@Table(name = "inventory_entry")
public class InventoryEntry extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7852139035753597611L;

	@Version
	@Column(name = "version")
	private Integer version;

	@Size(min = 5, max=GlobalConstants.SIZE_SERIAL)
	@Column(name = "isbn")
	private String isbn;

	@Size(min = 5, max = 200)
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "publisher", length=150)
	private String publisher;

	@Column(name = "date_of_publication")
	private Date dateOfPublication;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "photo", columnDefinition="TEXT")
	private String photo;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getDateOfPublication() {
		return dateOfPublication;
	}

	public void setDateOfPublication(Date dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		InventoryEntry inventoryEntry = (InventoryEntry) o;

		if (!Objects.equals(getId(), inventoryEntry.getId()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public String toString() {
		return "Inventory entry{" + "id=" + getId() + ", version='" + version + "'"
				+ ", name='" + name + "'" + ", publisher='" + publisher + "'"
				+ ", dateOfPublication='" + dateOfPublication + "'"
				+ ", description='" + description + "'" + ", photo='" + photo
				+ "'" + '}';
	}

}
