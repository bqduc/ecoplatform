/**
 * 
 */
package net.brilliance.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ducbq
 * 
 * Global Universal Unique Identifier sequential generator
 * 
 * Encapsulates algorithms and state for generating deterministic sequences.
 * The sequence of numbers generated will always follow the same pattern,
 * regardless of the time, place, or platform.
 */
public class GUUISequenceGenerator {
	private static final long defaultStartedGUUID = 100000000000L;

	private Map<String, AtomicLong> sequentialGUUIdGeneratorMap = null;

	private GUUISequenceGenerator(){
		this.sequentialGUUIdGeneratorMap = new HashMap<>();
	}

	private static class SingletonHelper{
    private static final GUUISequenceGenerator INSTANCE = new GUUISequenceGenerator();
	}

  public static GUUISequenceGenerator getInstance(){
    return SingletonHelper.INSTANCE;
  }

	public void initiatedStartedNumber(Map<String, Long> initializedValues){
		this.sequentialGUUIdGeneratorMap = new HashMap<>();
		for (String key :initializedValues.keySet()){
			this.sequentialGUUIdGeneratorMap.put(key, new AtomicLong(initializedValues.get(key)));
		}
	}

	private Long getNextGloballyUnique(String sequence){
		if (!this.sequentialGUUIdGeneratorMap.containsKey(sequence))
			this.sequentialGUUIdGeneratorMap.put(sequence, new AtomicLong(defaultStartedGUUID));

		return this.sequentialGUUIdGeneratorMap.get(sequence).incrementAndGet();
	}

	public Long nextGUUId(String sequence){
		return this.getNextGloballyUnique(sequence);
	}

	public String nextGUUIdString(String sequence){
		StringBuilder sbGuuId = new StringBuilder(sequence)
				.append(this.getNextGloballyUnique(sequence));
		return sbGuuId.toString();
	}

	public String nextGUUIdString(String sequence, GeneratorAlgorithm generatorAlgorithm){
		StringBuilder sbGuuId = new StringBuilder(sequence)
				.append(this.getNextGloballyUnique(sequence));
		return sbGuuId.toString();
	}

	/**
	 * Returns a globally unique long integer.
	 */
	public static Long nextGloballyUniqueLong(String sequence) {
		return getInstance().getNextGloballyUnique(sequence);
	}

	public static Map<String, Long> getGUUIdMap(){
		Map<String, Long> guuiMap = new HashMap<>();
		GUUISequenceGenerator instance = getInstance();
		for (String sequence :instance.sequentialGUUIdGeneratorMap.keySet()){
			guuiMap.put(sequence, Long.valueOf(instance.sequentialGUUIdGeneratorMap.get(sequence).get()));
		}
		return guuiMap;
	}
}
