/**
 * 
 */
package net.brilliance.dispatch;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

/**
 * @author ducbq
 *
 */
@Component(value="uiUtility")
public class UIUtility {
	/**
	 * Formats a BigInteger to a thousand grouped String
	 * @param number
	 * @return
	 */
	public String formatNumber (BigInteger number) {
		if (null==number)
			return "";

		return String.format("%,d", number);
	}
}
