package net.brilliance.common;

public class NumericConverter {
	/** Holds the number 1-19, dual purpose for special cases (teens) **/
	private static final String[] UNITS = { "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín", "mười",
			"mười một", "mười hai", "mười ba", "mười bốn", "mười lăm", "mười  sáu",
			"mười bảy", "mười tám", "mười chín" };
	/** Holds the tens places **/
	private static final String[] TENS = { "mười", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi",
			"bảy mươi", "tám mươi", "chín mươi" };
	/** Covers max value of Long **/
	private static final String[] THOUSANDS = { CommonConstants.STRING_BLANK, "ngàn", "triệu", "tỷ°", "chục tỷ", "trăm tỷ", "ngàn tỷ", "chục ngàn tỷ" };

	private static final String HUNDRED = "trăm";
	private static final String AND = "lẻ";
	private static final String SPECIAL_ONE = "mốt";
	private static final String SPECIAL_FIFTH = "lăm";
	public static final String SPECIAL_END_STATEMENT = "đồng chẵn./.";

	/**
	 * Represents a number <strong class="highlight">in</strong> <strong class="highlight">words</strong> (seven hundred thirty four, two hundred and seven, etc...)
	 * 
	 * The largest number this will accept is
	 * 
	 * <pre>
	 * 999,999,999,999,999,999,999
	 * </pre>
	 * 
	 * but that's okay becasue the largest value of Long is
	 * 
	 * <pre>
	 * 9,223,372,036,854,775,807
	 * </pre>
	 * 
	 * . The smallest number is
	 * 
	 * <pre>
	 * -9,223,372,036,854,775,807
	 * </pre>
	 * 
	 * (Long.MIN_VALUE +1) due <strong class="highlight">to</strong> a limitation of Java.
	 * 
	 * @param number
	 *          between Long.MIN_VALUE and Long.MAX_VALUE
	 * @return the number writen <strong class="highlight">in</strong> <strong class="highlight">words</strong>
	 */
	public String numberInWords(long number) {
		StringBuilder sb = new StringBuilder();
		// Zero is an easy one, but not technically a number :P
		if (number == 0) {
			return "zero";
		}
		// Negative numbers are easy too
		if (number < 0) {
			sb.append("negative ");
			number = Math.abs(number);
		}

		// Log keeps track of which Thousands place we're <strong
		// class="highlight">in</strong>
		long log = 1000000000000000000L, sub = number;
		int i = 7;
		do {
			// This is the 1-999 subset of the current thousand's place
			sub = number / log;
			// Cut down number for the next loop
			number = number % log;
			// Cut down log for the next loop
			log = log / 1000;
			i--; // tracks the big number place
			if (sub != 0) {
				// If this thousandths place is not 0 (that's okay, just skip)
				// tack it on
				sb.append(hundredsInWords((int) sub));
				sb.append(" ");
				sb.append(THOUSANDS[i]);
				if (number != 0) {
					sb.append(" ");
				}
			}
		} while (number != 0);

		return sb.toString();
	}

	/**
	 * Converts a number into hundreds.
	 * 
	 * The basic unit of the American numbering system is "hundreds". Thus 1,024 = (one thousand) twenty four 1,048,576 = (one million) (fourty eight thousand) (five hundred seventy
	 * six) so there's no need <strong class="highlight">to</strong> break it down further than that.
	 * 
	 * @param n
	 *          between 1 and 999
	 * @return
	 */
	protected String hundredsInWords(int n) {
		if (n < 1 || n > 999) {
			throw new AssertionError(n); // Use assersion errors <strong
			// class="highlight">in</strong>
			// private methods only!
		}
		StringBuilder sb = new StringBuilder();

		int tens = n / 10;
		boolean specialRemainder = (tens % 10 == 0);

		// Handle the "hundred", with a special provision for x01-x09
		if (n > 99) {
			sb.append(UNITS[(n / 100) - 1]);
			sb.append(" " + HUNDRED);
			n = n % 100;
			if (n != 0) {
				sb.append(" ");
			}
			if (n < 10) {
				sb.append(AND + " ");
			}
		}

		// Handle the special cases and the tens place at the same time.
		if (n > 19) {
			sb.append(TENS[(n / 10) - 1]);

			n = n % 10;
			if (n != 0) {
				sb.append(" ");
			}
		}

		// This is the ones place
		/*
		 * if (n > 0) { if (n == 1 && tens > 10 && !specialRemainder) { sb.append(SPECIAL_ONE); } else if (n == 5 && !specialRemainder) { sb.append(SPECIAL_FIFTH); } else {
		 * sb.append(UNITS[n - 1]); } }
		 */

		if (n > 0) {
			if (n == 1 && tens > 1 && !specialRemainder) {
				sb.append(SPECIAL_ONE);
			} else if (n == 5 && !specialRemainder) {
				sb.append(SPECIAL_FIFTH);
			} else {
				sb.append(UNITS[n - 1]);
			}
		}

		if (sb.toString().trim().endsWith(AND)) {
			sb.delete(sb.length() - AND.length() - 1, sb.length());
		}

		return sb.toString();
	}
}
