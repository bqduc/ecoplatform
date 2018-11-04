/**
 * 
 */
package net.brilliance.common;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ducbq
 * 
 * Encapsulates algorithms and state for generating deterministic sequences.
 * The sequence of numbers generated will always follow the same pattern,
 * regardless of the time, place, or platform.
 */
public class SequenceGenerator {
	private static AtomicLong numberGenerator = new AtomicLong(910000000000L);
	/**
	 * Returns a globally unique long integer.
	 */
	public static long nextGloballyUniqueLong() {
		return numberGenerator.getAndIncrement();
	}

	public static long randomGloballyUnique(){
		return ThreadLocalRandom.current().nextLong( 910000000001L, 910999999999L);
	}
}
