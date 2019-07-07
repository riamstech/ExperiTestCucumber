Feature: Login Page 

  
    @Regression
    Scenario: Verify Push notification functionality
      Given I open the app for the first time in my mobile device
      When I open the application
      When I click on the push notification icon
      Then I should be able to navigate to Notifications screen

     @Regression
    Scenario: Verify More service functionality
      Given I open the app for the first time in my mobile device
      When I open the application
      When I click on the more services button
      Then I should be able to navigate to more services screen
      
    @Regression
   Scenario: Verify Pre-login page components
      Given I open the app for the first time in my mobile device
      When I open the application
      Then I should see push notification inbox icon
      Then I shoudl see Acces code/Bio Metric login fields
      And I should see Pre login Access tray items

      
    @Regression
    Scenario Outline: Verify the login functionality for valid access code and password
      Given I open the app for the first time in my mobile device
      When I open the application
      When I enter <username> and <password>
      Then I should be able to see the home page
      
      Examples:
      |username|password|
      |test123 |test124 |
      |test124 |test125 |
     
      
      