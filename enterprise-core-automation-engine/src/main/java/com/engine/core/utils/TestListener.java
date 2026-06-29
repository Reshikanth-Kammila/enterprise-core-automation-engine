package com.engine.core.utils;

import com.engine.core.driver.DriverManager;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Initializing Test Suite Execution Reports...");
        ExtentReportManager.initReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting Test Case: " + result.getMethod().getMethodName());
        ExtentReportManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().log(Status.PASS, "Test Case Passed Successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.getTest().log(Status.FAIL, "Test Case Failed: " + result.getThrowable());
        
        // Advanced Fallback: Check if the thread has an active browser session before capturing a screenshot
        if (DriverManager.getDriver() != null) {
            try {
                String base64Screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BASE64);
                
                // Embed the visual screenshot directly into the HTML dashboard log row
                ExtentReportManager.getTest().addScreenCaptureFromBase64String(
                        base64Screenshot, "Failure System State Screenshot");
                System.out.println("Failure screenshot successfully embedded into execution report.");
            } catch (Exception e) {
                System.out.println("Driver context found, but failed to capture screen state: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().log(Status.SKIP, "Test Case Skipped.");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Flushing final report dashboard to disk storage...");
        ExtentReportManager.flushReport();
    }
}