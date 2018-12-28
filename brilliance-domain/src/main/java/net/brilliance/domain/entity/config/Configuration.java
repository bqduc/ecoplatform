/**
 * 
 */
package net.brilliance.domain.entity.config;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@Entity
@Table(name = "configuration")
public class Configuration extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7122460417089959400L;

	@Column(name = "name", length=50, nullable=false, unique=true)
	private String name;

	@Column(name = "value", length=200, nullable=false)
	private String value;

	@Column(name = "value_extended", length=500)
	private String valueExtended;

	@Column(name = "grouped", length=30)
	private String group;

	@Column(name = "info", columnDefinition="TEXT")
	private String info;

	@OneToMany(mappedBy="configuration", cascade = CascadeType.ALL)
	private Set<ConfigurationDetail> configurationDetails = new HashSet<>();

	public String getName() {
		return name;
	}

	public Configuration setName(String name) {
		this.name = name;
		return this;
	}

	public String getValue() {
		return value;
	}

	public Configuration setValue(String value) {
		this.value = value;
		return this;
	}

	public String getGroup() {
		return group;
	}

	public Configuration setGroup(String group) {
		this.group = group;
		return this;
	}

	public String getInfo() {
		return info;
	}

	public Configuration setInfo(String info) {
		this.info = info;
		return this;
	}

	public String getValueExtended() {
		return valueExtended;
	}

	public Configuration setValueExtended(String valueExtended) {
		this.valueExtended = valueExtended;
		return this;
	}

	public Set<ConfigurationDetail> getConfigurationDetails() {
		return configurationDetails;
	}

	public Configuration setConfigurationDetails(Set<ConfigurationDetail> configurationDetails) {
		this.configurationDetails = configurationDetails;
		return this;
	}

	public Configuration addConfigurationDetail(ConfigurationDetail configurationDetail) {
		this.configurationDetails.add(configurationDetail);
		return this;
	}

	public static Configuration getInstance(String name, String value){
		return getInstance(null, name, value, null);
	}

	public static Configuration getInstance(String group, String name, String value, String info){
		return new Configuration()
				.setGroup(group)
				.setName(name)
				.setValue(value)
				.setInfo(info);
	}
}
