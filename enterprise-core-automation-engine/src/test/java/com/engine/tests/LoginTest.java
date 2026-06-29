package com.engine.tests;

import com.engine.core.driver.DriverManager;
import com.engine.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void verifySuccessfulAuthentication() {
        // 1. Navigate to the specific target page
        DriverManager.getDriver().get("https://practice.expandtesting.com/login");

        // 2. Initialize the Page Object
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());

        // 3. Execute the Business Flow
        System.out.println("Executing login flow...");
        loginPage.performLogin("practice", "SuperSecretPassword!");

        // 4. Assert the Outcome
        String actualMessage = loginPage.getAlertMessage();
        System.out.println("System Alert: " + actualMessage);
        
        Assert.assertTrue(actualMessage.contains("You logged into a secure area!"), 
                "The login failed or the success message did not appear.");
    }
}