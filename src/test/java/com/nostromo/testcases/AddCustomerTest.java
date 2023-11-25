package com.nostromo.testcases;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.nostromo.base.TestBase;
import com.nostromo.utilities.TestUtil;

import java.util.Hashtable;

public class AddCustomerTest extends TestBase{
	
	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(Hashtable<String, String> data) {
		if(!data.get("runmode").equals("Y")) {
			throw new SkipException("Sipping the test AddCustomerTest as the Run mode is NO.");
		}

		click("addCustomerBtn_CSS");
		type("firstname_CSS", data.get("firstname"));
		type("lastname_CSS", data.get("lastname"));
		type("postcode_CSS", data.get("postcode"));
		click("addBtn_CSS");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//        Alert alert = driver.switchTo().alert();
		assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
//		Assert.fail("Customer not added successful!");
	}

}
