/**
 * 
 */
package net.powertrain.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.util.IOUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.common.RandomString;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.domain.entity.general.Project;
import net.brilliance.model.Bucket;
import net.brilliance.officesuite.GlobalOfficeSuiteRepository;
import net.brilliance.officesuite.SpreadsheetXSSFEventBasedExtractor;
import net.brilliance.officesuite.model.DataContainer;
import net.brilliance.scheduler.job.MonthEndJob;
import net.brilliance.service.helper.GlobalDataServiceHelper;

/**
 * @author ducbq
 *
 */
public class MainUnitTest {
	private final static String[] fileNames = new String[]{
			"C:\\\\Users\\\\ducbq\\\\eclipse-workspace\\\\common-utility\\\\src-test\\\\test\\\\CountryCodes.xls",
			"D:\\workspace\\pg-clinic\\e-platform\\data-test\\DS tai tu do tren 10T co thung hang phu hop TT 32.xls",
			"D:\\\\workspace\\\\pg-clinic\\\\e-platform\\\\data-test\\\\DS tai tu do tren 10T co thung hang phu hop TT 32 - data.xls", 
			"D:\\user-data\\DS Xe tai.xls",
			"D:\\user-data\\DS Xe tai.xlsx", 
			"f:\\user-data\\patient-data.xlsx", 
			"D:\\user-data\\Report.xlsx", 
			"D:\\workspace\\pg-clinic\\e-platform\\data-test\\contact-biz-manager.xls", 
			"D:\\workspace\\cloud-pos\\vpte\\src\\main\\resources\\config\\data\\projects-data-1.xlsx",
			"D:\\user-data\\projects-data-1.xlsx", 
			"F:/Downloads/danh-sach-5600-nhan-vien-ban-hang.xlsx"
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			testMisc();
			//testMarshallProducts();
			//createBookCovers();
			/*for (EmployeeConfigurationModel employeeConfigModel :EmployeeConfigurationModel.values()){
				System.out.println(employeeConfigModel.getModel() + "\t|" + employeeConfigModel.getIndex() + "\t|"+employeeConfigModel.getDataPattern());
			}
			
			
			
			
			InputStream inputStream = new FileInputStream(fileNames[fileNames.length-1] "C:/Users/ducbq/Documents/data-vpex-repaired.xlsx");
			long started = System.currentTimeMillis();
			Bucket dataBucket = GlobalExternalDataHelper.getInstance().readXlsData(inputStream, new String[]{"danh-sach"});
			System.out.println("Tenken: " + (System.currentTimeMillis()-started));
			System.out.println("Read data is done.");
			readEmployeeData(dataBucket, "danh-sach");*/
			
			/*InventoryCatalogDataDispatchHelper generalDataDispatcher = new InventoryCatalogDataDispatchHelper();
			String fileName = "D:/user-data/1. Thuốc tân dược/1. Danh mục thuốc tân dược.xlsx";
			InputStream inputStream = new FileInputStream(fileName);
			generalDataDispatcher.dispatchCatalogues();*/
			
			//generalDataDispatcher.dispatchInventoryItems(inputStream, 4);
			
			
			
			//testExternalDataHelper(); //Fine
			/*ExcelTest instance = ExcelTest.getInstance();
			instance.setUp();
			instance.shouldParseCorrectly_GivenXlsFile(fileName);*/
			/*testClasses();
			String encodedUrl = URLEncoder.encode("04A/GPĐC1-ĐN", "UTF-8");
			System.out.println(encodedUrl);

			testHexEncodeDecode("ducbuiquy@gmail.com");
			testSystemEnvironmentVariable();
			testEncodePassword();
			//readExcel();
			testGenerator();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void readEmployeeData(Bucket dataBucket, String sheetName){
		List<String> dataParts = null;
		try {
			List<List<String>> dataStrings = (List<List<String>>)dataBucket.getBucketData().get(sheetName);
			for (int i = 1; i < dataStrings.size(); ++i){
				dataParts = dataStrings.get(i);
				System.out.println(dataParts.get(1)+"|\t"+dataParts.get(2)+" "+dataParts.get(3)+"|\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void readExcel() {
		long startTime, duration, taken;
		try {
			/*startTime = System.currentTimeMillis();
			OfficeSuiteDataContainer container = ExcelSuiteMarshaller.instance().readData(fileNames[5], null);
			duration = System.currentTimeMillis()-startTime;
			taken = (duration)/1000;
			System.out.println("Taken: " + taken);*/
			
			
			startTime = System.currentTimeMillis();
			DataContainer dataContainer = GlobalOfficeSuiteRepository.instance().fetchExcelData(new File(fileNames[8]));
			duration = System.currentTimeMillis()-startTime;
			System.out.println("Taken: " + (duration)/1000);
			displayData(dataContainer.getBucketData());
			/*RowConverter<ExcelSuiteEntity> excelSuiteConverter = (row) -> new ExcelSuiteEntity(row);
			ExcelSuiteDataExchange<ExcelSuiteEntity> excelSuiteReader = ExcelSuiteDataExchange.builder(ExcelSuiteEntity.class)
	        .converter(excelSuiteConverter)
	        .withHeader()
	        .csvDelimiter(';')
	        .sheets(0)
	        .build();

			Map<Object, List<ExcelSuiteEntity>> fetchedData = excelSuiteReader.fetch(fileNames[5], null);
			for (Object key :fetchedData.keySet()) {
				List<ExcelSuiteEntity> fetchedList = fetchedData.get(key);
				System.out.println("Sheet: " + key);
				displayData(fetchedList);
			}*/

			//List<ExcelSuiteEntity> excelSuiteEntities = excelSuiteReader.read(fileNames[5]);
			//System.out.println(excelSuiteEntities);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void displayData(Map<Object, Object> dataMap) {
		List<Project> projects = new ArrayList<>();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (Object worksheet :dataMap.keySet()) {
			System.out.println("Data of worksheet: " + worksheet);
			if (dataMap.get(worksheet) instanceof Map) {
				Map itemMap = (Map)dataMap.get(worksheet);
				for (Object innerKey :itemMap.keySet()) {
					displayData((List<DataContainer>)itemMap.get(innerKey));
				}
			}else if (dataMap.get(worksheet) instanceof List) {
				List<DataContainer> objectList = (List<DataContainer>)dataMap.get(worksheet);
				System.out.println("Number of rows: " + objectList.size());
				String code = null;
				int pos = -1;
				String parsedData = null;
				for (DataContainer object :objectList) {
					parsedData = object.getContainer().get(0).toString().trim();
					pos = parsedData.indexOf("471");
					try {
						if (pos != -1) {
							if (parsedData.length() > 12+pos) {
								code = parsedData.substring(pos, pos+12);
							}else {
								code = parsedData.substring(pos);
							}
						}else {
							code = parsedData;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("--------------------------------------------------------------");
					System.out.println("Licence: " + code);
					System.out.println("Project name: " + object.getContainer().get(4).toString().trim());
					System.out.println("Investor country: " + object.getContainer().get(5).toString().trim());
					System.out.println("Business domain: " + object.getContainer().get(6).toString().trim());
					System.out.println("Investment capital: " + object.getContainer().get(7));
					System.out.println("Investor: " + object.getContainer().get(3));
					System.out.println("Licence date: " + object.getContainer().get(2));
					System.out.println("Implement address: " + object.getContainer().get(8));
					System.out.println("Implement duration: " + object.getContainer().get(9));
					System.out.println("Contact address: " + object.getContainer().get(11));
				}
			}
		}
	}

	private static void displayData(List<DataContainer> dataList) {
		int objectCounter = 0;
		for (DataContainer excelSuiteEntity :dataList) {
			++objectCounter;
			System.out.print(objectCounter + ":\t" + excelSuiteEntity.getContainer().size() + "\t:");
			for (Object obj :excelSuiteEntity.getContainer()) {
				if (null==obj) {
					System.out.print("~ ");
				}else {
					System.out.print("~" + obj.toString());
				}
			}
			System.out.println();
		}
	}

	protected static void testSystemEnvironmentVariable(){
		System.out.println("-------------------System environment variables-------------------------");
		Map<String, String> systemEnvVars = System.getenv();
		for (String envVar :systemEnvVars.keySet()){
			System.out.println("$" + envVar + ": " + System.getenv(envVar));
		}
		System.out.println("---------------------done-----------------------");
	}

	protected static void testEncodePassword(){
		boolean encodeHashAsBase64 = true;
		String value = "0Password";
		int strength = 256;
		String salt = "裴达克·奎";
		try {
			Md5PasswordEncoder md5PwdEncoder = new Md5PasswordEncoder();
	    md5PwdEncoder.setEncodeHashAsBase64(encodeHashAsBase64);
	    ShaPasswordEncoder shaPwdEncoder = new ShaPasswordEncoder(strength);
	    shaPwdEncoder.setEncodeHashAsBase64(encodeHashAsBase64);
	    String md5Encoded = md5PwdEncoder.encodePassword(value, salt);
	    String encrypted = shaPwdEncoder.encodePassword(md5Encoded, salt);
	    System.out.println(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected static void testGenerator(){
		String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
		RandomString tickets = new RandomString(23, new SecureRandom(), easy);
		System.out.println(tickets.nextString());
	}

	protected static void testHexEncodeDecode(String string) throws Exception {
		String encodedString = CommonUtility.encodeHex(string);
		System.out.println("Encoded: " + encodedString);
		
		String decodedString = CommonUtility.decodeHex(encodedString);
		System.out.println("Decoded: " + decodedString);
		/*String encoded = Base64Utils.encodeToString(string.getBytes());
		byte[] encodedBytes = Base64Utils.encode(string.getBytes());
		String encodedHex = Hex.encodeHexString(encodedBytes);
		System.out.println("Encoded hex: " + encodedHex);

		byte[] hexDecodedBytes = Hex.decodeHex(encodedHex.toCharArray());
		byte[] decodedBytes = Base64Utils.decode(hexDecodedBytes);
		String decodedHex = new String(decodedBytes);*/
	}

	protected static void testClasses(){
		try {
			Class<?> monthEndClass = Class.forName(MonthEndJob.class.getName());
			System.out.println(monthEndClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isProduct(String codeValue){
		final String regularExpression = "^\\d{4}[.]\\d{2}$";
		final String regularExpressionExactly = "^\\d{4}[.]\\d{2}[.]\\d{2}$";
		return CommonUtility.regularExpressionCompiled(regularExpressionExactly, codeValue) || 
				CommonUtility.regularExpressionCompiled(regularExpression, codeValue);
	}

	private static boolean isCategory(String codeValue){
		final String regularExpression = "^\\d{2}[.]\\d{2}$";
		return CommonUtility.regularExpressionCompiled(regularExpression, codeValue);
	}

	private static boolean isDepartment(String codeValue){
		final String regularExpression = "^Chương*?$";
		return CommonUtility.regularExpressionCompiled(regularExpression, codeValue);
	}

	private boolean isCatalog(String codeValue){
		final String regularExpression = "^PHẦN*?$";
		return CommonUtility.regularExpressionCompiled(regularExpression, codeValue);
	}

	protected static void testExternalDataHelper(){
		final String regExpDepartment = "Chương";
		final String regExpCategory = "^\\d{2}[.]\\d{2}$";
		final String regExpProduct = "^\\d{4}[.]\\d{2}$";
		final String regExpProductExactly = "^\\d{4}[.]\\d{2}[.]\\d{2}$";
		int deptDescIdx;
		try {
			GlobalDataServiceHelper globalDataServiceHelper = new GlobalDataServiceHelper();
			String fileName = "D:/user-data/danh muc-hang-customized.xlsx";
			InputStream inputStream = new FileInputStream(fileName);
			Bucket dataBucket = globalDataServiceHelper.readSpreadsheetData(inputStream);
			List<List<String>> dataStrings = (List<List<String>>)dataBucket.get("Xay dung");
			String department = "";
			String category = "";
			String categoryName = "";
			String product = "";
			String productName = "";
			List<String> dataString;
			for (int idx = 0; idx < dataStrings.size(); ++idx){
				dataString = dataStrings.get(idx);
				System.out.println(idx+1 + "\t|" + dataString);
				if (isCategory(dataString.get(0))){
					category = dataString.get(0);
					categoryName = dataString.get(1);

					product = "";
					productName = "";
					//System.out.println((++idx) + "\tGroup\t|" + dataString.get(0)+"|\t" + dataString.get(1));
				}else if (isProduct(dataString.get(0))){
					product = dataString.get(0);
					productName = dataString.get(1);
					//System.out.println(++idx + "\tProduct\t|" + dataString.get(0)+"|\t" + dataString.get(1));
				}else if (isDepartment(dataString.get(0)) || dataString.get(0).startsWith(regExpDepartment)){
					department = dataString.get(0);
					category = "";
					categoryName = "";
					product = "";
					productName = "";
					//Loop to get all information about the department
					do{
						dataString = dataStrings.get(++idx);
					}while(!(isDepartment(dataString.get(0)) || isCategory(dataString.get(0)) || isProduct(dataString.get(0))));
				}
				System.out.println(++idx + "\t|"+ department + "\t|" +category+ "\t|" +categoryName + "\t|" + product + "\t|" +productName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static byte[] getPicture(String fileName) throws IOException{
		byte[] bytes = null;
		InputStream inputStream = new FileInputStream(fileName);
		bytes = IOUtils.toByteArray(inputStream);
		inputStream.close();
		return bytes;
	}

	protected static void createBookCovers(){
		String books = "F:/Downloads/book covers/ebooklist.xlsx";
		File coverDir = new File("F:/Downloads/book-covers-images/");
		File [] files = coverDir.listFiles(new FilenameFilter() {
	    @Override
	    public boolean accept(File dir, String name) {
	        return name.endsWith(".jpg");
	    }
		});

		/*try {
			Workbook workbook = WorkbookFactory.create(new File(books));
			Sheet sheet = workbook.getSheet("Sheet1");
			
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}*/

		String fileName = "";
		for (int i = 0; i < files.length; i++){
			fileName = files[i].getPath();

			try {
				BufferedImage bimg = ImageIO.read(new File(fileName));
				System.out.println(fileName +"\t{" + bimg.getWidth() + "-" + bimg.getHeight() + "}");
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		/*for (File file: files){
			fileName = file.getName();
			if (-1 != fileName.indexOf("--")){
				fileName = fileName.substring(0, fileName.indexOf("--"));
				file.renameTo(new File(coverDir.getPath() + "/" + fileName + ".jpg"));
			}
			System.out.println(fileName + "|\t" + file.getName());
		}*/
		//Column #7
	}
	protected static void testMisc(){
		String temple = "12345";
		System.out.println(temple.substring(0, 6)); 
	}

	protected static void testMarshallProducts(){
		try {
			String resourceFileName = "F:/Downloads/DANH-MỤC-HÀNG-HÓA-XUẤT-KHẨU-NHẬP-KHẨU-VIỆT-NAM-2015.xlsx";
			/*
			SpreadsheetXSSFEventBasedExtractor instance = SpreadsheetXSSFEventBasedExtractor.getInstance(permisionsResourceFileName);

			Bucket bucket = instance.extractData(new String[]{permissionGroupSheet, permissionSheet, actionSheet, grantedPermissionSheet});*/
			String sheet = "Danh muc-";
			Map<Object, Object> configParams = ListUtility.createMap();
			configParams.put(sheet+Bucket.PARAM_STARTED_ROW_INDEX, Integer.valueOf(2));
			Bucket bucket = SpreadsheetXSSFEventBasedExtractor.extractSpreadsheetData(resourceFileName, new String[]{sheet}, configParams);
			//System.out.println(bucket.get(sheet));
			//PHẦN 
			//Chương 
			Catalogue currentParent = Catalogue.builder().build();
			Catalogue currentCatalog = Catalogue.builder().build();
			String superCatalogCode = "";
			String superCatalog = "";
			String code = "";
			String desc = "";
			byte idx = 0;
			for (List<String> stringElements :((List<List<String>>)bucket.get(sheet))){
				if (CommonUtility.isNotEmpty(stringElements.get(0)) && CommonUtility.isEmpty(stringElements.get(1)) && CommonUtility.isEmpty(stringElements.get(2))){
					if (stringElements.get(0).startsWith("PHẦN ")){
						//Begin of one top catalog and the content can be multiple lines
						/** - The first line is code
						 *  - The second line is name 
						 *  - From the next lines is description
						 */
						idx = 0;
						code = stringElements.get(0);
					} else if (stringElements.get(0).startsWith("Chương ")){
						//Begin of second level catalog and then complete the parent catalog if presented
					} else {
						desc += stringElements.get(0) + "\n";
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
