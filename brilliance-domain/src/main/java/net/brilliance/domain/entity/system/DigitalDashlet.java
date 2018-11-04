/**
 * 
 */
package net.brilliance.domain.entity.system;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.admin.Authority;
import net.brilliance.framework.entity.BaseObject;
import net.brilliance.framework.global.GlobalConstants;

/**
 * @author ducbq
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "dmx_dashlet")
public class DigitalDashlet extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8408523530919438474L;

	@Column(name = "serial", length=GlobalConstants.SIZE_SERIAL)
	private String serial;

	@Column(name = "name", length=50)
	private String name;

	@Column(name = "translated_name", length=50)
	private String translatedName;

	@Column(name = "access_uri", length=50)
	private String accessURI;

	@Column(name = "access_role", length=20)
	private String accessRole;

	@ManyToOne
	@JoinColumn(name = "dashboard_id")
	private DigitalDashboard dashboard;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private DigitalDashlet parent;

	@Column(name = "notes", columnDefinition="TEXT")
	private String notes;

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	//@formatter:off
	@JoinTable(
			name = "dmx_dashlet_authority_granted", 
			inverseJoinColumns = {@JoinColumn(name = "authority_id")},
			joinColumns = {@JoinColumn(name = "dashlet_id")}
	)
	//@formatter:on
	private Set<Authority> accessedAuthorities;

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

	public String getAccessURI() {
		return accessURI;
	}

	public void setAccessURI(String accessURI) {
		this.accessURI = accessURI;
	}

	public DigitalDashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(DigitalDashboard dashboard) {
		this.dashboard = dashboard;
	}

	public DigitalDashlet getParent() {
		return parent;
	}

	public void setParent(DigitalDashlet parent) {
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

	public String getAccessRole() {
		return accessRole;
	}

	public void setAccessRole(String accessRole) {
		this.accessRole = accessRole;
	}

	public Set<Authority> getAccessedAuthorities() {
		return accessedAuthorities;
	}

	public void setAccessedAuthorities(Set<Authority> accessedAuthorities) {
		this.accessedAuthorities = accessedAuthorities;
	}
}
