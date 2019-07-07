package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.google.common.io.Files;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import global.GlobalVariables;
import helpers.ReportHelper;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import net.bytebuddy.agent.builder.AgentBuilder.CircularityLock.Global;
import util.MyJiraClient;

@CucumberOptions(strict = true, monochrome = true, features = "src/test/resources/features", glue = "stepdefinition", format = {
		"pretty", "json:target/cucumber.json" }, tags = { "@Regression,@JunitScenario,@TestngScenario" })

public class CucumberRunner extends AbstractTestNGCucumberTests {

	public static Properties config = null;
	public static URL url;
	public static DesiredCapabilities capabilities;
	public static AndroidDriver<AndroidElement> driver;

	public void LoadConfigProperty() throws IOException {
		config = new Properties();
		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "//src//test//resources//config//config.properties");
		config.load(ip);
	}

	public void configureDriverPath() throws IOException {
		if (System.getProperty("os.name").startsWith("Linux")) {
			/*
			 * String firefoxDriverPath = System.getProperty("user.dir") +
			 * "/src/test/resources/drivers/linux/geckodriver";
			 * System.setProperty("webdriver.gecko.driver", firefoxDriverPath); String
			 * chromeDriverPath = System.getProperty("user.dir") +
			 * "/src/test/resources/drivers/linux/chromedriver";
			 * System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			 */
		}
		if (System.getProperty("os.name").startsWith("Mac")) {
			/*
			 * String firefoxDriverPath = System.getProperty("user.dir") +
			 * "/src/test/resources/drivers/mac/geckodriver";
			 * System.setProperty("webdriver.gecko.driver", firefoxDriverPath); String
			 * chromeDriverPath = System.getProperty("user.dir") +
			 * "/src/test/resources/drivers/mac/chromedriver";
			 * System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			 */}
		if (System.getProperty("os.name").startsWith("Windows")) {
			/*
			 * String firefoxDriverPath = System.getProperty("user.dir") +
			 * "//src//test//resources//drivers//windows//geckodriver.exe";
			 * System.setProperty("webdriver.gecko.driver", firefoxDriverPath); String
			 * chromeDriverPath = System.getProperty("user.dir") +
			 * "//src//test//resources//drivers//windows//chromedriver.exe";
			 * System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			 */
		}
	}

	public void openApp() {
		try {
			// loads the config options
			LoadConfigProperty();
			// configures the driver path
			configureDriverPath();
			if (config.getProperty("MoibilePlatForm").toString().equalsIgnoreCase("Android")) {
				final String URL_STRING = config.getProperty("AppiumServer");

				url = new URL(URL_STRING);

				capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, config.getProperty("MoibilePlatForm"));
				capabilities.setCapability(MobileCapabilityType.UDID, "WCR7N18822001158");
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Mobile Automation");
				capabilities.setCapability("autoGrantPermissions", true);
				// capabilities.setCapability("appPackage",
				// "com.android.packageinstaller.permission.ui.GrantPermissionsActivity");
				/*
				 * capabilities.setCapability("unlockType","pin");
				 * capabilities.setCapability("unlockKey","887733");
				 */
				capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.ocbc.mobile");
				capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
						"com.ocbc.mib.activities.ActivitySplash");

				driver = new AndroidDriver(url, capabilities);
				// unlockDevice(driver);

			} else if (config.getProperty("MoibilePlatForm").equals("iOS")) {
				/*
				 * ChromeOptions options = new ChromeOptions();
				 * options.addArguments("--headless"); options.addArguments("--disable-gpu");
				 * options.addArguments("--no-sandbox");
				 * options.addArguments("--disable-dev-shm-usage");
				 * options.setExperimentalOption("useAutomationExtension", false); driver = new
				 * ChromeDriver(options);
				 */
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void implicitWait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public void explicitWait(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 3000);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void pageLoad(int time) {
		driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
	}

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	public void unlockDevice(AndroidDriver<AndroidElement> driver) {
		if (((AndroidDriver) driver).isDeviceLocked()) {
			System.out.println("Device is locked so unlocking it");
			((AndroidDriver) driver).unlockDevice();

		} else {
			System.out.println("Device is not locked");
		}
	}

	public static String currentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String cal1 = dateFormat.format(cal.getTime());
		return cal1;
	}

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception {

	}

	protected void handleOnetokenBanner() {
		driver.findElement(By.id("intro_got_it_tv")).click();
		sleep(4000);
		driver.findElement(By.id("intro_got_it_tv")).click();
		sleep(4000);
		driver.findElement(By.id("intro_got_it_tv")).click();
		sleep(4000);
		driver.findElement(By.id("showcase_noti_btn")).click();
		sleep(4000);
		driver.findElement(By.id("notification_close_icon")).click();

	}

	@AfterClass(alwaysRun = true)
	public void takeScreenshot() throws IOException
	{
		/*
		 * File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		 * File trgtFile = new File(System.getProperty("user.dir") +
		 * "//screenshots/screenshot.png"); trgtFile.getParentFile().mkdir();
		 * trgtFile.createNewFile(); Files.copy(scrFile, trgtFile);
		 */

		//driver.quit();

	}

	@AfterSuite(alwaysRun = true)
	public void generateHTMLReports() {
		
		System.out.println("after suite");
		ReportHelper.generateCucumberReport();
		driver.quit();
	}

	

	public void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String captureScreenshot(String functionName) {
		try {

			File imageFile = driver.getScreenshotAs(OutputType.FILE);
			GlobalVariables.currentScreenshot = imageFile.getAbsolutePath();

			String failureImageFileName = functionName
					+ new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime()) + ".jpeg";
			File failureImageFile = new File("C:\\screenshots\\" + failureImageFileName);
			failureImageFile.getParentFile().mkdir();
			try {
				failureImageFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Files.copy(imageFile, failureImageFile);
				return failureImageFile.getAbsolutePath();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
