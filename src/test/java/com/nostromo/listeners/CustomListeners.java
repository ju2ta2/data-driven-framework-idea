package com.nostromo.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.nostromo.utilities.MonitoringMail;
import com.nostromo.utilities.TestConfig;
import jakarta.mail.MessagingException;
import org.testng.*;

import com.nostromo.base.TestBase;
import com.nostromo.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {

	public String messageBody;

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

	@Override
	public void onStart(ISuite suite) {

	}

	@Override
	public void onFinish(ISuite suite) {
		// Creation email with link to jenkins report
		MonitoringMail mail = new MonitoringMail();
        try {
            messageBody = "http://" + InetAddress.getLocalHost().getHostAddress() + ":8080/job/data-driven-framework-idea/HTML_20Report";
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        System.out.println(messageBody);
        try {
            mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
