/**
 * 
 */
package net.brilliance.domain.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.brilliance.framework.entity.BaseObject;

/**
 * @author ducbq
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "sys_sequence")
public class SystemSequence extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2463829858405513456L;

	@Column(name = "name", length=50)
	private String name;

	@Column(name = "value")
	private Long value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public void increase(long step){
		this.value += step;
	}
}
