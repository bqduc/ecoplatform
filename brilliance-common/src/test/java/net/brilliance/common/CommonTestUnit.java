/**
 * 
 */
package net.brilliance.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ducbq
 *
 */
public class CommonTestUnit {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommonTestUnit instance = new CommonTestUnit();
		instance.testNumericPattern();
	}

	protected void testBrillianceHashMap() {
		BrillianceHashMap<String, Object> brillianceMap = new BrillianceHashMap<>();
		brillianceMap
		.doPut("", "")
		.doPut("", "")
		;
		
		
	}
	
	protected void testNumericPattern(){
		String s = new String("Str87uyuy232");
		Matcher matcher = Pattern.compile("\\d+").matcher(s);
		matcher.find();
		int i = Integer.valueOf(matcher.group());
		System.out.println(i);
		
		s = new String("INC100000000000");
		matcher = Pattern.compile("\\d+").matcher(s);
		matcher.find();
		long l = Long.valueOf(matcher.group());
		System.out.println(l);
	}

	
	protected void testUniqueSequence(){
		for (int i = 0; i < 100; i++){
			System.out.println(SequenceGenerator.nextGloballyUniqueLong());
		}
		System.out.println("--------------------------------------------");
		for (int i = 0; i < 100; i++){
			System.out.println(SequenceGenerator.nextGloballyUniqueLong());
		}
	}
	
	protected void testGUUIGenerator(){
		String seqInc = "INC";
		String seqCrq = "CRQ";
		String seqPrb = "PRB";
		for (int i =0; i < 5; ++i){
			System.out.println(GUUISequenceGenerator.nextGloballyUniqueLong(seqInc));
		}
		System.out.println("----------------------------------------------------");
		for (int i =0; i < 5; ++i){
			System.out.println(GUUISequenceGenerator.nextGloballyUniqueLong(seqCrq));
		}
		System.out.println("----------------------------------------------------");
		for (int i =0; i < 5; ++i){
			System.out.println(GUUISequenceGenerator.nextGloballyUniqueLong(seqPrb));
		}
		System.out.println("====================================================");
		for (int i =0; i < 5; ++i){
			System.out.println(GUUISequenceGenerator.nextGloballyUniqueLong(seqInc));
		}
		System.out.println("----------------------------------------------------");
		for (int i =0; i < 5; ++i){
			System.out.println(GUUISequenceGenerator.nextGloballyUniqueLong(seqCrq));
		}
		System.out.println("----------------------------------------------------");
		for (int i =0; i < 5; ++i){
			System.out.println(GUUISequenceGenerator.nextGloballyUniqueLong(seqPrb));
		}
	}
}
