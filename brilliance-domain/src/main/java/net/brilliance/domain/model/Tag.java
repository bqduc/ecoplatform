/**
 * 
 */
package net.brilliance.domain.model;

/**
 * @author ducbq
 *
 */
public class Tag {
	private int id;
	private String code;
	private String tagName;

	//getter and setter methods

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Tag() {
	}

	public Tag(int id, String code, String tagName) {
		this.id = id;
		this.code = code;
		this.tagName = tagName;
	}
}