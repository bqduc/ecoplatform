/**
 * 
 */
package net.brilliance.domain.entity.system;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.admin.Authority;
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
@Table(name = "dmx_dashlet")
public class DigitalDashlet extends BizObjectBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8408523530919438474L;

	@Column(name = "serial", length=GlobalConstants.SIZE_SERIAL)
	private String serial;

	@Column(name = "name", length=50)
	private String name;

	@Column(name = "display_link", length=50)
	private String displayLink;

	@Column(name = "translated_name", length=50)
	private String translatedName;

	@Builder.Default
	@Column(name = "ui_box_class", length=50)
	private String uiBoxClass = "small-box bg-light-blue";

	@Builder.Default
	@Column(name = "ui_box_data_class", length=50)
	private String uiDataViewClass = "inner";

	@Builder.Default
	@Column(name = "ui_box_inner_data_class", length=50)
	private String uiInnerDataViewClass = "h3";

	@Builder.Default
	@Column(name = "ui_box_inner_data_detail_class", length=50)
	private String uiInnerDataDetailViewClass = "p";

	@Builder.Default
	@Column(name = "ui_icon_class", length=50)
	private String uiIconClass = "icon";

	@Builder.Default
	@Column(name = "ui_icon_data_class", length=50)
	private String uiIconDataClass = "ion ion-ribbon-b";

	@Builder.Default
	@Column(name = "ui_link_class", length=50)
	private String uiLinkClass = "small-box-footer";

	@Builder.Default
	@Column(name = "ui_link_data_class", length=50)
	private String uiLinkDataClass = "fa fa-arrow-circle-right";

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

	@Column(name = "inner_data", length=50)
	private String innerData;

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

	public String getUiBoxClass() {
		return uiBoxClass;
	}

	public void setUiBoxClass(String uiBoxClass) {
		this.uiBoxClass = uiBoxClass;
	}

	public String getUiDataViewClass() {
		return uiDataViewClass;
	}

	public void setUiDataViewClass(String uiDataViewClass) {
		this.uiDataViewClass = uiDataViewClass;
	}

	public String getUiIconClass() {
		return uiIconClass;
	}

	public void setUiIconClass(String uiIconClass) {
		this.uiIconClass = uiIconClass;
	}

	public String getUiIconDataClass() {
		return uiIconDataClass;
	}

	public void setUiIconDataClass(String uiIconDataClass) {
		this.uiIconDataClass = uiIconDataClass;
	}

	public String getUiLinkClass() {
		return uiLinkClass;
	}

	public void setUiLinkClass(String uiLinkClass) {
		this.uiLinkClass = uiLinkClass;
	}

	public String getUiLinkDataClass() {
		return uiLinkDataClass;
	}

	public void setUiLinkDataClass(String uiLinkDataClass) {
		this.uiLinkDataClass = uiLinkDataClass;
	}

	public String getDisplayLink() {
		return displayLink;
	}

	public void setDisplayLink(String displayLink) {
		this.displayLink = displayLink;
	}

	public String getUiInnerDataViewClass() {
		return uiInnerDataViewClass;
	}

	public void setUiInnerDataViewClass(String uiInnerDataViewClass) {
		this.uiInnerDataViewClass = uiInnerDataViewClass;
	}

	public String getInnerData() {
		return innerData;
	}

	public void setInnerData(String innerData) {
		this.innerData = innerData;
	}

	public String getUiInnerDataDetailViewClass() {
		return uiInnerDataDetailViewClass;
	}

	public void setUiInnerDataDetailViewClass(String uiInnerDataDetailViewClass) {
		this.uiInnerDataDetailViewClass = uiInnerDataDetailViewClass;
	}
}
