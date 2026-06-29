package com.engine.tests;

import com.engine.core.driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod
    public void setUp() {
        // This runs automatically BEFORE every single @Test method, launching the browser
        DriverManager.initDriver();
    }

    @AfterMethod
    public void tearDown() {
        // This runs automatically AFTER every single @Test method, safely closing the browser
        DriverManager.quitDriver();
    }
}