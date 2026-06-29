package com.engine.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // 1. Locators (Strictly private to prevent test scripts from modifying them)
    private By usernameInput = By.id("username");
    private By passwordInput = By.id("password");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By flashMessage = By.id("flash");

    // 2. Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // 3. Page Actions (Business logic exposed to the test scripts)
    public void performLogin(String username, String password) {
        enterText(usernameInput, username);
        enterText(passwordInput, password);
        click(loginButton);
    }

    public String getAlertMessage() {
        return getText(flashMessage);
    }
}