package com.nostromo.testcases;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.nostromo.base.TestBase;
import com.nostromo.utilities.TestUtil;

import java.util.Hashtable;

public class OpenAccountTest extends TestBase{
	
	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest(Hashtable<String, String> data) {
		if(!data.get("runmode").equals("Y")) {
			throw new SkipException("Sipping the test OpenAccountTest as the Run mode is NO.");
		}

		click("openAccountBtn_CSS");
		select("selectCustomerNameDrDown_CSS", data.get("customer"));
		select("selectCurrencyDrDown_CSS", data.get("currency"));
		click("openAccountProcessBtn_CSS");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
//		Assert.fail("Customer not added successful!");
	}

}
