/**
 * 
 */
package net.brilliance.officesuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import net.brilliance.common.ListUtility;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.model.Bucket;

/**
 * @author ducbq
 *
 */
public class SpreadsheetXSSFEventBasedExtractorBack {
	public static final String MISSING_ENTRY = "";
	List<List<String>> stringTable = null;
	List<String> sbCurrentRow = null;
	int actualMaxPhysicalCells = 0;
	Integer startedRowIndex = Integer.valueOf(0);

	public static int defaultNumberOfCells = 200;

	/***---------Begin of buffered sheet content handler---------***/
	private class BufferedSheetContentsHandler implements SheetContentsHandler {
		private boolean firstCellOfRow = false;
		private int currentRow = -1;
		private int currentCol = -1;

		private void outputMissingRows(int number) {
			for (int i = 0; i < number; i++) {
				for (int j = 0; j < minColumns; j++) {
					sbCurrentRow.add(MISSING_ENTRY);
				}
			}
		}

		@Override
		public void startRow(int rowNum) {
			//Initialize the 
			sbCurrentRow = ListUtility.createArrayList();

			// If there were gaps, output the missing rows
			outputMissingRows(rowNum - currentRow - 1);
			// Prepare for this row
			firstCellOfRow = true;
			currentRow = rowNum;
			currentCol = -1;
		}

		@Override
		public void endRow(int rowNum) {
			// Ensure the minimum number of columns
			for (int i = currentCol; i < minColumns; i++) {
				sbCurrentRow.add(MISSING_ENTRY);
			}

			if (ListUtility.isNotEmptyList(sbCurrentRow)){
				stringTable.add(sbCurrentRow);
			}
		}

		@Override
		public void cell(String cellReference, String formattedValue, XSSFComment comment) {
			if (firstCellOfRow) {
				firstCellOfRow = false;
			}

			// gracefully handle missing CellRef here in a similar way as XSSFCell does
			if (cellReference == null) {
				cellReference = new CellAddress(currentRow, currentCol).formatAsString();
			}

			// Did we miss any cells?
			int thisCol = (new CellReference(cellReference)).getCol();
			int missedCols = thisCol - currentCol - 1;
			
			//Evaluate actual maximum number of cells
			if (thisCol > actualMaxPhysicalCells){
				actualMaxPhysicalCells = thisCol;
			}

			for (int i = 0; i < missedCols; i++) {
				sbCurrentRow.add(MISSING_ENTRY);
			}
			currentCol = thisCol;

			// Number or string?
			try {
				// noinspection ResultOfMethodCallIgnored
				Double.parseDouble(formattedValue);
				sbCurrentRow.add(formattedValue);
			} catch (NumberFormatException e) {
				sbCurrentRow.add(formattedValue);
			}
		}

		@Override
		public void headerFooter(String text, boolean isHeader, String tagName) {
			// Skip, no headers or footers in CSV
		}
	}
	/***---------End of buffered sheet content handler---------***/

	///////////////////////////////////////

	private final OPCPackage xlsxPackage;

	/**
	 * Number of columns to read starting with leftmost
	 */
	private final int minColumns;

	/**
	 * Creates a new XLSX -> CSV converter
	 *
	 * @param pkg
	 *          The XLSX package to process
	 *          
	 * @param minColumns
	 *          The minimum number of columns to output, or -1 for no minimum
	 */
	public SpreadsheetXSSFEventBasedExtractorBack(OPCPackage pkg, int minColumns) {
		this.xlsxPackage = pkg;
		this.minColumns = minColumns;
	}

	public static SpreadsheetXSSFEventBasedExtractorBack getInstance(InputStream inputStream) throws InvalidFormatException, IOException {
		OPCPackage opcPackage = OPCPackage.open(inputStream);
		return new SpreadsheetXSSFEventBasedExtractorBack(opcPackage, defaultNumberOfCells);
	}

	public static SpreadsheetXSSFEventBasedExtractorBack getInstance(String xlsxFilePath) throws InvalidFormatException, IOException {
		return getInstance(new FileInputStream(xlsxFilePath));
	}

	public static Bucket extractSpreadsheetData(String spreadsheetFilePath, String[] sheets, Map<Object, Object> configParameters) throws EcosysException {
		Bucket bucket = null;
		try {
			bucket = getInstance(spreadsheetFilePath).extractData(sheets, configParameters);
		} catch (Exception e) {
			throw new EcosysException("Extract spreadsheet data error. ", e);
		}
		return bucket;
	}
	/**
	 * Parses and shows the content of one sheet using the specified styles and shared-strings tables.
	 *
	 * @param styles
	 *          The table of styles that may be referenced by cells in the sheet
	 * @param strings
	 *          The table of strings that may be referenced by cells in the sheet
	 * @param sheetInputStream
	 *          The stream to read the sheet-data from.
	 * 
	 * @exception java.io.IOException
	 *              An IO exception from the parser, possibly from a byte stream or character stream supplied by the application.
	 * @throws SAXException
	 *           if parsing the XML data fails.
	 */
	public void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheetHandler, InputStream sheetInputStream)
			throws IOException, SAXException {
		DataFormatter formatter = new DataFormatter();
		InputSource sheetSource = new InputSource(sheetInputStream);
		try {
			this.actualMaxPhysicalCells = 0;
			XMLReader sheetParser = SAXHelper.newXMLReader();
			ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
			sheetParser.setContentHandler(handler);
			sheetParser.parse(sheetSource);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
		}
	}

	public int getActualMaxPhysicalCells() {
		return actualMaxPhysicalCells;
	}

	public Bucket extractData(String[] sheets, Map<Object, Object> configParameters) throws InvalidOperationException, IOException, OpenXML4JException, SAXException{
		return extractData(ListUtility.arraysAsList(sheets), configParameters);
	}

	public Bucket extractData(List<String> sheets, Map<Object, Object> configParameters) throws InvalidOperationException, IOException, OpenXML4JException, SAXException{
		InputStream stream = null;
		Bucket dataBucket = Bucket.getInstance();
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		SheetContentsHandler sheetContentsHandler = new BufferedSheetContentsHandler();
		String rowIndexKey = "";
		while (iter.hasNext()) {
			stream = iter.next();
			if (sheets.contains(iter.getSheetName())){
				this.stringTable = ListUtility.createArrayList();
				//Process the started row index 
				rowIndexKey = iter.getSheetName() + Bucket.PARAM_STARTED_ROW_INDEX;
				if (null != configParameters && configParameters.containsKey(rowIndexKey)){
					this.startedRowIndex = (Integer)configParameters.get(rowIndexKey);
				}

				processSheet(styles, strings, sheetContentsHandler, stream);
				//ListUtility.cleanEmtpty(this.stringTable);
				for (int idx = 0; idx < this.startedRowIndex; ++idx){
					this.stringTable.remove(0);
				}

				dataBucket.put(iter.getSheetName(), this.stringTable);
			}
			stream.close();
		}
		this.xlsxPackage.close();
		return dataBucket;
	}

	public Bucket parseXlsxData() throws InvalidOperationException, IOException, OpenXML4JException, SAXException{
		Bucket dataBucket = Bucket.getInstance();
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		SheetContentsHandler sheetContentsHandler = new BufferedSheetContentsHandler();
		InputStream stream = null;
		String sheetName = null;
		while (iter.hasNext()) {
			this.stringTable = ListUtility.createArrayList();
			stream = iter.next();
			sheetName = iter.getSheetName();
			processSheet(styles, strings, sheetContentsHandler, stream);
			dataBucket.put(sheetName, this.stringTable);
			stream.close();
		}
		this.xlsxPackage.close();
		return dataBucket;
	}

}
