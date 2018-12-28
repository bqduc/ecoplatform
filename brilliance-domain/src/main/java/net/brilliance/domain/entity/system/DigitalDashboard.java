/**
 * 
 */
package net.brilliance.domain.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * @author ducbq
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "dmx_dashboard")
public class DigitalDashboard extends BizObjectBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7705564912577543433L;

	@Column(name = "serial", length=GlobalConstants.SIZE_SERIAL)
	private String serial;

	@Column(name = "name", length=50)
	private String name;

	@Column(name = "translated_name", length=50)
	private String translatedName;

	@Column(name = "notes", columnDefinition="TEXT")
	private String notes;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private DigitalDashboard parent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public DigitalDashboard getParent() {
		return parent;
	}

	public void setParent(DigitalDashboard parent) {
		this.parent = parent;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getTranslatedName() {
		return translatedName;
	}

	public void setTranslatedName(String translatedName) {
		this.translatedName = translatedName;
	}
}
