/**
 * 
 */
package net.brilliance.domain.model;

/**
 * @author BDQ1HC
 *
 */
public enum DefaultAccessRightAction {
	View("View", "View single object description"),
	Create("CreateNew", "Create a new object description"),
	Modify("ModifyExisting", "Modify an existing object in application"), 
	Delete("DeleteExisting", "Delete an existing object in application"), 
	ViewList("ViewList", "View objects in application"),
	ExportToPdf("ExportToPdf", "Export objects to pdf file"),
	ExportToExcel("ExportToExcel", "Export objects to Excel file"),
	ExportToCsv("ExportToCsv", "Export objects to csv file. ")
	;

	private String action;
	private String description;

	public String getAction() {
		return action;
	}

	public String getDescription() {
		return description;
	}

	private DefaultAccessRightAction(String action) {
		this.action = action;
	}

	private DefaultAccessRightAction(String action, String description) {
		this.action = action;
		this.description = description;
	}
}
