/**
 * 
 */
package net.brilliance.domain.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@Entity
@Table(name = "configuration_detail")
public class ConfigurationDetail extends BizObjectBase {
	private static final long serialVersionUID = -6404039963892756706L;

	@Column(name = "name", length=50, nullable=false)
	private String name;

	@Column(name = "value", length=200, nullable=false)
	private String value;

	@Column(name = "value_extended", length=200)
	private String valueExtended;

	@Column(name = "info", columnDefinition="TEXT")
	private String info;

	@ManyToOne
	@JoinColumn(name = "configuration_id")
	private Configuration configuration;

	private ConfigurationDetail(){
	}

	public String getName() {
		return name;
	}

	public ConfigurationDetail setName(String name) {
		this.name = name;
		return this;
	}

	public String getValue() {
		return value;
	}

	public ConfigurationDetail setValue(String value) {
		this.value = value;
		return this;
	}

	public String getInfo() {
		return info;
	}

	public ConfigurationDetail setInfo(String info) {
		this.info = info;
		return this;
	}

	public String getValueExtended() {
		return valueExtended;
	}

	public ConfigurationDetail setValueExtended(String valueExtended) {
		this.valueExtended = valueExtended;
		return this;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public ConfigurationDetail setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		return this;
	}

	public static ConfigurationDetail getInstance(){
		return new ConfigurationDetail();
	}

	public static ConfigurationDetail getInstance(Configuration configuration){
		return getInstance()
				.setConfiguration(configuration);
	}

	public static ConfigurationDetail getInstance(Configuration configuration, String name, String value, String valueExtended){
		return getInstance(configuration)
				.setName(name)
				.setValue(value)
				.setValueExtended(valueExtended);
	}
}
