package com.example.asteroids;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu extends Application {
	// Purpose of this class: to create a menu screen for the Asteroids game, with a
	// background of stars on a canvas.
	// This class has six instance variables, divided into two for clarity:
	// The constants:
	private final int WIDTH = App.WIDTH; // WIDTH = an integer that represents the width of the game window.
	private final int HEIGHT = App.HEIGHT; // HEIGHT = an integer that represents the height of the game window.
	private static final int NUM_STARS = 150; // NUM_STARS = an integer that represents the number of stars to be
												// displayed on the canvas.
	private final String TITLE = "Asteroids Game"; // TITLE = string that represents the title of the window.

	// The object:
	private Canvas canvas; // canvas = Canvas object -> provides a surface for rendering graphics.
	// private GraphicsContext gc; // gc = GraphicsContext object -> used to draw on the canvas.
	private Stage primaryStage;
	private Stage stage;
	private Scene titleScene;
	private Scene startButtonScene;
	private Scene highScoresButtonScene;
	private StackPane stackPane;

	public VBox mainMenuScene(Stage primaryStage) {
		// create main menu container
		VBox mainMenu = new VBox(20);
		mainMenu.setAlignment(Pos.CENTER);

		// Create and format title label.
		Label titleLabel = new Label(TITLE);
		titleLabel.setStyle(
				"-fx-font-family: Audiowide; -fx-font-size: 60; -fx-text-fill: white; -fx-background-color: transparent;");
		mainMenu.getChildren().add(titleLabel);

		// Create and format start button.
		Button startButton = new Button("Start Game");     
        Buttons.setButton(startButton);

		// Event handling: start game when button is clicked.
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				App app = new App();
				app.createNewGame(primaryStage);
			}
		});
		// startButton.setOnAction(event -> startGame(primaryStage));
		mainMenu.getChildren().add(startButton);

		// Create and format hall of fame (high scores) button.
		Button highScoresButton = new Button("High Scores");    
        Buttons.setButton(highScoresButton);
		highScoresButton.setOnAction(event -> showHighScores());
		mainMenu.getChildren().add(highScoresButton);

		// Create and format info button.
		Button infoButton = new Button("How To Play");   
        Buttons.setButton(infoButton);
		infoButton.setOnAction(event -> howToPlay());
		mainMenu.getChildren().add(infoButton);

		// create and format exit button
		Button exitButton = new Button("Exit");
        Buttons.setButton(exitButton);
		// Event handling: start game when button is clicked.
		exitButton.setOnAction(event -> Platform.exit());
		mainMenu.getChildren().add(exitButton);

		// Set margin of the main menu -> raise bottom by 50.
		StackPane.setMargin(mainMenu, new Insets(0, 0, 50, 0));

		// Return main menu VBox.
		return mainMenu;
	}

	public void showHighScores() {
		// Create a new dialog (ii.e. info window) to display high scores.
		Dialog<Void> highScoresDialog = new Dialog<>();
		highScoresDialog.setTitle("High Scores");
		highScoresDialog.setHeaderText("Top 10 High Scores:");

		// Format the style of the dialog using CSS.
		DialogPane highScoresDialogPane = highScoresDialog.getDialogPane();
		highScoresDialogPane.setStyle("-fx-background-color: black; -fx-font-family: Silkscreen; -fx-font-size: 16;");

		// Format the style of the header region using CSS.
		Region highScoresHeaderRegion = (Region) highScoresDialogPane.lookup(".header-panel");
		highScoresHeaderRegion.setStyle("-fx-background-color: black; -fx-font-size: 20;");

		// Event listener: changes colour of header text to white when header region is
		// shown.
		highScoresDialog.setOnShown(event -> {
			Node highScoresHeader = highScoresDialogPane.lookup(".header-panel");
			if (highScoresHeader instanceof Pane) {
				Pane highScoresHeaderPane = (Pane) highScoresHeader;
				ObservableList<Node> highScoresCchildren = highScoresHeaderPane.getChildren();
				for (Node highScoresChild : highScoresCchildren) {
					if (highScoresChild instanceof Label) {
						Label highScoresHeaderLabel = (Label) highScoresChild;
						highScoresHeaderLabel.setStyle("-fx-text-fill: white;");
						break;
					}
				}
			}
		});

		// Create a ListView to display the high scores.
		ListView<String> listView = new ListView<>();
		listView.setPrefSize(WIDTH / 3, HEIGHT / 3);
		listView.setStyle("-fx-background-color: black;");

		// Load the high scores from a file and add them to the ListView.
		List<String> highScores = loadHighScoresFromFile();
		listView.getItems().addAll(highScores);

		// Create a VBox to hold the ListView.
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);
		vBox.getChildren().addAll(listView);

		// Set the VBox as the content of the dialog.
		highScoresDialog.getDialogPane().setContent(vBox);

		// Change the default button type do 'OK'.
		ButtonType highScoresOkButton = new ButtonType("OK", ButtonData.OK_DONE);
		highScoresDialog.getDialogPane().getButtonTypes().addAll(highScoresOkButton);

		// Format the style using CSS.
		Button okBtnHighScores = (Button) highScoresDialogPane.lookupButton(highScoresOkButton);
		okBtnHighScores
				.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #008000, #32CD32, #00FF7F);");

		// Event listener: change cursor style when hovering over the OK button.
		okBtnHighScores.setOnMouseEntered(event -> {
			okBtnHighScores.setCursor(Cursor.HAND);
		});
		okBtnHighScores.setOnMouseExited(event -> {
			okBtnHighScores.setCursor(Cursor.DEFAULT);
		});

		// Change style of the cells of the list using CSS.
		// Create custom cell factory -> cell factory generates + updates display of
		// each item of list of a list.
		listView.setCellFactory(list -> { // Use lambda expression to take a ListView object as input
			return new ListCell<String>() { // + return a new ListCell object.
				@Override
				protected void updateItem(String item, boolean empty) { // Protected: restrict access to the method to
																		// the same class, subclasses, and classes in
																		// the same package.
					// Implement updateItem() method from ListCell class -> sets the text + graphic
					// content of the cell.
					super.updateItem(item, empty); // Use 'super.updateItem' to preserve default behaviour of the
													// ListCell; otherwise cell will not be displayed properly.
					setText(item); // Set text to item's value;
					setTextFill(Color.WHITE); // Set text colour to white;
					setStyle("-fx-background-color: black;"); // Set background of cell to black.
				}
			};
		});
		// Show the dialog/high scores; wait until user closes the pane.
		highScoresDialog.showAndWait();
	}

	// Method for loading high scores from file.
	private List<String> loadHighScoresFromFile() {
		List<HighScores.Score> highScores = HighScores.getScores();
		Collections.sort(highScores, Collections.reverseOrder());
		List<String> formattedScores = new ArrayList<>();
		for (int i = 0; i < Math.min(highScores.size(), 10); i++) {
			HighScores.Score score = highScores.get(i);
			formattedScores.add(String.format("%d. %s: %d", i + 1, score.getPlayerName(), score.getScore()));
		}
		return formattedScores;
	}

	// Method to show playing instructions.
	private void howToPlay() {
		// Create Alert object to display message.
		Alert instructions = new Alert(AlertType.NONE, "1. Use the arrow keys to move your ship\n"
				+ "2. Use the space button to fire a bullet\n"
				+ "3. Use the shift key to activate hyperspace\n"
				+ "4. Aim for the asteroids and alien ship\n"
				+ "5. Avoid all enemies including the alien ship\n\n"
				+ "Good luck!");
		instructions.setHeaderText("How to Play");

		// Create OK button and add it to the Alert.
		ButtonType infoOkButton = new ButtonType("OK", ButtonData.OK_DONE);
		DialogPane infoDialogPane = instructions.getDialogPane();
		infoDialogPane.getButtonTypes().addAll(infoOkButton);

		// Format style of the OK button.
		Button okBtnInfo = (Button) infoDialogPane.lookupButton(infoOkButton);
		okBtnInfo.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #008000, #32CD32, #00FF7F);");

		// Event listener: change cursor to hand when hovering over the OK button.
		okBtnInfo.setOnMouseEntered(event -> {
			okBtnInfo.setCursor(Cursor.HAND);
		});
		okBtnInfo.setOnMouseExited(event -> {
			okBtnInfo.setCursor(Cursor.DEFAULT);
		});

		// Format style of message using CSS.
		DialogPane instructionsDialogPane = instructions.getDialogPane();
		instructionsDialogPane.setStyle("-fx-background-color: black; -fx-font-family: Silkscreen;");

		// Find the header region and format its style using CSS.
		Region instructionsHeaderRegion = (Region) instructionsDialogPane.lookup(".header-panel");
		instructionsHeaderRegion.setStyle("-fx-background-color: black; -fx-font-size: 16;");

		// Create Event Listener to iterate through the header label and change its text
		// colour to white.
		instructions.setOnShown(event -> { // Lambda function: event is triggered when instructions dialog is shown.
			Node instructionsHeader = instructionsDialogPane.lookup(".header-panel"); // Search for header region using
																						// CSS ('.header-panel') ->
																						// assign this to the variable
																						// instructionsHeader.
			if (instructionsHeader instanceof Pane) { // If the header region is a Pane:
				Pane instructionsHeaderPane = (Pane) instructionsHeader; // Create new reference to the existing Pane ->
																			// assign it to the variable
																			// instructionsHeader;
				ObservableList<Node> instructionsChildren = instructionsHeaderPane.getChildren(); // Get list of all the
																									// children nodes in
																									// the Header pane;
				for (Node instructionsChild : instructionsChildren) { // Iterate through all the children nodes in the
																		// list.
					if (instructionsChild instanceof Label) { // If the child node is a label:
						Label headerLabel = (Label) instructionsChild; // Create new reference to the same label ->
																		// assign it to the variable headerLabel;
						headerLabel.setStyle("-fx-text-fill: white;"); // Change text colour of the label to white.
						break; // Exit loop.
					}
				}
			}

			Node instructionsContent = instructionsDialogPane.lookup(".content"); // Search for instructions content
																					// dialog using CSS -> assign this
																					// to the variable
																					// instructionsContent.
			if (instructionsContent instanceof Label) { // If the Node created above is a label:
				Label instructionsContentLabel = (Label) instructionsContent; // Cast the node to a Label object ->
																				// assigns it to the variable
																				// instructionsContentLabel.
				instructionsContentLabel.setStyle("-fx-text-fill: white;"); // Set the text colour of the
																			// instructionsContentLabel to white.
			}
		});

		// Wait until user either closes Alert window or clicks the OK button.
		instructions.showAndWait();
	}

	// Star class for background animation.
	class Star {
		// Instance variables:
		private double x; // X-Coordinate of the star;
		private double y; // Y-Coordinate of the star;
		private double speed; // Speed of star's movement;
		public double angle; // Angle at which star is moving;
		private double direction; // Direction star is going in;
		private Color color; // Colour of star;
		private double size; // Size of star.

		// Constructor for the Star class -> take all instance variables as arguments.
		public Star(double x, double y, double speed, Color color, double size, double direction, double angle) {
			// Initialise all instance variables.
			this.x = x;
			this.y = y;
			this.direction = direction;
			this.angle = angle;
			this.color = color;
			this.size = size;
			this.speed = Math.random() * 2 + 1; // Random speed between 1 and 3 -> generated by Math.random() method.
		}

		// Update method for Star class.
		public void update() {
			// Coordinates X and Y -> update based on speed and direction.
			x += speed * Math.cos(direction);
			y += speed * Math.sin(direction);

			// If star goes off-screen:
			if (x < -size || x > WIDTH + size || y < -size || y > HEIGHT + size) {
				// Reset star position (coordinates X and Y) randomly.
				x = Math.random() * WIDTH;
				y = Math.random() * HEIGHT;
				speed = Math.random() * 10 + 1; // Generate new speed randomly between 1 and 10.
				direction = Math.random() * Math.PI * 2; // Generate new angle randomly between 0 and 2π.
			}

			// Variables vx and vy = velocity of star going in directions x and y ->
			// dependent on angle and speed.
			double vx = Math.cos(Math.toRadians(angle)) * speed;
			double vy = Math.sin(Math.toRadians(angle)) * speed;
			x -= vx;
			y += vy;

			if (x < -size || y < -size || y > HEIGHT + size) {
				// If the star goes off-screen, wrap around to the other side.
				x = WIDTH + size;
				y = Math.random() * HEIGHT;
				angle = Math.random() * 360; // Generate random angle for a new direction.
				speed = Math.random() * 2 + 1; // Generate random speed.
			}
		}

		// Method to draw stars on the canvas -> takes GraphicsContext as object, gc as
		// parameter.
		public void draw(GraphicsContext gc) {
			gc.setFill(color);
			gc.fillOval(x, y, size, size); // Draw oval -> represents star.
		}
	}

	public void createMenu(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.hide();
		stage = new Stage();
		stage.setWidth(primaryStage.getWidth());
		stage.setHeight(primaryStage.getHeight());
		// stage.setX(primaryStage.getX());
		// stage.setY(primaryStage.getY());
		// start(stage);
		try {
			start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	@Override
	public void start(Stage primaryStage) {
		// Create the Canvas and GraphicsContext objects.
		this.canvas = new Canvas(WIDTH, HEIGHT); // Instantiates new Canvas object with specified width and height +
													// initialises it with the WIDTH and HEIGHT values.
		GraphicsContext gc = canvas.getGraphicsContext2D(); // Instantiates new GraphicsContext object by calling the
															// getGraphicsContext2D() method of the Canvas object
															// 'canvas' + initialises it with the returned value.

		// Create title label and three buttons for start, high scores, and info (how to
		// play).
		// The label + every button have their own Scene -> Scene = class that
		// represents a container for a scene.
		// Add style sheets to each Scene in order to access Google Fonts API.
		Label titleLabel = new Label(TITLE);
		this.titleScene = new Scene(titleLabel);
		titleScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Audiowide");

		Button startButton = new Button("Start Game");
		this.startButtonScene = new Scene(startButton);
		startButtonScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Silkscreen");

		Button highScoresButton = new Button("High Scores");
		this.highScoresButtonScene = new Scene(highScoresButton);
		highScoresButtonScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Silkscreen");

		// Create an EventHandler to display a "How to Play" dialog box when the
		// infoButton is clicked.
		Button infoButton = new Button("How to Play");
		infoButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				howToPlay();
			}
		});

		// Button exitButton = new Button("Exit");

		// Create ArrayList of Star objects.
		// Each star = a white dot on the menu screen, placed anywhere on the screen/of
		// different sizes/going in random directions and speed.
		ArrayList<Star> stars = new ArrayList<>();
		for (int i = 0; i < NUM_STARS; i++) {
			double x = Math.random() * WIDTH;
			double y = Math.random() * HEIGHT;
			double speed = Math.random() * 4 + 1;
			Color color = Color.WHITE;
			double size = Math.random() * 3 + 1;
			double direction = Math.random() * 2 * Math.PI;
			double angle = Math.random() * 360;
			stars.add(new Star(x, y, speed, color, size, direction, angle));
		}
		// Create StackPane to add menu items on top of the background stars.
		this.stackPane = new StackPane(canvas);
		stackPane.getChildren().add(mainMenuScene(primaryStage));
		stackPane.setAlignment(Pos.TOP_LEFT);
		// stackPane.setAlignment(Pos.CENTER);
		Scene backgroundScene = new Scene(stackPane); // New scene 'backgroundScene': stackPane = its root node.
		backgroundScene.getStylesheets().addAll("https://fonts.googleapis.com/css2?family=Silkscreen",
				"https://fonts.googleapis.com/css2?family=Audiowide");

		// Create Timeline object to update star position and redraw them every 50ms.
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
			// Clear the canvas and set a black background.
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, WIDTH, HEIGHT);

			// Update and redraw the stars.
			for (Star star : stars) {
				star.update();
				star.draw(gc);
			}
		}));
		// Run timeline indefinitely once it is started.
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		// Create primaryStage window to display all scenes.
		primaryStage.setTitle(TITLE);
		primaryStage.setScene(titleScene);
		// primaryStage.show();
		primaryStage.setScene(startButtonScene);
		// primaryStage.show();
		primaryStage.setScene(backgroundScene);
		primaryStage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}

}