package com.nostromo.testcases;

import org.testng.annotations.Test;
import com.nostromo.base.TestBase;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;

public class BankManagerLoginTest extends TestBase {

    @Test
    public void bankManagerLoginTest() throws InterruptedException, IOException {
    	
    	verifyEquals("abc", "xyz");
    	Thread.sleep(2000);
    	
    	log.debug("Inside LoginTest class");
    	click("bankMngLogBtn_CSS");
        assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustomerBtn_CSS"))), "Login not successful");
        log.debug("LoginTest successfully executed!");
        
//        Assert.fail("Login not successful!");
    }
}
