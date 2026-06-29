package com.engine.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static void initDriver() {
        if (driverThread.get() == null) {
            ChromeOptions options = new ChromeOptions();
            
            // Enterprise CI/CD Cloud Arguments
            options.addArguments("--headless=new"); // Runs browser silently in the background
            options.addArguments("--window-size=1920,1080"); // Fakes a full 1080p monitor
            options.addArguments("--no-sandbox"); // Bypasses OS security model limits in Linux
            options.addArguments("--disable-dev-shm-usage"); // Prevents RAM memory crashes in Docker/CI
            
            WebDriver driver = new ChromeDriver(options);
            driverThread.set(driver);
        }
    }

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static void quitDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
        }
    }
}