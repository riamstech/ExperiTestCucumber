package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import main.CucumberRunner;

public class LoginPage extends CucumberRunner {

	public boolean isNotificationIconPresent() {
		try {
			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
			return driver.findElement(By.xpath("//*[@id='noti_btn']")).isDisplayed(); // need to change this.

		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;

	}

	public boolean isGreetingLabelPresent() {
		try {
			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
			return driver.findElement(By.xpath("//*[@id='noti_btn']")).isDisplayed(); // need to change this.

		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}

	public boolean isBioMetricOptionPresent() {
		try {

			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
			return driver.findElement(By.id("iv_onetouch")).isDisplayed();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}

	public boolean isPreloginAccessTrayPresent() {
		try {

			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
			return driver.findElement(By.id("iv_onetouch")).isDisplayed(); // need to change this.
		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}

	public void clickPushNotificationButton() {
		try {

			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
			  driver.findElement(By.xpath("//*[@id='noti_btn']")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public boolean isPushNotificationPageExists() {
		try {

			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
			return driver.findElement(By.id("notification_toolbar_title")).isDisplayed(); // need to change this.
		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}

	public void clickMoreServicesButton() {
		try {

			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
			driver.findElement(By.xpath("//*[@id='next_btn']")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public boolean isMoreServicesPageExists() {
		try {

			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
			return driver.findElement(By.id("title_services_tv")).isDisplayed(); // need to change this.
		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}

	public LoginPage enterUserCredentails(String userName, String password) {
		try {
			new WebDriverWait(driver, 10)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='pin_edt']")));
			driver.findElement(By.xpath("//*[@id='access_code_edt']")).sendKeys(userName);

			driver.findElement(By.xpath("//*[@id='pin_edt']")).sendKeys(password);
			driver.findElement(By.xpath("//*[@text='Login']")).click();

			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
			return this;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public void clickLoginButton() {

		try {

			driver.findElement(By.id("email_sign_in_button")).click();
			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public boolean isHomePageExists() {
		try

		{
			captureScreenshot(new Exception().getStackTrace()[0].getMethodName());
			//new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("homepage")));
			return driver.findElement(By.id("homepage")).isDisplayed(); // need to change this.
			//return true;

		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return false;
	}

}