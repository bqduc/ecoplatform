/**
 * 
 */
package net.brilliance.framework.entity;

import java.io.Serializable;

/**
 * @author ducbq
 *
 */
public interface BaseEntity extends Comparable<Object>, Serializable {
	Long getId();
	void setId(Long id);

	Boolean isVisible();
	void setVisible(Boolean visible);
}
