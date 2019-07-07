package stepdefinition;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.ScenarioImpl;
import gherkin.formatter.model.Result;
import global.GlobalVariables;
import main.CucumberRunner;
import pages.LoginPage;
import util.MyJiraClient;

public class Login extends CucumberRunner {

	LoginPage loginPage = new LoginPage();
	String stepsToReproduce="";

	@Before
	public void before(Scenario scenario)
	{
		
		System.out.println("Before");
	   GlobalVariables.scenario=scenario; 
	   System.out.println(scenario.getName());
	   GlobalVariables.stepstoReproduce="";
	}
	
	
	@After
	public void tearDown(Scenario scenario) throws IOException
	{
		
		if (scenario.isFailed()) {
			//String filePath = captureScreenshot("Application Screenshot");
			 MyJiraClient myJiraClient = new MyJiraClient("mahammad.rasheed", "Ocbc@2020", "http://localhost:8081/");
					myJiraClient.RaiseJiraIssue(scenario.getName(),GlobalVariables.stepstoReproduce,logError(scenario) , "mahammad.rasheed@nttdata.com", GlobalVariables.currentScreenshot);
		}

		driver.quit();
	}
	
	private static String logError(Scenario scenario) {
		   Field field = FieldUtils.getField(((ScenarioImpl) scenario).getClass(), "stepResults", true);
		   field.setAccessible(true);
		   try {
		       ArrayList<Result> results = (ArrayList<Result>) field.get(scenario);
		       for (Result result : results) {
		           if (result.getError() != null)
		             return  result.getError().getMessage();
		       }
		   } catch (Exception e) 
		   {
		   
		   }
		return "";
		}

	
	@Given("^I open the app for the first time in my mobile device$")
	public void i_open_the_app_for_the_first_time_in_my_mobile_device() 
	{
		GlobalVariables.stepstoReproduce+="\nI open the app for the first time in my mobile device";
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		openApp();
		implicitWait(5);
		//handleOnetokenBanner();
	}
	
	@When("^I open the application$")
	public void i_open_the_application() 
	{
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		GlobalVariables.stepstoReproduce+="\nI open the application";
	}
	
	@Then("^I should see push notification inbox icon$")
	public void i_should_see_push_notification_inbox_icon()  {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		GlobalVariables.stepstoReproduce+="\nI should see push notification inbox icon";
		Assert.assertTrue(loginPage.isNotificationIconPresent(), "Notification icon on Login page is not displayed");
	}
	
	@Then("^I should see Greeting label$")
	public void i_should_see_Greeting_label()  {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		GlobalVariables.stepstoReproduce+="\nI should see Greeting labe";
		Assert.assertTrue(loginPage.isGreetingLabelPresent(), "Greering Label on Login page is not displayed");
	}
	
	@Then("^I should see Acces code/Bio Metric login fields$")
	public void i_shoudl_see_Acces_code_Bio_Metric_login_fields() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		GlobalVariables.stepstoReproduce+="\nI should see Acces code/Bio Metric login fields";
		Assert.assertTrue(loginPage.isBioMetricOptionPresent(), "BioMetric Label on Login page is not displayed");
	}
	
	@Then("^I should see Pre login Access tray items$")
	public void i_should_see_Pre_login_Access_tray_items()  {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		GlobalVariables.stepstoReproduce+="\nI open the app for the first time in my mobile device";
		Assert.assertTrue(loginPage.isBioMetricOptionPresent(), "Pre login Access Tray option is not displayed");
	}
	
	@When("^I click on the push notification icon$")
	public void i_click_on_the_push_notification_icon() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		GlobalVariables.stepstoReproduce+="\nI click on the push notification icon";
		loginPage.clickPushNotificationButton();
	}
	
	@Then("^I should be able to navigate to Notifications screen$")
	public void i_should_be_able_to_navigate_to_Notifications_screen()  {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		GlobalVariables.stepstoReproduce+="\nI should be able to navigate to Notifications screen";
		Assert.assertTrue(loginPage.isPushNotificationPageExists(), "Notifications screen is not displayed on click off Push notification button");
	}
	
	@When("^I click on the more services button$")
	public void i_click_on_the_more_services_button()  {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		GlobalVariables.stepstoReproduce+="\n\nI click on the more services button";
		loginPage.clickMoreServicesButton();
	}
	
	@Then("^I should be able to navigate to more services screen$")
	public void i_should_be_able_to_navigate_to_more_services_screen()  {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		GlobalVariables.stepstoReproduce+="\nI should be able to navigate to more services screene";
		Assert.assertTrue(loginPage.isMoreServicesPageExists(), "More Services screen is not displayed on click off Push notification button");
	}
	
	@When("^I enter test(.+) and test(.+)$")
	public void i_enter_test_and_test(String userName, String password) 
	{
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		GlobalVariables.stepstoReproduce+="\nI enter username :"+userName+" and  Password : "+password+"";
		loginPage.enterUserCredentails(userName,password);
	}
	
	@Then("^I should be able to see the home page$")
	public void i_should_be_able_to_see_the_home_page()
	{
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		
		GlobalVariables.stepstoReproduce+="\nI should be able to see the home page";
		if(!loginPage.isHomePageExists())
		Assert.fail("Home Page is not displayed after entering user name and password");
		
	}

}
