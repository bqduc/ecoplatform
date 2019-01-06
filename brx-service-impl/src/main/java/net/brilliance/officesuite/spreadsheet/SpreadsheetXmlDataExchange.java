/**
 * 
 */
package net.brilliance.officesuite.spreadsheet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

import net.brilliance.common.CommonUtility;
import net.brilliance.dispatch.exceptions.SpreadsheetSuiteException;
import net.brilliance.model.OfficeSuiteTarget;
import net.brilliance.officesuite.OfficeExcelUtility;
import net.brilliance.officesuite.OfficeSuiteUtility;
import net.brilliance.officesuite.model.DataContainer;
import net.brilliance.officesuite.model.SpreadsheetContainer;
import net.brilliance.officesuite.model.SpreadsheetDocDataContainer;

/**
 * @author ducbq
 *
 */
public class SpreadsheetXmlDataExchange {
	List<Object> sheetRowsData = new ArrayList<>();
	List<String> currentRowData;

	//DataSuiteChunk currentExcelSuiteEntity;
	//ExcelSheetEntity excelSheetEntity = ExcelSheetEntity.instance();

	private class BufferedSheetContentsHandler implements SheetContentsHandler {
		private boolean firstCellOfRow = false;
		private int currentRow = -1;
		private int currentCol = -1;

		private void outputMissingRows(int number) {
			for (int i = 0; i < number; i++) {
				for (int j = 0; j < minColumns; j++) {
					currentRowData.add(CommonUtility.STRING_BLANK);
				}
				//sbCurrentRow.append(ExcelSuiteConstants.NEW_LINE /*'\n'*/);
			}
		}

		@Override
		public void startRow(int rowNum) {
			//Initialize the 
			currentRowData = new ArrayList<>();

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
				currentRowData.add(CommonUtility.STRING_BLANK);
			}
			sheetRowsData.add(currentRowData);

			//excelSheetEntity.addEntity(new DataSuiteChunk(currentRowData));
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
			for (int i = 0; i < missedCols; i++) {
				currentRowData.add(CommonUtility.STRING_BLANK);
			}
			currentCol = thisCol;

			// Number or string?
			try {
				// noinspection ResultOfMethodCallIgnored
				Double.parseDouble(formattedValue);
				currentRowData.add(formattedValue);
			} catch (NumberFormatException e) {
				currentRowData.add(formattedValue);
			}
		}

		@Override
		public void headerFooter(String text, boolean isHeader, String tagName) {
			// Skip, no headers or footers in CSV
		}
	}
	/***---------End of buffered sheet content handler---------***/

	/**
	 * Number of columns to read starting with leftmost
	 */
	private int minColumns;

	/**
	 * Creates a new XLSX office suite converter
	 *
	 * @param pkg
	 *          The XLSX package to process
	 * @param minColumns
	 *          The minimum number of columns to output, or -1 for no minimum
	 */
	private SpreadsheetXmlDataExchange() {
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
	private void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheetHandler, InputStream sheetInputStream)
			throws IOException, SAXException {
		DataFormatter formatter = null;
		InputSource sheetSource = null;
		XMLReader sheetParser = null;
		ContentHandler handler = null;
		try {
			sheetParser = SAXHelper.newXMLReader();
			formatter = new DataFormatter();
			sheetSource = new InputSource(sheetInputStream);
			handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
			sheetParser.setContentHandler(handler);
			sheetParser.parse(sheetSource);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
		}
	}

	/**
	 * Initiates the processing of the XLS workbook file to buffer of strings.
	 *
	 * @throws IOException
	 *           If reading the data from the package fails.
	 * @throws SAXException
	 *           if parsing the XML data fails.
	 */
	private DataContainer process(File file, final List<String> sheetNames, final OfficeSuiteTarget officeSuiteTarget, int minColumns) throws IOException, OpenXML4JException, SAXException {
		DataContainer dataContainer = DataContainer.instance();
		OPCPackage xlsxPackage = null;
		InputStream stream = null;
		SheetContentsHandler sheetContentsHandler = null;
		ReadOnlySharedStringsTable strings = null;
		XSSFReader xssfReader = null;
		StylesTable styles = null;
		XSSFReader.SheetIterator sheetIterator = null;
		List<String> processingSheetNames = new ArrayList<>();
		try {
			this.minColumns = minColumns;
			xlsxPackage = OPCPackage.open(file, PackageAccess.READ);
			strings = new ReadOnlySharedStringsTable(xlsxPackage);
			xssfReader = new XSSFReader(xlsxPackage);
			styles = xssfReader.getStylesTable();
			sheetIterator = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
			sheetContentsHandler = new BufferedSheetContentsHandler();
			//Build processing sheet names
			if (CommonUtility.isNotEmpty(sheetNames)) {
				processingSheetNames.addAll(sheetNames);
			}

			if (processingSheetNames.isEmpty()) {
				while (sheetIterator.hasNext()) {
					stream = sheetIterator.next();

					this.sheetRowsData.clear();
					//this.excelSheetEntity.clear();
					processSheet(styles, strings, sheetContentsHandler, stream);

					dataContainer.put(sheetIterator.getSheetName(), this.sheetRowsData);
					//dataContainer.put(sheetIterator.getSheetName(), this.excelSheetEntity);
					stream.close();
				}
			} else {
				// Get data of all sheets and make each sheet as an element of the map
				while (sheetIterator.hasNext()) {
					stream = sheetIterator.next();
					if (!processingSheetNames.contains(sheetIterator.getSheetName()))
						continue;

					this.sheetRowsData.clear();
					//this.excelSheetEntity.clear();
					processSheet(styles, strings, sheetContentsHandler, stream);

					dataContainer.put(sheetIterator.getSheetName(), new ArrayList<>(this.sheetRowsData));
					//dataContainer.put(sheetIterator.getSheetName(), new ArrayList<DataSuiteChunk>(this.excelSheetEntity.getContainer()));
					stream.close();
					break;
				}
			}
		} finally {
			xlsxPackage.close();
		}
		return dataContainer;
	}

	/**
	 * Initiates the processing of the XLS workbook file to buffer of strings.
	 *
	 * @throws IOException
	 *           If reading the data from the package fails.
	 * @throws SAXException
	 *           if parsing the XML data fails.
	 */
	private DataContainer process(final String file, final List<String> sheetNames, final OfficeSuiteTarget officeSuiteTarget, int minColumns) throws IOException, OpenXML4JException, SAXException {
		DataContainer dataContainer = DataContainer.instance();
		OPCPackage xlsxPackage = null;
		InputStream stream = null;
		SheetContentsHandler sheetContentsHandler = null;
		ReadOnlySharedStringsTable strings = null;
		XSSFReader xssfReader = null;
		StylesTable styles = null;
		XSSFReader.SheetIterator sheetIterator = null;
		List<String> processingSheetNames = new ArrayList<>();
		try {
			this.minColumns = minColumns;
			xlsxPackage = OPCPackage.open(file, PackageAccess.READ);
			strings = new ReadOnlySharedStringsTable(xlsxPackage);
			xssfReader = new XSSFReader(xlsxPackage);
			styles = xssfReader.getStylesTable();
			sheetIterator = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
			sheetContentsHandler = new BufferedSheetContentsHandler();
			//Build processing sheet names
			if (CommonUtility.isNotEmpty(sheetNames)) {
				processingSheetNames.addAll(sheetNames);
			}

			if (processingSheetNames.isEmpty()) {
				while (sheetIterator.hasNext()) {
					stream = sheetIterator.next();

					this.sheetRowsData.clear();
					//this.excelSheetEntity.clear();
					processSheet(styles, strings, sheetContentsHandler, stream);

					dataContainer.put(sheetIterator.getSheetName(), this.sheetRowsData);
					//dataContainer.put(sheetIterator.getSheetName(), this.excelSheetEntity);
					stream.close();
				}
			} else {
				// Get data of all sheets and make each sheet as an element of the map
				while (sheetIterator.hasNext()) {
					stream = sheetIterator.next();
					if (!processingSheetNames.contains(sheetIterator.getSheetName()))
						continue;

					this.sheetRowsData.clear();
					//this.excelSheetEntity.clear();
					processSheet(styles, strings, sheetContentsHandler, stream);

					dataContainer.put(sheetIterator.getSheetName(), new ArrayList<>(this.sheetRowsData));
					//dataContainer.put(sheetIterator.getSheetName(), new ArrayList<DataSuiteChunk>(this.excelSheetEntity.getContainer()));
					stream.close();
					break;
				}
			}
		} finally {
			xlsxPackage.close();
		}
		return dataContainer;
	}

	/**
	 * Initiates the processing of the XLS workbook file to buffer of strings.
	 *
	 * @throws IOException
	 *           If reading the data from the package fails.
	 * @throws SAXException
	 *           if parsing the XML data fails.
	 */
	private DataContainer process(InputStream inputStream, final List<String> sheetNames, final OfficeSuiteTarget officeSuiteTarget, int minColumns) throws IOException, OpenXML4JException, SAXException {
		DataContainer dataContainer = DataContainer.instance();
		OPCPackage xlsxPackage = null;
		InputStream stream = null;
		SheetContentsHandler sheetContentsHandler = null;
		ReadOnlySharedStringsTable strings = null;
		XSSFReader xssfReader = null;
		StylesTable styles = null;
		XSSFReader.SheetIterator sheetIterator = null;
		List<String> processingSheetNames = new ArrayList<>();
		try {
			this.minColumns = minColumns;
			xlsxPackage = OPCPackage.open(inputStream);
			strings = new ReadOnlySharedStringsTable(xlsxPackage);
			xssfReader = new XSSFReader(xlsxPackage);
			styles = xssfReader.getStylesTable();
			sheetIterator = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
			sheetContentsHandler = new BufferedSheetContentsHandler();
			//Build processing sheet names
			if (CommonUtility.isNotEmpty(sheetNames)) {
				processingSheetNames.addAll(sheetNames);
			}

			if (processingSheetNames.isEmpty()) {
				while (sheetIterator.hasNext()) {
					stream = sheetIterator.next();

					this.sheetRowsData.clear();
					//this.excelSheetEntity.clear();
					processSheet(styles, strings, sheetContentsHandler, stream);

					dataContainer.put(sheetIterator.getSheetName(), this.sheetRowsData);
					//dataContainer.put(sheetIterator.getSheetName(), this.excelSheetEntity);
					stream.close();
				}
			} else {
				// Get data of all sheets and make each sheet as an element of the map
				while (sheetIterator.hasNext()) {
					stream = sheetIterator.next();
					if (!processingSheetNames.contains(sheetIterator.getSheetName()))
						continue;

					this.sheetRowsData.clear();
					//this.excelSheetEntity.clear();
					processSheet(styles, strings, sheetContentsHandler, stream);

					dataContainer.put(sheetIterator.getSheetName(), new ArrayList<>(this.sheetRowsData));
					//dataContainer.put(sheetIterator.getSheetName(), new ArrayList<DataSuiteChunk>(this.excelSheetEntity.getContainer()));
					stream.close();
					break;
				}
			}
		} finally {
			if (null != xlsxPackage) {
				xlsxPackage.close();
			}
		}
		return dataContainer;
	}

	public DataContainer fetch(final String file, final List<String> sheetNames) throws SpreadsheetSuiteException{
		DataContainer officeSuiteObject = null;
		try {
			if (OfficeSuiteUtility.instance().isOfficeExcelXlsx(file)) {
				officeSuiteObject = this.process(file, sheetNames, OfficeSuiteTarget.StringTable, -1);
			}
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
		return officeSuiteObject;
	}

	public DataContainer fetch(File file, final List<String> sheetNames) throws SpreadsheetSuiteException{
		DataContainer officeSuiteObject = null;
		try {
			if (OfficeSuiteUtility.instance().isOfficeExcelXlsx(file)) {
				officeSuiteObject = this.process(file, sheetNames, OfficeSuiteTarget.StringTable, -1);
			}
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
		return officeSuiteObject;
	}

	public DataContainer fetch(InputStream inputStream, final List<String> sheetNames) throws SpreadsheetSuiteException{
		DataContainer officeSuiteObject = null;
		try {
			officeSuiteObject = this.process(inputStream, sheetNames, OfficeSuiteTarget.StringTable, -1);
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
		return officeSuiteObject;
	}

	private SpreadsheetContainer processSheet(Sheet sheet) throws SpreadsheetSuiteException {
		SpreadsheetContainer excelSheetContainer = SpreadsheetContainer.instance();
		//Put each row into a list and put them into a container
		Iterator<Row> rowIterator = null;
		Row currRow = null;
		Object cellValue = null;
		OfficeExcelUtility officeExcelUtility = OfficeExcelUtility.instance();
		List<Object> sheetRowsData = new ArrayList<>();
		List<Object> currentRowData;
		int physicalColumns = 0;
		int idx;
		try {
			rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				currRow = rowIterator.next();
				if (physicalColumns < currRow.getLastCellNum()) {
					physicalColumns = currRow.getLastCellNum();
				}

				currentRowData = new ArrayList<>();
				for (idx = 0; idx < physicalColumns; ++idx) {
					cellValue = officeExcelUtility.getValue(currRow.getCell(idx));
					currentRowData.add(cellValue);
				}
				sheetRowsData.add(currentRowData);
			}

			//Handle missing cells for each row
			handleMissingCells(physicalColumns, sheetRowsData);
			for (idx = 0; idx < sheetRowsData.size(); ++idx) {
				excelSheetContainer.put(Integer.valueOf(idx), sheetRowsData.get(idx));
			}
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
		return excelSheetContainer;
	}

	private void handleMissingCells(int physicalCells, List<Object> sheetRowsData) {
		List<Object> cells = null;
		int missingCells, i;
		for (Object row :sheetRowsData) {
			cells = (List<Object>)row;
			if (cells.size() < physicalCells) {
				missingCells = physicalCells - cells.size();
				for (i = 0; i < missingCells; i++) {
					cells.add(CommonUtility.EMPTY_DATA);
				}
			}
		}
	}

	public SpreadsheetDocDataContainer fetchByStream(InputStream inputStream, final List<String> sheetNames) throws SpreadsheetSuiteException{
		SpreadsheetDocDataContainer excelDocDataContainer = SpreadsheetDocDataContainer.instance();
		SpreadsheetContainer sheetData = null;
		Workbook workbook = null;
		Iterator<Sheet> sheetIterator = null;
		Sheet sheet = null;
		try {
			workbook = StreamingReader.builder().open(inputStream);
			sheetIterator = workbook.sheetIterator();
			if (CommonUtility.isNotEmpty(sheetNames)) {
				while (sheetIterator.hasNext()) {
					sheet = sheetIterator.next();
					if (!sheetNames.contains(sheet.getSheetName()))
						continue;

					sheetData = processSheet(sheet);
					if (CommonUtility.isNotEmpty(sheetData)) {
						excelDocDataContainer.putSheetData(sheet.getSheetName(), sheetData);
					}
				}
			}else {
				while (sheetIterator.hasNext()) {
					sheet = sheetIterator.next();
					sheetData = processSheet(sheet);
					if (CommonUtility.isNotEmpty(sheetData)) {
						excelDocDataContainer.putSheetData(sheet.getSheetName(), sheetData);
					}
				}
			}
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
		return excelDocDataContainer;
	}

	public static SpreadsheetXmlDataExchange instance(){
		return instance;
	}

	private static SpreadsheetXmlDataExchange instance = new SpreadsheetXmlDataExchange();
}
