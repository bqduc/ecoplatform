/**
 * 
 */
package net.brilliance.domain.entity.aquacultural;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * Prawn seed (category)
 * @author ducbq
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "seed")
public class Seed extends BizObjectBase {
	@Column(name = "name")
	private String name;

	@Column(name = "comments")
	private String comments;

	/**A place where eggs are hatched under artificial conditions (especially fish eggs)*/
	@Column(name = "hatchery")
	private String hatchery;

	public String getHatchery() {
		return hatchery;
	}

	public void setHatchery(String hatchery) {
		this.hatchery = hatchery;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}