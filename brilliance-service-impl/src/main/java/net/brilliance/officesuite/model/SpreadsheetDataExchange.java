package net.brilliance.officesuite.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;
import net.brilliance.common.CommonUtility;

public class SpreadsheetDataExchange<T> {
	private static final char CSV_DELIM = ';';

	public interface RowConverter<T> {
		T convert(Object[] row);
	}

	public static class Builder<T> {
		private boolean hasHeader;
		private RowConverter<T> converter;
		private char delimiter = CSV_DELIM;

		public Builder() {
		}

		public Builder<T> converter(RowConverter<T> converter) {
			this.converter = converter;
			return this;
		}

		public Builder<T> withHeader() {
			this.hasHeader = true;
			return this;
		}

		public Builder<T> csvDelimiter(char delimiter) {
			this.delimiter = delimiter;
			return this;
		}

		public SpreadsheetDataExchange<T> build() {
			return new SpreadsheetDataExchange<T>(this);
		}
	}

	private Builder<T> info;

	public static <T> Builder<T> builder(Class<T> cls) {
		return new Builder<T>();
	}

	private SpreadsheetDataExchange(Builder<T> info) {
		this.info = info;
	}

	public DataContainer fetch(final String fileName, final List<String> sheetNames) throws Exception {
		DataContainer fetchedData = null;
		try (FileInputStream is = new FileInputStream(fileName)) {
			fetchedData = mappedRead(is, sheetNames);
		}
		return fetchedData;
	}

	public DataContainer fetch(final InputStream inputStream, final List<String> sheetNames) throws Exception {
		return readXlsxData(inputStream, sheetNames);
	}

	public DataContainer fetch(File file, final List<String> sheetNames) throws Exception {
		return readXlsxData(new FileInputStream(file), sheetNames);
	}

	public DataContainer fetchXlsxData(final InputStream inputStream, final List<String> sheetNames) throws Exception {
		return mappedRead(inputStream, sheetNames);
	}

	public DataContainer mappedRead(InputStream is, List<String> sheetNames) throws Exception {
		DataContainer dataContainer = null;
		try (BufferedInputStream buf = new BufferedInputStream(is)) {
			dataContainer = this.readXlsxData(buf, sheetNames);
		}
		return dataContainer;
	}

	private DataContainer readXlsxData(InputStream is, List<String> sheetNames) throws Exception {
		DataContainer dataContainer = DataContainer.instance();
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(is);
		} catch (NotOfficeXmlFileException e) {
      POIFSFileSystem fs = new POIFSFileSystem(is);
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      System.out.println(wb);
		}
		Sheet sheet = null;
		List<T> objList = new ArrayList<>();
		//Build list of processing sheet names
		List<String>processSheetNames = new ArrayList<>();
		if (CommonUtility.isNotEmpty(sheetNames)) {
			processSheetNames.addAll(sheetNames);
		}else {
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				processSheetNames.add(workbook.getSheetName(i));
			}
		}

		for (String sheetName :processSheetNames) {
			sheet = workbook.getSheet(sheetName);
			objList.clear();
			extractSheet(sheet, objList);

			dataContainer.put(sheetName, new ArrayList<>(objList));
		}
		workbook.close();
		return dataContainer;
	}

	protected DataContainer readData(InputStream is, List<String> sheetNames) throws Exception {
		DataContainer dataContainer = DataContainer.instance();

		Workbook workbook = WorkbookFactory.create(is);
		Sheet sheet = null;
		List<T> objList = new ArrayList<>();
		//Build list of processing sheet names
		List<String>processSheetNames = new ArrayList<>();
		if (CommonUtility.isNotEmpty(sheetNames)) {
			processSheetNames.addAll(sheetNames);
		}else {
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				processSheetNames.add(workbook.getSheetName(i));
			}
		}

		for (String sheetName :processSheetNames) {
			sheet = workbook.getSheet(sheetName);
			objList.clear();
			extractSheet(sheet, objList);

			dataContainer.put(sheetName, new ArrayList<>(objList));
		}
		return dataContainer;
	}

	protected List<T> readCsv(InputStream in) throws Exception {
		List<T> objList = new ArrayList<>();
		InputStreamReader isr = new InputStreamReader(in);
		try (CSVReader cvsr = new CSVReader(isr, info.delimiter)) {
			List<String[]> allRows = cvsr.readAll();
			int start = info.hasHeader ? 1 : 0;
			for (int i = start; i < allRows.size(); i++) {
				T obj = info.converter.convert(allRows.get(i));
				objList.add(obj);
			}
		}
		return objList;
	}

	private void extractSheet(Sheet sheet, List<T> objList) {
		Iterator<Row> rowIterator = sheet.iterator();
		if (rowIterator.hasNext() && info.hasHeader) {
			rowIterator.next();
		}
		while (rowIterator.hasNext()) {
			T obj = extractObject(rowIterator);
			objList.add(obj);
		}
	}

	private T extractObject(Iterator<Row> rowIterator) {
		Row row = rowIterator.next();
		Iterator<Cell> cellIterator = row.cellIterator();
		Object[] rowVals = new Object[row.getLastCellNum()];
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			rowVals[cell.getColumnIndex()] = getValue(cell);
		}
		return info.converter.convert(rowVals);
	}

	/*private List<Object> extractRowValues(Iterator<Row> rowIterator) {
		List<Object> rowValues = new ArrayList<>();
		Row row = rowIterator.next();
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			rowValues.add(getValue(cell));
		}
		return rowValues;
	}*/

	/*private boolean isExcel(InputStream is) throws Exception {
		return POIXMLDocument.hasOOXMLHeader(is)  .xlsx 
				|| POIFSFileSystem.hasPOIFSHeader(is);  .xls 
	}*/

	public Object getValue(Cell cell) {
		Object retVal = null;
		try {
			if (CellType.STRING.equals(cell.getCellTypeEnum())) {
				retVal = cell.getStringCellValue();
			}else if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
        if (DateUtil.isCellDateFormatted(cell)) {
        	retVal = cell.getDateCellValue();
        } else {
        	retVal = cell.getNumericCellValue();
        }
      }else if (CellType.BOOLEAN.equals(cell.getCellTypeEnum())) {
      	retVal = cell.getBooleanCellValue();
			}else if (CellType.FORMULA.equals(cell.getCellTypeEnum())) {
				retVal = cell.getCellFormula();
			}else if (CellType.ERROR.equals(cell.getCellTypeEnum())) {
				retVal = cell.getErrorCellValue();
			}else if (CellType._NONE.equals(cell.getCellTypeEnum())||CellType.BLANK.equals(cell.getCellTypeEnum())) {
				retVal = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;

		/*switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_BLANK:
			return null;
		}
		return null;*/
	}
}
