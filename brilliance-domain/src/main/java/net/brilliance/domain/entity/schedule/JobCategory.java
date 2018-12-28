/**
 * 
 */
package net.brilliance.domain.entity.schedule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sys_job_category")
public class JobCategory extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7171709303768216187L;

	@Column(name = "name", length=50, nullable=false, unique=true)
	private String name;

	@Column(name = "info", length=100)
	private String info;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private JobCategory parent;

	public String getName() {
		return name;
	}

	public JobCategory setName(String name) {
		this.name = name;
		return this;
	}

	public String getInfo() {
		return info;
	}

	public JobCategory setInfo(String info) {
		this.info = info;
		return this;
	}

	public static JobCategory getInstance(String name, String info){
		return JobCategory.builder().build()
				.setName(name)
				.setInfo(info);
	}

	public JobCategory getParent() {
		return parent;
	}

	public void setParent(JobCategory parent) {
		this.parent = parent;
	}
}
