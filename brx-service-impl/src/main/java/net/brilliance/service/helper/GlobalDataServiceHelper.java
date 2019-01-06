package net.brilliance.service.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import net.brilliance.common.ListUtility;
import net.brilliance.dispatch.spreadsheet.SpreadsheetStringTableDataParser;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.component.ComponentBase;
import net.brilliance.model.Bucket;

@Component
public class GlobalDataServiceHelper extends ComponentBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7517148901726100825L;

	public Bucket readSpreadsheetData(InputStream inputStream) throws EcosysException {
		return readSpreadsheetData(inputStream, ListUtility.createMap());
	}

	public Bucket readSpreadsheetData(InputStream inputStream, String[] sheets) throws EcosysException {
		Map<Object, Object> params = ListUtility.createMap();
		params.put(Bucket.PARAM_DATA_SHEETS, sheets);
		return readSpreadsheetData(inputStream, params);
	}

	public Bucket readSpreadsheetData(InputStream inputStream, Map<Object, Object>params) throws EcosysException {
		Bucket dataBucket = null;
		try {
			dataBucket = SpreadsheetStringTableDataParser.getInstance(inputStream).extractData(params);
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		return dataBucket;
	}

	public List<String> getCsvStrings(String file, int dataStartedRowIdx) throws EcosysException{
		List<String> dataStrings = new ArrayList<>();
		BufferedReader br = null;
		String line = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			for (int idx = 0; idx < dataStartedRowIdx; idx++){
				// Skip first line that only holds headers.
				br.readLine();
			}

			while ((line = br.readLine()) != null) {
				dataStrings.add(line);
			}
		} catch (Exception e) {
			throw new EcosysException(e);
		} finally {
			try {
				// Release resources.
				br.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return dataStrings;
	}
}
