package com.sangarius.javafxcrudapp;

import java.util.List;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
  private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
  private static final String DB_USERNAME = "your_username";
  private static final String DB_PASSWORD = "your_password";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("JavaFX CRUD App");

    Label statusLabel = new Label("Loading...");
    ProgressBar progressBar = new ProgressBar();
    progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

    VBox root = new VBox(statusLabel, progressBar);
    Scene scene = new Scene(root, 300, 200);

    primaryStage.setScene(scene);
    primaryStage.show();

    // Create and execute data loading task
    DataLoader dataLoader = new DataLoader(DB_URL, DB_USERNAME, DB_PASSWORD);
    progressBar.progressProperty().bind(dataLoader.progressProperty());
    dataLoader.setOnSucceeded(
        new EventHandler<WorkerStateEvent>() {
          @Override
          public void handle(WorkerStateEvent event) {
            List<String> data = dataLoader.getValue();
            statusLabel.setText("Data loaded successfully");
            // Process loaded data here
            // For example, display data in UI
          }
        });
    dataLoader.setOnFailed(
        new EventHandler<WorkerStateEvent>() {
          @Override
          public void handle(WorkerStateEvent event) {
            statusLabel.setText("Error loading data: " + dataLoader.getException().getMessage());
          }
        });
    new Thread(dataLoader).start(); // Start data loading task in a new thread
  }
}
