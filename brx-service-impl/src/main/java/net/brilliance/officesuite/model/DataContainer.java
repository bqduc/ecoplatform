/**
 * 
 */
package net.brilliance.officesuite.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ducbq
 *
 */
public class DataContainer {
	private Map<Object, Object> bucketData = null;
	private List<Object> container;

	private DataContainer() {
		this.bucketData = new HashMap<>();
	}

	public DataContainer(Object[] values) {
		this.container = new ArrayList<>();
		for (int idx = 0; idx < values.length; idx++) {
			this.container.add(values[idx]);
		}
	}

	public List<Object> getContainer() {
		return container;
	}

	public void setContainer(List<Object> container) {
		this.container = container;
	}

	public static DataContainer instance() {
		return new DataContainer();
	}

	public Map<Object, Object> getBucketData() {
		return bucketData;
	}

	public void setBucketData(Map<Object, Object> bucketData) {
		this.bucketData = bucketData;
	}

	public DataContainer putAll(Map<Object, Object> values) {
		this.bucketData.putAll(values);
		return this;
	}

	public DataContainer put(Object key, Object value) {
		this.bucketData.put(key, value);
		return this;
	}

	public Object get(Object key) {
		if (this.bucketData.containsKey(key))
			return this.bucketData.get(key);

		return null;
	}

	public Object pull(Object key) {
		if (this.bucketData.containsKey(key))
			return this.bucketData.remove(key);

		return null;
	}
}
