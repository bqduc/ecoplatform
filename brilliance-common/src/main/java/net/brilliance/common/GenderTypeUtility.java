/**
 * 
 */
package net.brilliance.common;

import java.util.List;

import net.brilliance.model.GenderType;

/**
 * @author ducbq
 *
 */
public class GenderTypeUtility {
	private final static List<String> femalePatterns = ListUtility.arraysAsList(new String[]{"NU", "NỮ", "FEMALE", "BÀ", "CÔ"});
	private final static List<String> malePatterns = ListUtility.arraysAsList(new String[]{"NAM", "MALE", "ÔNG"});

	public static GenderType getGenderType(String src){
		if (femalePatterns.contains(src.toUpperCase()))
			return GenderType.Female;

		if (malePatterns.contains(src.toUpperCase()))
			return GenderType.Male;

		return GenderType.Unknown;
	}
}
