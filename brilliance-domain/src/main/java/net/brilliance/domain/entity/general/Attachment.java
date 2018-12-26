package net.brilliance.domain.entity.general;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Builder;
import net.brilliance.framework.entity.BaseObject;

/**
 * A Book.
 */
@Builder
@Entity
@Table(name = "attachment")
public class Attachment extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5803112544828198887L;

	@Size(min = 3, max = 200)
	@Column(name = "name", nullable = false)
	private String name;

	@Lob
  private byte[] data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Attachment book = (Attachment) o;

		if (!Objects.equals(getId(), book.getId()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}
}
