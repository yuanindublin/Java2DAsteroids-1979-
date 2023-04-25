package com.example.asteroids;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
//import java.util.logging.Handler;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameOver extends Application {
	
	private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final String TITLE = "Asteroids Game";
    private Stage primaryStage;    
    private Stage stage;

    private Canvas canvas;
    private AtomicInteger points;
    
	private VBox gameOver(Stage primaryStage, AtomicInteger points) {
        // create main menu container
        VBox gameOver = new VBox(20); // spacing = 20
        gameOver.setAlignment(Pos.CENTER); // center all elements in the VBox        
        this.points = points;

        // create and format title label
        Label titleLabel = new Label("GAME OVER");
        titleLabel.setStyle("-fx-font-family: Silkscreen; -fx-font-size: 30; -fx-background-color: transparent; -fx-text-fill: white;");
        gameOver.getChildren().add(titleLabel);

        // display score
        Label scoreLabel = new Label("Your Score: " + points);
        scoreLabel.setStyle("-fx-font-family: Silkscreen; -fx-font-size: 30; -fx-background-color: transparent; -fx-text-fill: white;");
		gameOver.getChildren().add(scoreLabel);

        // create and format enter name label
        Label enterNameLabel = new Label("Enter Your Name:");
        enterNameLabel.setStyle("-fx-font-family: Silkscreen; -fx-font-size: 30; -fx-background-color: transparent; -fx-text-fill: white;");
        gameOver.getChildren().add(enterNameLabel);

        // create and format text field for entering name
        TextField nameTextField = new TextField();
        nameTextField.setStyle("-fx-font-family: Silkscreen; -fx-font-size: 30; -fx-background-color: transparent; -fx-text-fill: white;");
        nameTextField.setMaxWidth(350);
        nameTextField.setPromptText("Enter your name");
        gameOver.getChildren().add(nameTextField);

        // create and format start button
        Button saveButton  = new Button("Save");   
        Buttons.setButton(saveButton);
        saveButton .setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                // get player's name and score
                String name = nameTextField.getText();
                int score = points.get();

                // validate player's name
                if (name == null || name.trim().isEmpty()) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Cannot access your name.");
                    alert.showAndWait();
                    return;          
                }
                HighScores.addScore(name,score);                     
                Menu menu = new Menu();
                menu.createMenu(primaryStage);
                }

        });
        // startButton.setOnAction(event -> startGame(primaryStage));
        gameOver.getChildren().add(saveButton);      
        
        // create and format start button
        Button startButton = new Button("New Game");       
        Buttons.setButton(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                App app = new App();
                app.createNewGame(primaryStage);
            }
        });
        gameOver.getChildren().add(startButton); 

        // create and format back to menu button
        Button menuButton = new Button("Back to Menu");        
        Buttons.setButton(menuButton);
        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Menu menu = new Menu();
                menu.createMenu(primaryStage);
            }
        });
        gameOver.getChildren().add(menuButton);  

        // create and format exit button
        Button exitButton = new Button("Exit");
        Buttons.setButton(exitButton);
        exitButton.setOnAction(event -> Platform.exit());
        gameOver.getChildren().add(exitButton);

        return gameOver;
    }

    // Create Game Over screen
    public void createGameOver(Stage primaryStage, AtomicInteger points){ 
        this.points = points;
        this.primaryStage = primaryStage;
        this.primaryStage.hide();
        stage = new Stage();
        stage.setWidth(primaryStage.getWidth());
        stage.setHeight(primaryStage.getHeight());
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

	@Override
	public void start(Stage primaryStage) {
		// create the canvas and set its width and height
        this.canvas = new Canvas(WIDTH, HEIGHT);

		Button saveButton = new Button("Save");
		Button startButton = new Button("New Game");
		Button menuButton = new Button("Back to Menu");
		Button exitButton = new Button("Exit");

		// Initialise root container.
        BorderPane rootContainer = new BorderPane();
        // rootContainer.setCenter(gameOver(primaryStage));
        rootContainer.setCenter(gameOver(primaryStage,points));
        Scene scene = new Scene(rootContainer, WIDTH, HEIGHT);

        // set background color
        rootContainer.setStyle("-fx-padding: 10; -fx-background-color: black;");
        rootContainer.getChildren().addAll(saveButton,startButton, menuButton, exitButton);

		// set the title of the window
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

	}

	// public static void main(String args[]) {
	// 	launch(args);
	// }

}