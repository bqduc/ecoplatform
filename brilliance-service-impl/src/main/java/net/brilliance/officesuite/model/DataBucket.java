package net.brilliance.officesuite.model;
/**
 * 
 *//*
package com.scs.officesuite.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vpx.common.CommonUtility;
import net.vpx.domain.model.OfficeSuiteTarget;

*//**
 * @author ducbq
 *
 *//*
public class Bucket {
	private OfficeSuiteTarget suiteTargeted;
	private Map<Object, Object> bucketData = null;
	private List<Object> container;

	private Bucket() {
		this.bucketData = new HashMap<>();
	}

	public Bucket(Object[] values) {
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

	public static Bucket getInstance() {
		Bucket dataBucket = new Bucket();
		return dataBucket;
	}

	public static Bucket getInstance(OfficeSuiteTarget suiteTargeted) {
		Bucket dataBucket = new Bucket();
		dataBucket.setSuiteTargeted(suiteTargeted);
		return dataBucket;
	}

	public Map<Object, Object> getBucketData() {
		return bucketData;
	}

	public Object getBucketedData(Object key){
		return bucketData.get(key);
	}

	public void setBucketData(Map<Object, Object> bucketData) {
		this.bucketData = bucketData;
	}

	public Bucket putAll(Map<Object, Object> values) {
		this.bucketData.putAll(values);
		return this;
	}

	public Bucket put(Object key, Object value) {
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

	public OfficeSuiteTarget getSuiteTargeted() {
		return suiteTargeted;
	}

	public void setSuiteTargeted(OfficeSuiteTarget suiteTargeted) {
		this.suiteTargeted = suiteTargeted;
	}
}
*/