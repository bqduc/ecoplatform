/**
 * 
 */
package net.brilliance.officesuite;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import net.brilliance.dispatch.exceptions.SpreadsheetSuiteException;

/**
 * @author ducbq
 *
 */
public class OfficeExcelUtility {
	private static OfficeExcelUtility instance = new OfficeExcelUtility();

	//private static DataFormatter dataFormatter = new DataFormatter();

	private OfficeExcelUtility(){
	}

	public static OfficeExcelUtility instance(){
		return instance;
	}

	public Workbook getExcelWorkbook(final File file) throws SpreadsheetSuiteException {
		return this.createWorkbook(file);
	}

	private Workbook createWorkbook(final File file) throws SpreadsheetSuiteException{
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(file);
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
		return workbook;
	}

	public Workbook getExcelWorkbook(final InputStream inputStream) throws SpreadsheetSuiteException {
		return this.createWorkbook(inputStream);
	}

	private Workbook createWorkbook(final InputStream inputStream) throws SpreadsheetSuiteException{
		Workbook workbook = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(inputStream);
			workbook = WorkbookFactory.create(bufferedInputStream);
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
		return workbook;
	}

	public void closeExcelWorkbook(Workbook workbook) throws SpreadsheetSuiteException{
		try {
			workbook.close();
		} catch (Exception e) {
			throw new SpreadsheetSuiteException(e);
		}
	}

	public Object getValue(Cell cell) {
		Object retVal = null;
		try {
			if (null==cell)
				return null;

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
	}

	/*private String getStringCellData(Cell cell){
		String stringCellData = null;
		switch(cell.getCellType()){
		case Cell.CELL_TYPE_NUMERIC:
			stringCellData = dataFormatter.formatCellValue(cell);
			break;
		case Cell.CELL_TYPE_STRING:
			stringCellData = dataFormatter.formatCellValue(cell);
			break;
		case Cell.CELL_TYPE_FORMULA:
			stringCellData = "";
			break;
		case Cell.CELL_TYPE_BLANK:
			stringCellData = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			stringCellData = dataFormatter.formatCellValue(cell);
			break;
		case Cell.CELL_TYPE_ERROR:
			stringCellData = "";
			break;
		}
		return stringCellData;
	}

	private Object getCellData(Cell cell){
		Object stringCellData = null;
		switch(cell.getCellType()){
		case Cell.CELL_TYPE_NUMERIC:
			stringCellData = dataFormatter.formatCellValue(cell);
			break;
		case Cell.CELL_TYPE_STRING:
			stringCellData = dataFormatter.formatCellValue(cell);
			break;
		case Cell.CELL_TYPE_FORMULA:
			stringCellData = "";
			break;
		case Cell.CELL_TYPE_BLANK:
			stringCellData = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			stringCellData = dataFormatter.formatCellValue(cell);
			break;
		case Cell.CELL_TYPE_ERROR:
			stringCellData = "";
			break;
		}
		return stringCellData;
	}*/

	/*private void readStreaming(File file){
		try {
	    InputStream is = new FileInputStream(file("/path/to/workbook.xlsx"));
	    StreamingReader reader = StreamingReader.builder()
	            .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
	            .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
	            .sheetIndex(0)        // index of sheet to use (defaults to 0)
	            .read(is);            // InputStream or File for XLSX file (required)
	    
	    for (Row r : reader) {
	      for (Cell c : r) {
	        System.out.println(c.getStringCellValue());
	      }
	    } 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/*public OfficeSuiteDataContainer unmarshall(final File file, String worksheetName) throws ExcelSuiteException {
		OfficeSuiteDataContainer officeSuiteObject = OfficeSuiteDataContainer.createInstance();
		Workbook workbook = null;
		org.apache.poi.ss.usermodel.Sheet worksheet = null;
		short totalCellsPerRow = 0;
		int totalRowsPerSheet = 0;
		Map<String, Object> mappedChunks;
		Cell cell = null;
		Row row = null;
		try {
			workbook = WorkbookFactory.create(file);
			worksheet = workbook.getSheet(worksheetName);
			row = worksheet.getRow(worksheet.getFirstRowNum());
			short firstCellIndex = row.getFirstCellNum();
			totalCellsPerRow = (short)(row.getLastCellNum()-firstCellIndex);
			totalRowsPerSheet = worksheet.getLastRowNum();
			//Create the column headers 
			String[] columnHeaders = new String[totalCellsPerRow];
			for (short cellIdx = 0; cellIdx < totalCellsPerRow; ++cellIdx){
				cell = row.getCell(cellIdx+firstCellIndex);
				if (null != cell){
					columnHeaders[cellIdx] = cell.getRichStringCellValue().toString();
				}else{
					columnHeaders[cellIdx] = "";
				}
			}

			for (int rowIdx = totalCellsPerRow; rowIdx <= totalRowsPerSheet; rowIdx++){
				row = worksheet.getRow(rowIdx);
				mappedChunks = OfficeSuiteDataContainer.createDataChunks();
				for (short cellIdx = 0; cellIdx < totalCellsPerRow; cellIdx++){
					cell = row.getCell(cellIdx);
					if (null==cell){
						
					}else{
						//mappedChunks.put(cell., value)
					}
				}
			}
		} catch (Exception e) {
			throw new ExcelSuiteException(e);
		}
		return officeSuiteObject;
	}*/
}
