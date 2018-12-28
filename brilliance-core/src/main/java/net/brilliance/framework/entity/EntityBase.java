/**
 * 
 */
package net.brilliance.framework.entity;

import java.io.Serializable;

/**
 * @author ducbq
 *
 */
public interface EntityBase extends Comparable<Object>, Serializable {
	Long getId();
	void setId(Long id);
}
