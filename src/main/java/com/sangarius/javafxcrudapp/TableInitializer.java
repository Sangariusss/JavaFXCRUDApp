package com.sangarius.javafxcrudapp;

import com.github.javafaker.Faker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableInitializer {
  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/your_database";
    String user = "your_username";
    String password = "your_password";

    int numberOfRecords = 50;

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
      Faker faker = new Faker();
      String sql = "INSERT INTO your_table (column1, column2, ...) VALUES (?, ?, ...)";
      PreparedStatement statement = conn.prepareStatement(sql);

      for (int i = 0; i < numberOfRecords; i++) {
        statement.setString(1, faker.name().firstName());
        statement.setString(2, faker.name().lastName());
        // Set other columns as needed
        statement.executeUpdate();
      }

      System.out.println("Records inserted successfully");
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getMessage());
    }
  }
}
