/**
 * 
 */
package net.brilliance.domain.entity.audit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.admin.UserAccount;

/**
 * @author ducbq
 *
 */
@Embeddable 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Data
public class AuditLog {
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_user_id")
	private Long createdBy;

	@Column(name="last_modified_date")
	private Date lastModifiedDate;

	@ManyToOne
	@JoinColumn(name="modified_user_id")
	private UserAccount lastModifiedBy;
}
