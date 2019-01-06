/**
 * 
 */
package net.brilliance.officesuite;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import net.brilliance.dispatch.exceptions.SpreadsheetSuiteException;

/**
 * @author ducbq
 *
 */
public class OfficeSuiteUtility {
	private static OfficeSuiteUtility instance = new OfficeSuiteUtility();

	private OfficeSuiteUtility(){
		//Privacy constructor
	}

	public static OfficeSuiteUtility instance(){
		return instance;
	}

	public boolean isOfficeExcel(final InputStream inputStream) throws SpreadsheetSuiteException {
		return isOfficeExcelXlsx(inputStream)||isOfficeExcelXls(inputStream);
	}

	public boolean isOfficeExcelXlsx(final String file) throws SpreadsheetSuiteException {
		try {
			return isOfficeExcelXlsx(new FileInputStream(file));
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
	}

	public boolean isOfficeExcelXlsx(File file) throws SpreadsheetSuiteException {
		try {
			final InputStream testStream = new BufferedInputStream(new FileInputStream(file));
			return DocumentFactoryHelper.hasOOXMLHeader(testStream);
			//return isOfficeExcelXlsx(new FileInputStream(file));
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
	}

	public boolean isOfficeExcelXlsx(final InputStream inputStream) throws SpreadsheetSuiteException {
		try {
			return POIXMLDocument.hasOOXMLHeader(new BufferedInputStream(inputStream));
			//final InputStream testStream = new BufferedInputStream(inputStream);
			//return DocumentFactoryHelper.hasOOXMLHeader(testStream);
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
	}

	public boolean isOfficeExcelXls(File file) throws SpreadsheetSuiteException {
		try {
			return isOfficeExcelXls(new FileInputStream(file));
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
	}

	public boolean isOfficeExcelXls(final String file) throws SpreadsheetSuiteException {
		try {
			return isOfficeExcelXls(new FileInputStream(file));
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
	}

	public boolean isOfficeExcelXls(final InputStream inputStream) throws SpreadsheetSuiteException {
		try {
			return POIFSFileSystem.hasPOIFSHeader(new BufferedInputStream(inputStream));
			//final InputStream testStream = new BufferedInputStream(inputStream);
			//return POIFSFileSystem.hasPOIFSHeader(testStream);
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
	}
}
