package net.brilliance.officesuite.spreadsheet;
/**
 * 
 *//*
package com.scs.officesuite.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

import com.scs.officesuite.model.Bucket;

import net.vpx.common.CommonUtility;
import net.vpx.domain.entity.vbb.Forum;
import net.vpx.domain.entity.vbb.Post;
import net.vpx.domain.entity.vbb.Topic;

*//**
 * @author ducbq
 *
 *//*
public class Xlsx2StringTableParser {
	public static final String MISSING_ENTRY = "";
	//Map<Object, List<String>> buffer = null;
	List<List<String>> stringTable = null;
	List<String> sbCurrentRow = null;
	int actualMaxPhysicalCells = 0;

	public static int defaultNumberOfCells = 100;

	*//***---------Begin of buffered sheet content handler---------***//*
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
			sbCurrentRow = new ArrayList<>();

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
			stringTable.add(sbCurrentRow);
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
	*//***---------End of buffered sheet content handler---------***//*

	///////////////////////////////////////

	private final OPCPackage xlsxPackage;

	*//**
	 * Number of columns to read starting with leftmost
	 *//*
	private final int minColumns;

	*//**
	 * Creates a new XLSX -> CSV converter
	 *
	 * @param pkg
	 *          The XLSX package to process
	 *          
	 * @param minColumns
	 *          The minimum number of columns to output, or -1 for no minimum
	 *//*
	public Xlsx2StringTableParser(OPCPackage pkg, int minColumns) {
		this.xlsxPackage = pkg;
		this.minColumns = minColumns;
	}

	public static Xlsx2StringTableParser getInstance(InputStream inputStream) throws InvalidFormatException, IOException {
		OPCPackage opcPackage = OPCPackage.open(inputStream);		
		Xlsx2StringTableParser xlsx2csv = new Xlsx2StringTableParser(opcPackage, defaultNumberOfCells);
		return xlsx2csv;
	}

	*//**
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
	 *//*
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

	*//**
	 * Initiates the processing of the XLS workbook file to string table.
	 *
	 * @throws IOException
	 *           If reading the data from the package fails.
	 * @throws SAXException
	 *           if parsing the XML data fails.
	 *           
	 * @return Bucket 
	 *//*
	public Bucket processXlsData(OfficeSuiteTarget officeSuiteTarget) throws IOException, OpenXML4JException, SAXException {
		Bucket dataBucket = Bucket.getInstance(officeSuiteTarget);
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		SheetContentsHandler sheetContentsHandler = new BufferedSheetContentsHandler();

		while (iter.hasNext()) {
			this.buffer = new HashMap<>();
			InputStream stream = iter.next();
			String sheetName = iter.getSheetName();
			processSheet(styles, strings, sheetContentsHandler, stream);
			dataBucket.put(sheetName, this.buffer);
			stream.close();
		}
		return dataBucket;
	}

	public Bucket parseXlsxData(List<String> sheets) throws InvalidOperationException, IOException, OpenXML4JException, SAXException{
		InputStream stream = null;
		Bucket dataBucket = Bucket.getInstance();
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		SheetContentsHandler sheetContentsHandler = new BufferedSheetContentsHandler();

		while (iter.hasNext()) {
			stream = iter.next();
			if (sheets.contains(iter.getSheetName())){
				this.stringTable = new ArrayList<>();
				processSheet(styles, strings, sheetContentsHandler, stream);
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
			this.stringTable = new ArrayList<>();
			stream = iter.next();
			sheetName = iter.getSheetName();
			processSheet(styles, strings, sheetContentsHandler, stream);
			dataBucket.put(sheetName, this.stringTable);
			stream.close();
		}
		this.xlsxPackage.close();
		return dataBucket;
	}

	public void parseFoumnData(List<List<String>> stringTable) {
		int columnIndexForum = 0;
		int columnIndexTopic = 1;
		int columnIndexThread = 2;
		int columnIndexThreadDesc = 3;
		int columnIndexPost = 4;

		String cellData = null;
		Forum forum = null;
		Topic topic = null;
		net.vpx.domain.entity.vbb.Thread thread = null;
		Post post = null;
		for (int rowIdx = 0; rowIdx < stringTable.size(); ++rowIdx) {
			List<String> rowData = stringTable.get(rowIdx);
			cellData = rowData.get(columnIndexForum);
			if (CommonUtility.isNotEmpty(cellData)){
				forum = Forum.getInstance(cellData);
				System.out.println("Processing forumn: " + forum);
				continue;
			}

			cellData = rowData.get(columnIndexTopic);
			if (CommonUtility.isNotEmpty(cellData)){
				topic = Topic.getInstance(cellData, forum);
				System.out.println("Processing topic: " + topic);
				continue;
			}

			cellData = rowData.get(columnIndexThread);
			if (CommonUtility.isNotEmpty(cellData)){
				thread = net.vpx.domain.entity.vbb.Thread.getInstance(cellData, rowData.get(columnIndexThreadDesc), topic);
				System.out.println("Processing thread: " + thread);
				continue;
			}

			while (CommonUtility.isNotEmpty(cellData = rowData.get(columnIndexPost))){
				post = Post.getInstance(cellData, thread);
				try {
					rowData = stringTable.get(++rowIdx);
				} catch (Exception e) {
					break;
				}

				if (CommonUtility.isEmpty(rowData)){
					break;
				}
				System.out.println(forum.getName() + "\t|" + topic.getName() + "\t|" + thread.getName()+ "\t|" + post);
			}
		}
	}

	public static Bucket parseXlsData(File xlsDataFile) throws InvalidOperationException, IOException, OpenXML4JException, SAXException{
		int minColumns = -1;
		// The package open is instantaneous, as it should be.
		OPCPackage p = OPCPackage.open(xlsDataFile.getPath(), PackageAccess.READ);
		Xlsx2StringTableParser xlsx2csv = new Xlsx2StringTableParser(p, minColumns);
		return xlsx2csv.processXlsData(OfficeSuiteTarget.StringTable);
	}

	
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Use:");
			System.err.println("  XLSX2CSV <xlsx file> [min columns]");
			return;
		}

		File xlsxFile = new File(args[0]);
		//File xlsxFile = new File("D:/user-data/Report.xlsx");
		final String dir = System.getProperty("user.dir");
    System.out.println("current dir = " + dir);
		File xlsxFile = new File(dir + "/src/main/resources/config/liquibase/data/forum-structure.xlsx");
		
		if (!xlsxFile.exists()) {
			System.err.println("Not found or not a file: " + xlsxFile.getPath());
			return;
		}

		Xlsx2StringTableParser parserInstance = Xlsx2StringTableParser.getInstance(new FileInputStream(xlsxFile));
		Bucket dataBucket = parserInstance.parseXlsxData();
		System.out.println(dataBucket);
		@SuppressWarnings("unchecked")
		List<List<String>> dataMap = (List<List<String>>)dataBucket.getBucketData().get("Sheet1");
		parserInstance.parseFoumnData(dataMap);
		
		
		for (Object rowKey :dataMap.keySet()){
			for (int cellIdx = 0; cellIdx < defaultNumberOfCells; cellIdx++){
				System.out.print(dataMap.get(rowKey).get(cellIdx) + "#"); 
			}
			System.out.println();
		}
		
		int minColumns = -1;
		if (args.length >= 2)
			minColumns = Integer.parseInt(args[1]);

		// The package open is instantaneous, as it should be.
		OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
		Xlsx2StringTableParser xlsx2csv = new Xlsx2StringTableParser(p, minColumns);
		dataBucket = xlsx2csv.processXlsData(OfficeSuiteTarget.StringTable);
		@SuppressWarnings("unchecked")
		dataMap = (Map<Object, List<String>>)dataBucket.getBucketData().get("Sheet1");
		for (Object rowKey :dataMap.keySet()){
			for (int cellIdx = 0; cellIdx < xlsx2csv.getActualMaxPhysicalCells(); cellIdx++){
				System.out.print(dataMap.get(rowKey).get(cellIdx) + "#"); 
			}
			System.out.println();
		}
		System.out.println(dataBucket);
		//xlsx2csv.process(OfficeSuiteTarget.CSV);
		//p.close();
	}
}
*/