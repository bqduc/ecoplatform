/**
 * 
 */
package net.brilliance.officesuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.dispatch.exceptions.SpreadsheetSuiteException;
import net.brilliance.officesuite.model.DataContainer;
import net.brilliance.officesuite.model.SpreadsheetDataExchange;
import net.brilliance.officesuite.model.SpreadsheetDocDataContainer;
import net.brilliance.officesuite.model.SpreadsheetDataExchange.RowConverter;
import net.brilliance.officesuite.spreadsheet.SpreadsheetXmlDataExchange;

/**
 * @author ducbq
 *
 */
public class GlobalOfficeSuiteRepository {
	protected Logger log = GlobalLoggerFactory.getLogger(this.getClass());
	private static final GlobalOfficeSuiteRepository instance = new GlobalOfficeSuiteRepository();

	private GlobalOfficeSuiteRepository() {
	}

	public static GlobalOfficeSuiteRepository instance() {
		return instance;
	}

	private DataContainer fetchSmallData(final InputStream inputStream, final List<String> sheetNames) throws SpreadsheetSuiteException{
		DataContainer dataContainer = DataContainer.instance();
		try {
			RowConverter<DataContainer> excelSuiteConverter = (row) -> new DataContainer(row);
			SpreadsheetDataExchange<DataContainer> excelSuiteReader = SpreadsheetDataExchange.builder(DataContainer.class)
	        .converter(excelSuiteConverter)
	        .withHeader()
	        .csvDelimiter(';')
	        .build();

			dataContainer = excelSuiteReader.fetch(inputStream, sheetNames);
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
		return dataContainer;
	}

	public SpreadsheetDocDataContainer fetchExcelData(InputStream inputStream, List<String> sheetNames) throws SpreadsheetSuiteException {
		SpreadsheetDocDataContainer dataContainer = null;
		try {
			dataContainer = SpreadsheetXmlDataExchange.instance().fetchByStream(inputStream, sheetNames);
			/*Workbook workbook = StreamingReader.builder().open(inputStream);
			System.out.println(workbook.getNumberOfSheets());
			Iterator<Sheet> sheetIterator = workbook.sheetIterator();
			Iterator<Row> rowIterator = null;
			Iterator<Cell> cellIterator = null;
			Cell currCell = null;
			Row currRow = null;
			Object cellValue = null;
			OfficeExcelUtility officeExcelUtility = OfficeExcelUtility.instance();
			while (sheetIterator.hasNext()) {
				Sheet sheet = (Sheet)sheetIterator.next();
				rowIterator = sheet.rowIterator();
				while (rowIterator.hasNext()) {
					currRow = rowIterator.next();
					cellIterator = currRow.cellIterator();
					while (cellIterator.hasNext()) {
						currCell = cellIterator.next();
						cellValue = officeExcelUtility.getValue(currCell);
						if (null != cellValue) {
							System.out.println("[" + cellValue + "]");
						}else {
							System.out.println("[]");
						}
					}
				}
			}*/
			/*StreamingReader reader = StreamingReader.builder()
	        .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
	        .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
	        .sheetIndex(0)        // index of sheet to use (defaults to 0)
	        .read(inputStream);            // InputStream or File for XLSX file (required)

    	for (Row r : reader) {
    	  for (Cell c : r) {
    	  	log.info("[" + c.getStringCellValue() + "]");
    	  }
    	}*/     
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataContainer;
		//return fetchDataFromExcel(inputStream, null);
	}

	public DataContainer fetchExcelData(final File file) throws SpreadsheetSuiteException {
		return fetchDataFromExcel(file, null);
	}

	public DataContainer fetchExcelData(final String sourceFile) throws SpreadsheetSuiteException {
		return fetchExcelData(sourceFile, null);
	}

	public DataContainer fetchExcelData(final String sourceFile, final List<String> sheetNames) throws SpreadsheetSuiteException {
		DataContainer result = null;
		try {
			result = fetchDataFromExcel(new FileInputStream(sourceFile), sheetNames);
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
		return result;
	}

	private DataContainer fetchDataFromExcel(final File file, final List<String> sheetNames) throws SpreadsheetSuiteException {
		DataContainer result = null;
		try {
			long size = file.length();
			int sizeByMegaBye = (int)(size/(1024*1024));
			if ((OfficeSuiteUtility.instance().isOfficeExcelXlsx(file) && sizeByMegaBye <= 4)||OfficeSuiteUtility.instance().isOfficeExcelXls(file)) {
				result = this.fetchSmallData(new FileInputStream(file), sheetNames);
				//result = ExcelSuiteXmlDataExchange.instance().fetch(file, sheetNames);
			}else if (OfficeSuiteUtility.instance().isOfficeExcelXlsx(file)) {
				result = SpreadsheetXmlDataExchange.instance().fetch(file, sheetNames);
			}
			//result = fetchDataFromExcel(new FileInputStream(file), sheetNames);
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
		return result;
	}

	private DataContainer fetchDataFromExcel(InputStream inputStream, final List<String> sheetNames) throws SpreadsheetSuiteException {
		DataContainer dataContainer = null;
		try {
			long size = inputStream.available();
			int sizeByMegaBye = (int)(size/(1024*1024));
			if ((OfficeSuiteUtility.instance().isOfficeExcelXlsx(inputStream) && sizeByMegaBye <= 4)||OfficeSuiteUtility.instance().isOfficeExcelXls(inputStream)) {
				dataContainer = SpreadsheetXmlDataExchange.instance().fetch(inputStream, sheetNames);
				dataContainer = this.fetchSmallData(inputStream, sheetNames);
			}else if (OfficeSuiteUtility.instance().isOfficeExcelXlsx(inputStream)) {
				dataContainer = SpreadsheetXmlDataExchange.instance().fetch(inputStream, sheetNames);
			}
		} catch (Exception e) {
			//dataContainer = this.fetchSmallData(inputStream, sheetNames);
			throw new SpreadsheetSuiteException(e);
		}
		return dataContainer;
	}

}
