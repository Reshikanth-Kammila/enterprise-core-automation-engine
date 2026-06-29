package com.engine.tests;

import com.engine.core.api.ApiClient;
import com.engine.core.driver.DriverManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class HybridWorkflowTest extends BaseTest {

    @Test
    public void verifyBackendInjectionAndFrontendNavigation() {
        System.out.println("--- PHASE 1: BACKEND DATA INJECTION VIA RESTASSURED ---");
        
        Map<String, String> userPayload = new HashMap<>();
        userPayload.put("name", "Automation Lead");
        userPayload.put("job", "SDET Architect");

        Response apiResponse = ApiClient.post("/api/users", userPayload);
        
        int statusCode = apiResponse.getStatusCode();
        String generatedId = apiResponse.jsonPath().getString("id");
        String assignedJob = apiResponse.jsonPath().getString("job");
        
        System.out.println("Backend Confirmed. Status Code: " + statusCode);
        System.out.println("System Generated Record ID: " + generatedId);
        
        Assert.assertEquals(statusCode, 201, "Backend record creation failed!");
        Assert.assertNotNull(generatedId, "Failed to fetch data reference token from backend.");
        Assert.assertEquals(assignedJob, "SDET Architect");

        System.out.println("--- PHASE 2: FRONTEND VALIDATION VIA SELENIUM WEBDRIVER ---");
        
        // Navigate to the target application UI
        DriverManager.getDriver().get("https://practice.expandtesting.com/");
        
        String frontendTitle = DriverManager.getDriver().getTitle();
        System.out.println("Frontend UI Page Title: " + frontendTitle);
        
        // Corrected assertion string to match the live website title precisely
        Assert.assertTrue(frontendTitle.contains("Automation Testing Practice Website"), 
                "Frontend landing page did not open or the website title has changed.");
        
        System.out.println("--- PHASE 3: BACKEND DATABASE VALIDATION VIA JDBC ---");
        /* * Note for Portfolio: In a live corporate environment, the below code executes 
         * to validate data persistence. It is commented out here because we do not 
         * possess the proprietary database credentials for the public practice UI.
         * * DatabaseManager.connect();
         * List<Map<String, Object>> dbRecord = DatabaseManager.executeQuery(
         * "SELECT * FROM users WHERE id = '" + generatedId + "'");
         * * Assert.assertEquals(dbRecord.get(0).get("job_title"), assignedJob, 
         * "Database record does not match API payload!");
         * DatabaseManager.disconnect();
         */
        System.out.println("Database architecture mapped and ready for internal DB credentials.");
    }
}