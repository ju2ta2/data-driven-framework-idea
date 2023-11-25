package com.nostromo.utilities;

import java.io.File;
import java.nio.file.Paths;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	
	private static ExtentReports extentReport;
	
	public static ExtentReports getInstance() {
		if(extentReport == null) {
			extentReport = new ExtentReports(Paths.get("target/surefire-reports/html/extent_report.html").toAbsolutePath().toString(), true, DisplayOrder.OLDEST_FIRST);
			extentReport.loadConfig(new File(Paths.get("src/test/resources/extentconfig/ReportsConfig.xml").toAbsolutePath().toString()));
		}
		return extentReport;
	}

}
