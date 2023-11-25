package com.nostromo.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.nostromo.base.TestBase;
import com.nostromo.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		extentTest = extReport.startTest(result.getName().toUpperCase());
		// Run mode - Y
		if(!TestUtil.isTestRunnable(result.getName(), excel)) {
			throw new SkipException("Sipping the test " + result.getName().toUpperCase() + " as the Run mode is NO.");
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.log(LogStatus.PASS, result.getName().toUpperCase() + "PASS");
		extReport.endTest(extentTest);
		extReport.flush();
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// For converting plain text to link in ng-reports
    	System.setProperty("org.uncommons.reportng.escape-output", "false");
    	
    	try {
    		Thread.sleep(1000);
			TestUtil.captureScreenshot();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	extentTest.log(LogStatus.FAIL, result.getName().toUpperCase() + " Failed with exception: " + result.getThrowable());
    	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(TestUtil.screenshotName));
    	
		Reporter.log("Click to see the screenshot");
        Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + ">Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");
        Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName + " height=200 width=200></img></a>");
        extReport.endTest(extentTest);
		extReport.flush();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.log(LogStatus.SKIP, result.getName().toUpperCase() + " skipped as the Run mode is NO.");
		extReport.endTest(extentTest);
		extReport.flush();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
