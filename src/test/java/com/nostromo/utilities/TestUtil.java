package com.nostromo.utilities;

import com.nostromo.base.TestBase;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;
import org.apache.commons.io.FileUtils;

public class TestUtil extends TestBase {

	public static String screenshotName;
	
	public static void captureScreenshot() throws IOException {
		File screenFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		screenshotName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())
				.replace(":", "_")
				.replace(".", "_")
				.replace("-", "_")
				.replace(" ", "_") + ".jpg";
		FileUtils.copyFile(screenFile, new File("target/surefire-reports/html/" + screenshotName));
	}
	
	@DataProvider(name="dp")
	public Object[][] getData(Method m) {
		
		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		Object[][] data = new Object[rows - 1][1];
		Hashtable<String, String> table;
		
		for(int rowNum = 2; rowNum <= rows; rowNum++) {
			table = new Hashtable<>();
			for(int colNum = 0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}
		}
		return data;
	}

	/**@DataProvider(name="dp")
	public Object[][] getData(Method m) {

		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][cols];

		for(int rowNum = 2; rowNum <= rows; rowNum++) {
			for(int colNum = 0; colNum < cols; colNum++) {
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		return data;
	} */

	public static boolean isTestRunnable(String testName, ExcelReader excel) {
		String sheetName = "test_suite";
		int rows = excel.getRowCount(sheetName);
		
		for(int rowNum = 2; rowNum <= rows; rowNum++) {
			String testCase = excel.getCellData(sheetName, "TCID", rowNum);
			
			if(testCase.equalsIgnoreCase(testName)) {
				String runMode = excel.getCellData(sheetName, "Runmode", rowNum);
				if(runMode.equalsIgnoreCase("Y")) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
}
