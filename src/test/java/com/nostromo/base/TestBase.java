package com.nostromo.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.nostromo.utilities.ExcelReader;
import com.nostromo.utilities.ExtentManager;
import com.nostromo.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

public class TestBase {

	/*
	 * WebDriver - done. 
	 * Properties - done. 
	 * Logs log4j jar, .log, log4j.properties, Logger.
	 * ExtentReports 
	 * DB 
	 * Excel 
	 * Mail 
	 * ReportNG, ExtentReports 
	 * Jenkins
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(Paths.get("src/test/resources/excel/testdata.xlsx").toAbsolutePath().toString());
	public static WebDriverWait wait;
	public ExtentReports extReport = ExtentManager.getInstance();
	public static ExtentTest extentTest;
	public static String browser;

	@BeforeSuite
	public void setUp() {
		if (driver == null) {
			try {
				fis = new FileInputStream(
						Paths.get("src/test/resources/properties/Config.properties").toAbsolutePath().toString());
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			try {
				config.load(fis);
				log.debug("Config file loaded!");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			try {
				fis = new FileInputStream(
						Paths.get("src/test/resources/properties/OR.properties").toAbsolutePath().toString());
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded!");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			if(System.getenv("browser") != null && !System.getenv().isEmpty()) {
				browser = System.getenv("browser");
			} else {
				browser = config.getProperty("browser");
			}
			config.setProperty("browser", browser);

			if (config.getProperty("browser").equals("firefox")) {
				if (config.getProperty("OS").equals("windows")) {
					System.setProperty("webdriver.gecko.driver", Paths
							.get("src\\test\\resources\\executables\\geckodriver.exe").toAbsolutePath().toString());
				} else if (config.getProperty("OS").equals("linux")) {
					System.setProperty("webdriver.gecko.driver",
							Paths.get("src/test/resources/executables/geckodriver").toAbsolutePath().toString());
				} else if (config.getProperty("OS").equals("macOS")) {
					System.setProperty("webdriver.gecko.driver",
							Paths.get("src/test/resources/executables/geckodrivermac").toAbsolutePath().toString());
				}
				driver = new FirefoxDriver();
				log.debug("Gecko driver launched!");
			} else if (config.getProperty("browser").equals("chrome")) {
				if (config.getProperty("OS").equals("windows")) {
					System.setProperty("webdriver.chrome.driver", Paths
							.get("src\\test\\resources\\executables\\chromedriver.exe").toAbsolutePath().toString());
				} else if (config.getProperty("OS").equals("linux")) {
					System.setProperty("webdriver.chrome.driver",
							Paths.get("src/test/resources/executables/chromedriver").toAbsolutePath().toString());
				} else if (config.getProperty("OS").equals("macOS")) {
					System.setProperty("webdriver.chrome.driver",
							Paths.get("src/test/resources/executables/chromedrivermac").toAbsolutePath().toString());
				}
				driver = new ChromeDriver();
				log.debug("Chrome driver launched!");
			} else if (config.getProperty("browser").equals("edge")) {
				if (config.getProperty("OS").equals("windows")) {
					System.setProperty("webdriver.edge.driver", Paths
							.get("src\\test\\resources\\executables\\msedgedriver.exe").toAbsolutePath().toString());
				} else if (config.getProperty("OS").equals("linux")) {
					System.setProperty("webdriver.edge.driver",
							Paths.get("src/test/resources/executables/msedgedriver").toAbsolutePath().toString());
				} else if (config.getProperty("OS").equals("macOS")) {
					System.setProperty("webdriver.edge.driver",
							Paths.get("src/test/resources/executables/msedgedriversmac").toAbsolutePath().toString());
				}
				driver = new EdgeDriver();
				log.debug("Edge driver launched!");
			}

			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to: " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts()
					.implicitlyWait(Duration.ofSeconds(Long.parseLong(config.getProperty("implicit.wait"))));
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		}
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		log.debug("Testsuite execution completed!");
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public void click(String locator) {
		if(locator.endsWith("_CSS")){
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		extentTest.log(LogStatus.INFO, "Clicking on: " + locator);
	}
	
	public void type(String locator, String value) {
		if(locator.endsWith("_CSS")){
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		extentTest.log(LogStatus.INFO, "Typing in: " + locator + " entered value as " + value);
	}
	
	public void select(String locator, String value) {
		WebElement dropdown = null;
		if(locator.endsWith("_CSS")){
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if(locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if(locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
        Select select;
        if (dropdown != null) {
            select = new Select(dropdown);
			select.selectByVisibleText(value);
        }

		extentTest.log(LogStatus.INFO, "Selecting from dropdown: " + locator + " value as " + value);
	}
	
	public static void verifyEquals(String expected, String actual) throws IOException, InterruptedException {
		try {
    		Assert.assertEquals(expected, actual);
		} catch (Throwable t) {
			Thread.sleep(1000);
			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure: " + t.getMessage() + "<br>");
	        Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName + " height=200 width=200></img></a>");
	        Reporter.log("<br>");
	        Reporter.log("<br>");
	        // Extent report
	        extentTest.log(LogStatus.FAIL, "Verification failed with exception: " + t.getMessage());
	    	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(TestUtil.screenshotName));
		}
	}

}
