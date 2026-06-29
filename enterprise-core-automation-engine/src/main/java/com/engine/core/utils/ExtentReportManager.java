package com.engine.core.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    public static synchronized ExtentReports initReport() {
        if (extent == null) {
            extent = new ExtentReports();
            // Define the output path for the interactive HTML report file
            ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReports/AutomationReport.html");
            
            // Configure corporate reporting dashboard aesthetics
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Enterprise Automation Execution Report");
            spark.config().setReportName("Regression Suite Execution Results");
            
            extent.attachReporter(spark);
            extent.setSystemInfo("Environment", ConfigReader.getProperty("env").toUpperCase());
            extent.setSystemInfo("Execution Engineer", "SDET Architect");
        }
        return extent;
    }

    public static synchronized ExtentTest startTest(String testName) {
        ExtentTest test = initReport().createTest(testName);
        testThreadLocal.set(test);
        return test;
    }

    public static synchronized ExtentTest getTest() {
        return testThreadLocal.get();
    }

    public static synchronized void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}