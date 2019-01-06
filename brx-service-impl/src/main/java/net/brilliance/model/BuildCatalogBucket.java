/**
 * 
 */
package net.brilliance.model;

/**
 * @author ducbq
 *
 */
public class BuildCatalogBucket {
	private Integer nextIdx = Integer.valueOf(-1);
	private boolean hasNext = false;
	private Object nextObject = null;

	public Integer nextIdx() {
		return nextIdx;
	}
	public BuildCatalogBucket setNextIdx(Integer nextIdx) {
		this.nextIdx = nextIdx;
		return this;
	}
	public boolean hasNext() {
		return this.hasNext;
	}
	public BuildCatalogBucket setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
		return this;
	}
	public Object next() {
		return this.nextObject;
	}
	public BuildCatalogBucket setNextObject(Object nextObject) {
		this.nextObject = nextObject;
		return this;
	}

	public static BuildCatalogBucket getInstance(){
		return new BuildCatalogBucket().setHasNext(Boolean.FALSE);
	}

	public static BuildCatalogBucket getInstance(int nextIdx, Object nextObject){
		return new BuildCatalogBucket()
				.setHasNext(Boolean.TRUE)
				.setNextIdx(nextIdx)
				.setNextObject(nextObject);
	}
}
