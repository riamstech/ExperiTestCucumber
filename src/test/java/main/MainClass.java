package main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;

public class MainClass {

	  public static URL url;
	  public static DesiredCapabilities capabilities;
	  public static AndroidDriver<AndroidElement> driver;
	public static void main(String[] args) throws IOException
	{
	
		
		System.out.println(new Exception().getStackTrace()[0].getMethodName());
		final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
	    url = new URL(URL_STRING);
	    
	    capabilities = new DesiredCapabilities();
	    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "OnePlus 5");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Mobile Automation");
		/*
		 * capabilities.setCapability("unlockType","pin");
		 * capabilities.setCapability("unlockKey","887733");
		 */
        capabilities.setCapability(MobileCapabilityType.APP, "C:\\DevOps\\testng-cucumber-master\\src\\test\\resources\\apps\\OCBC_Bank.apk");
       
        driver = new AndroidDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
       
        
        if (((AndroidDriver)driver).isDeviceLocked())
        {
        	System.out.println("Device is locked so unlocking it");
        	((AndroidDriver)driver).unlockDevice();
            	
        }
        else
        {
        	System.out.println("Device is not locked");
        }
        
//        Dimension size = driver.manage ()
//        	    .window ()
//        	    .getSize ();
//        	int startX = size.getWidth () / 2;
//        	int startY = size.getHeight () / 2;
//        	int endX = 0;
//        	int endY = (int) (startY * -1 * 0.75);
//        	TouchAction action = new TouchAction (driver);
//        	action.press(PointOption.point(startX, startY))
//        	    .moveTo (PointOption.point(endX, endY))
//        	    .release ()
//        	    .perform ();
        

      File file  = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(file, new File("C:/temp/Screenshot.jpg"));


        driver.removeApp("io.appium.android.apis");
       // driver.resetApp();
        
        
	}

}
