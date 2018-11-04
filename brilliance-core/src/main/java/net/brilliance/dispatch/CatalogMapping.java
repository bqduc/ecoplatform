/**
 * 
 */
package net.brilliance.dispatch;

/**
 * @author ducbq
 *
 */
public enum CatalogMapping {
	CODE(0, "Code"),
	NAME(1, "Name"),
	ISSUE_DATE(4, "Issue date"),
	DESCRIPTION(6, "Processing"),
	;

	private String name;
	private int index;

	private CatalogMapping(int idx, String name){
		this.name = name;
		this.index = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
