/**
 * 
 */
package net.brilliance.officesuite.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ducbq
 *
 */
public class SpreadsheetDocDataContainer {
	private Map<Object, SpreadsheetContainer> sheetDataContainer = null;

	private SpreadsheetDocDataContainer() {
		this.sheetDataContainer = new HashMap<>();
	}

	public static SpreadsheetDocDataContainer instance() {
		return new SpreadsheetDocDataContainer();
	}

	public SpreadsheetDocDataContainer putSheetData(Object key, SpreadsheetContainer value) {
		this.sheetDataContainer.put(key, value);
		return this;
	}

	public SpreadsheetContainer getSheetData(Object key) {
		if (this.sheetDataContainer.containsKey(key))
			return this.sheetDataContainer.get(key);

		return null;
	}

	public Object pull(Object key) {
		if (this.sheetDataContainer.containsKey(key))
			return this.sheetDataContainer.remove(key);

		return null;
	}

	public Map<Object, SpreadsheetContainer> getSheetDataContainer() {
		return sheetDataContainer;
	}

	public void setSheetDataContainer(Map<Object, SpreadsheetContainer> sheetData) {
		this.sheetDataContainer = sheetData;
	}
}
