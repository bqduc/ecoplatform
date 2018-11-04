package net.brilliance.domain.entity.general;

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
@Table(name = "reference_book")
public class Book extends BaseObject{
	private static final long serialVersionUID = 1L;

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

	// Book constructor
	public Book(Integer version, String name, String publisher,
			Date dateOfPublication, String description, String photo) {
		this.version = version;
		this.name = name;
		this.publisher = publisher;
		this.dateOfPublication = dateOfPublication;
		this.description = description;
		this.photo = photo;
	}

	// No-arg book constructor.
	public Book() {
	}

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

		Book book = (Book) o;

		if (!Objects.equals(getId(), book.getId()))
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
		return "Book{" + "id=" + getId() + ", version='" + version + "'"
				+ ", name='" + name + "'" + ", publisher='" + publisher + "'"
				+ ", dateOfPublication='" + dateOfPublication + "'"
				+ ", description='" + description + "'" + ", photo='" + photo
				+ "'" + '}';
	}

}
