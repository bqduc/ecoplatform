package net.brilliance.domain.entity.bpm;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.domain.model.bpm.BpmActionMode;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A Book.
 */

@Entity
@Table(name = "bpm_process")
public class BpmProcess extends BizObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3547326291453936976L;

	@ManyToOne
	@JoinColumn(name = "owner_user_id")
	private UserAccount owner;

	@Size(min = 5, max = 200)
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "publisher", length=150)
	private String publisher;

	@Column(name = "started_date")
	private Date startedDate;

	@Column(name = "finished_date")
	private Date finishedDate;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "attachment", columnDefinition="TEXT")
	private String attachment;

  @Column(name="process_action_mode")
  @Enumerated(EnumType.ORDINAL)
  private BpmActionMode actionMode;

	public UserAccount getOwner() {
		return owner;
	}

	public void setOwner(UserAccount owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BpmActionMode getActionMode() {
		return actionMode;
	}

	public void setActionMode(BpmActionMode actionMode) {
		this.actionMode = actionMode;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}

	public Date getFinishedDate() {
		return finishedDate;
	}

	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
}
