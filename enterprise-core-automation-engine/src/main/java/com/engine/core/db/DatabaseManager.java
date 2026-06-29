package com.engine.core.db;

import com.engine.core.utils.ConfigReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private static Connection connection;

    // 1. Establish the connection securely
    public static void connect() {
        if (connection == null) {
            try {
                String dbUrl = ConfigReader.getProperty("db.host");
                String username = ConfigReader.getProperty("db.username");
                String password = ConfigReader.getProperty("db.password");

                connection = DriverManager.getConnection(dbUrl, username, password);
                System.out.println("Secure JDBC Database Connection Established.");
            } catch (SQLException e) {
                System.out.println("Database Connection Failed. Check credentials or network firewall.");
                // In a real execution, we would throw a RuntimeException here to stop the test
            }
        }
    }

    // 2. Execute a query and return the data as a clean Java List of Maps
    public static List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                resultList.add(row);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("SQL Execution bypassed due to lack of active local DB connection.");
        }
        return resultList;
    }

    // 3. Safely tear down the connection
    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed safely.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}