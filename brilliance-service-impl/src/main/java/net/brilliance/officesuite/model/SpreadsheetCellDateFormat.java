package net.brilliance.officesuite.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public enum SpreadsheetCellDateFormat {
	NONE(-1, new SimpleDateFormat("yyyy/MM/dd")),
	FORMAT_14(14, new SimpleDateFormat("yyyy/M/d")),
	FORMAT_15(15, new SimpleDateFormat("d-MMM-yy", new Locale("en", "EN", "EN"))),
	FORMAT_16(16, new SimpleDateFormat("d-MMM", new Locale("en", "EN", "EN"))),
	FORMAT_17(17, new SimpleDateFormat("MMM-yy", new Locale("en", "EN", "EN"))),
	FORMAT_18(18, new SimpleDateFormat("H:mm a", new Locale("en", "EN", "EN"))),
	FORMAT_19(19, new SimpleDateFormat("H:mm:ss a", new Locale("en", "EN", "EN"))),
	FORMAT_20(20, new SimpleDateFormat("H:mm")),
	FORMAT_21(21, new SimpleDateFormat("H:mm:ss")),
	FORMAT_22(22, new SimpleDateFormat("yyyy/M/d H:mm")),
	FORMAT_30(30, new SimpleDateFormat("M/d/yy"))/*,
	FORMAT_31(31, new SimpleDateFormat("yyyy�NM��d��")),
	FORMAT_32(32, new SimpleDateFormat("H��mm��")),
	FORMAT_33(33, new SimpleDateFormat("H��mm��ss�b")),
	FORMAT_45(45, new SimpleDateFormat("mm:ss")),
	FORMAT_47(47, new SimpleDateFormat("mm:ss.S")),
	FORMAT_55(55, new SimpleDateFormat("yyyy�NM��")),
	FORMAT_56(56, new SimpleDateFormat("M��d��")),
	FORMAT_57(57, new SimpleDateFormat("Gyy.M.d", new Locale("ja", "JP", "JP"))),
	FORMAT_58(58, new SimpleDateFormat("GGGGyy�NM��d��", new Locale("ja", "JP", "JP")))*/;

	private int formatId;
	private DateFormat dateFormat;
	private SpreadsheetCellDateFormat(int format, DateFormat dateFormat) {
		this.formatId = format;
		this.dateFormat = dateFormat;
	}
	public DateFormat getDateFormat() {
		return this.dateFormat;
	}
	public int getFormatNo() {
		return this.formatId;
	}

	public static SpreadsheetCellDateFormat getFormt(int formatId) {
		for (SpreadsheetCellDateFormat elm : SpreadsheetCellDateFormat.values()) {
			if (elm.getFormatNo() == formatId) {
				return elm;
			}
		}
		return NONE;
	}

	public static boolean contains(int formatId) {
		for (SpreadsheetCellDateFormat elm : SpreadsheetCellDateFormat.values()) {
			if (elm.getFormatNo() == formatId) {
				return true;
			}
		}
		return false;
	}
}
