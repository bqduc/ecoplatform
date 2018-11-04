/**
 * 
 */
package net.brilliance.officesuite;

import java.util.List;
import java.util.Map;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.model.Bucket;

/**
 * @author ducbq
 *
 */
public class UnitTestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		doReadExlsxFile();
	}

	protected static void doReadExlsxFile(){
		try {
			String resourceFileName = "F:/Downloads/DANH-MỤC-HÀNG-HÓA-XUẤT-KHẨU-NHẬP-KHẨU-VIỆT-NAM-2015.xlsx";
			/*SpreadsheetXSSFEventBasedExtractor instance = SpreadsheetXSSFEventBasedExtractor.getInstance(permisionsResourceFileName);

			Bucket bucket = instance.extractData(new String[]{permissionGroupSheet, permissionSheet, actionSheet, grantedPermissionSheet});*/
			String sheet = "Danh muc-";
			Map<Object, Object> configParams = ListUtility.createMap();
			configParams.put(sheet+Bucket.PARAM_STARTED_ROW_INDEX, Integer.valueOf(2));
			Bucket bucket = SpreadsheetXSSFEventBasedExtractor.extractSpreadsheetData(resourceFileName, new String[]{sheet}, configParams);
			parseCatalogues((List<List<String>>)bucket.get(sheet));
			//System.out.println(bucket.get(sheet));
			//PHẦN 
			//Chương 
		} catch (Exception e) {
			
		}
	}

	protected static void parseCatalogues(List<List<String>> bucketDataElements){
		try {
			byte idx = 0;
			String superCatalogCode = "";
			String superCatalog = "";
			for (List<String> stringElements :bucketDataElements){
				if (CommonUtility.isNotEmpty(stringElements.get(0)) && CommonUtility.isEmpty(stringElements.get(1)) && CommonUtility.isEmpty(stringElements.get(2))){
					if (stringElements.get(0).startsWith("PHẦN ")){
						//Begin of one top catalog and the content can be multiple lines
						/** - The first line is code
						 *  - The second line is name 
						 *  - From the next lines is description
						 */
						idx = 0;
						superCatalogCode = stringElements.get(0);
						continue;
					} else if (stringElements.get(0).startsWith("Chương ")){
						//Complete the current catalog and make it as the parent

						//Begin of second level catalog and then complete the parent catalog if presented
					}
					if (CommonUtility.isEmpty(superCatalogCode)){
						superCatalogCode = stringElements.get(0);
					}else{
						superCatalog += stringElements.get(0);
					}
					continue;
					//System.out.println("Super catalog: " + stringElements.get(0));
				}else if (stringElements.get(0).length()==5){
					System.out.println("Catalog level 1: " + stringElements.get(0) + "#" + stringElements.get(1));
				}else if (stringElements.get(0).length()==7){
					System.out.println("Catalog level 2: " + stringElements.get(0) + "#" + stringElements.get(1));
				}else if (stringElements.get(0).length()==10){
					System.out.println("Product: " + stringElements.get(0) + "#" + stringElements.get(1));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
