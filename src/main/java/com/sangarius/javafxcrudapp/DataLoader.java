package com.sangarius.javafxcrudapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;

public class DataLoader extends Task<List<String>> {
  private final String url;
  private final String username;
  private final String password;

  public DataLoader(String url, String username, String password) {
    this.url = url;
    this.username = username;
    this.password = password;
  }

  @Override
  protected List<String> call() throws Exception {
    updateMessage("Loading data from database...");
    List<String> data = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM your_table")) {

      while (resultSet.next()) {
        // Assuming your_table has a column named 'name'
        String name = resultSet.getString("name");
        data.add(name);
      }

      // Simulate delay for loading data
      Thread.sleep(2000);

      updateMessage("Data loaded successfully");
    } catch (SQLException | InterruptedException e) {
      updateMessage("Error loading data: " + e.getMessage());
      e.printStackTrace();
    }

    return data;
  }
}
