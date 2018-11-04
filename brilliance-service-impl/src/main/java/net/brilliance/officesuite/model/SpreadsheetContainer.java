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
public class SpreadsheetContainer {
	private Map<Object, Object> sheetDataContainer = null;

	private SpreadsheetContainer() {
		this.sheetDataContainer = new HashMap<>();
	}

	public static SpreadsheetContainer instance() {
		return new SpreadsheetContainer();
	}

	public SpreadsheetContainer put(Object key, Object value) {
		this.sheetDataContainer.put(key, value);
		return this;
	}

	public Object get(Object key) {
		if (this.sheetDataContainer.containsKey(key))
			return this.sheetDataContainer.get(key);

		return null;
	}

	public Object pull(Object key) {
		if (this.sheetDataContainer.containsKey(key))
			return this.sheetDataContainer.remove(key);

		return null;
	}

	public Map<Object, Object> getSheetDataContainer() {
		return sheetDataContainer;
	}

	public void setSheetDataContainer(Map<Object, Object> sheetData) {
		this.sheetDataContainer = sheetData;
	}
}
