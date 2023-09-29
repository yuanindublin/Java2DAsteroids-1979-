package com.example.asteroids;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class App extends Application {

    private Canvas canvas;
    private GraphicsContext gc;
    private Pane pane;
    private Scene scene;
    private Stage primaryStage;
    private Stage stage;
    private Map<KeyCode, Boolean> pressedKeys = new HashMap<>(); // Create a map to store the state of pressed keys

    private static final Random Random = new Random();
    private static final int NUM_PARTICLES = 1000; // Number of star particles of the background
    public static int WIDTH = 800; // the width and height of the game window
    public static int HEIGHT = 600; // the width and height of the game window
    public AtomicInteger points; // Create an atomic integer to store the points
    private AtomicInteger level; // Create an atomic integer to store the level
    private static int lasthighscores;   // Create an integer to store the last highscore 
    private boolean canShoot = true; // We shoot only once when keeping the space bar pressed
    private boolean canCollide = true; // Invincible for 2 second when placed back in the game safely
    private Timeline flashtimeline; // Timeline to make the ship flash when invincible
    private Timeline invincibleTimeline; // Timeline to make the ship invincible for 2 seconds when placed back in the game safely
    private Timeline thrustTimeline;// Timeline to make the ship fill color white when accelerating
    private Asteroid<Double> asteroid;
    private Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);// Create a ship object at the center of the canvas
    private Alien alien = new Alien(WIDTH, Random.nextInt(HEIGHT));
    private Ship ship1 = new Ship(75, 75); // Create a ship object at the up left corner of the canvas to represent the lives of the player
    private Ship ship2 = new Ship(95, 75); // Create a ship object at the up left corner of the canvas to represent the lives of the player
    private Ship ship3 = new Ship(115, 75); // Create a ship object at the up left corner of the canvas to represent the lives of the player
                                      
    private long lastShotTime = 0L; // Last time the ship shot a projectile
    private int MaxLives = 3; // Maximum number of lives
    private int remainingLives = MaxLives; // Remaining lives
    private Text livesText = new Text(10, 80,"Lives: ");// Create a text object to lives
    private Text levelCleared = new Text(10, 60, "Level: 1"); // Create a text object to level number
    private Text levelClearedText = new Text(WIDTH / 2 - 50, HEIGHT / 4, "Level Cleared!"); // Create a text object to show level cleared
    private Text Pointstext = new Text(10, 40, "Points: 0");// Create a text object to display points
    private int currentLevel; // Current level

    private List<Projectile> projectiles = new ArrayList<>(); // Create a list to store the projectiles
    private List<Projectile> projectiles_alien = new ArrayList<>(); // Create a list to store the projectiles of the alien
    private List<Projectile> tailProjectiles = new ArrayList<>(); // Create a list to store the tail projectiles
    private double size; // Size of the Asteroid
    private double speed_L = 0.5; // Speed of the Asteroid
    private double scale = 0.5; // Scale of the Asteroid

    public void createNewGame(Stage primaryStage) { // Start the gameplay scene
        this.primaryStage = primaryStage;
        this.primaryStage.hide(); // Hide the main menu stage
        stage = new Stage(); // Create a new stage for the gameplay
        stage.setWidth(primaryStage.getWidth()); // Set the width of the gameplay stage to the width of the main menu stage
        stage.setHeight(primaryStage.getHeight()); //  Set the height of the gameplay stage to the height of the main menu stage
        try {
            start(stage); // Start the gameplay scene
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace if an exception occurs
        }
    };

    public void MoveAlien() { // Move the alien ship
        long currentTime = System.currentTimeMillis(); // Get the current time
        if ((currentTime - lastShotTime > 1000) & alien.isAlive() & alien.isVisible() & ship.isAlive() & canCollide) { // If the time elapsed since the last shot is greater than 1 second and the alien ship is alive and visible and the ship is alive and can collide
            Projectile projectile_alien = new Projectile((int) alien.getCharacter().getTranslateX() + 12, // Create a projectile object at the center of the alien ship                                                                                                          // ship
                    (int) alien.getCharacter().getTranslateY() + 10); 
            projectile_alien.getCharacter().setRotate(alien.getCharacter().getRotate()); // Set the rotation of the projectile to the rotation of the alien ship
            projectiles_alien.add(projectile_alien);

            double angle_alien = (Math.random() - 0.5) * 2 * Math.PI; // Set the angle of the projectile to a random angle between 0 and 2 * PI
            double speed_alien = alien.getMovement().magnitude() + 10; // set projectile speed to alien ship speed + 10
            double dx_alien = Math.cos(angle_alien) * speed_alien; // Set the x component of the projectile speed to the cosine of the angle times the speed
            double dy_alien = Math.sin(angle_alien) * speed_alien; // Set the y component of the projectile speed to the sine of the angle times the speed

            projectile_alien.setMovement(new Point2D(dx_alien, dy_alien)); // Set the movement of the projectile to the x and y components of the projectile speed
            pane.getChildren().add(projectile_alien.getCharacter()); // Add the projectile to the pane

            lastShotTime = currentTime; // Upate the lastshottime to the current time
        }
        alien.move(); // Move the alien ship
    }

    public void randomAsteroids() { // Create asteroids of random size
        Random rnd = new Random();
        int randomNumber = rnd.nextInt(3) + 1; // pick a number between 1 and 3
        // Set the size of the asteroid based on the random number
        switch (randomNumber) {
            case 1:
                size = 10 + rnd.nextDouble(5) ; // Small asteroid size 10 to 15
                break;
            case 2:
                size = 30 + rnd.nextDouble(15); // Middle asteroid size 30 to 45
                break;
            case 3:
                size = 60; // Large asteroid size 60
                break;
            default:
                System.out.println("Invalid size. Setting size to medium.");
                size = 15; // Default is Large asteroid size 60
                break;
        }
    } 

    @Override
    public void start(Stage stage) throws Exception {
        this.pane = new Pane();
        this.canvas = new Canvas(WIDTH, HEIGHT); // Create a canvas with specified width and height
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        pane.getChildren().addAll(canvas);

        /* Once each level is cleared, the next begins automatically */
        Pointstext.setFill(Color.WHITE);
        Pointstext.setFont(Font.font("Silkscreen", FontWeight.BOLD, 16));
        pane.getChildren().add(Pointstext);
        levelCleared.setFill(Color.WHITE);
        levelCleared.setFont(Font.font("Silkscreen", FontWeight.BOLD, 16));
        pane.getChildren().add(levelCleared);
        /* Display level is cleared information*/
        levelClearedText = new Text(WIDTH / 2 - 50, HEIGHT / 4, "Level Cleared!");
        levelClearedText.setFill(Color.WHITE);
        levelClearedText.setFont(Font.font("Silkscreen", FontWeight.BOLD, 16));
        levelClearedText.setVisible(false);
        pane.getChildren().add(levelClearedText);

        /* Display the number of lives */
        livesText.setFill(Color.WHITE);
        livesText.setFont(Font.font("Silkscreen", FontWeight.BOLD, 16));
        pane.getChildren().add(livesText);

        // Create AtomicInteger objects to store the level and points
        AtomicInteger level = new AtomicInteger(1);
        AtomicInteger points = new AtomicInteger();

        // Create an ArrayList to store Asteroid objects
        List<Asteroid<Double>> asteroids = new ArrayList<>();
        // Generate one random Asteroid object and add it to the asteroids list
        Random rnd = new Random();
        for (int i = 0; i < 1; i++) {
            randomAsteroids();
            Asteroid<Double> asteroid = new Asteroid<Double>(rnd.nextInt(WIDTH / 2), rnd.nextInt(HEIGHT), size, speed_L);
            asteroids.add(asteroid);
        }

        /* Background StarParticle Animation */
        List<StarParticle> particles = new ArrayList<>(); // Create a list to store star particles
        for (int i = 0; i < NUM_PARTICLES; i++) { // Create NUM_PARTICLES number of star particles and add them to the
                                                  // list
            particles.add(new StarParticle(Random.nextInt(WIDTH), 0, Duration.millis(i * Random.nextInt(1000))));
        }

        pane.getChildren().add(ship.getCharacter()); // Add the ship character to the pane
        pane.getChildren().add(alien.getCharacter()); // Add the alien ship characters to the pane
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));// Add asteroid characters to the

        scene = new Scene(pane); // Create a scene with the pane
        stage.setTitle("Asteroids!"); // Set the title of the stage
        scene.setFill(Color.BLACK); // Set the background color of the scene to black
        stage.setScene(scene); // Set the scene to the stage
        stage.show(); // Show the stage

        scene.setOnKeyPressed(event -> { // Event handler for key pressed
            pressedKeys.put(event.getCode(), Boolean.TRUE); // Update the map with the key code and value indicating it
                                                            // is pressed
        });

        scene.setOnKeyReleased(event -> { // Event handler for key released
            pressedKeys.put(event.getCode(), Boolean.FALSE); // Update the map with the key
            if (event.getCode() == KeyCode.SPACE) {
                canShoot = true;// We shoot only once when keep holding the spacebar
            }
        });

        //draw the number of ships left to demonstrate the remaining lives
        pane.getChildren().add(ship1.character);
        pane.getChildren().add(ship2.character);
        pane.getChildren().add(ship3.character);

        // create and format exit button
        Button quitButton = new Button("Quit");
		Buttons.setButton(quitButton);
        quitButton.setFocusTraversable(false);
        quitButton.setTranslateX(WIDTH - 100);
        quitButton.setTranslateY(15);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameOver gameover = new GameOver();
                gameover.createGameOver(stage, points);
            }
        });
        pane.getChildren().add(quitButton);

        new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) { // If the left key is pressed, te ship turns left
                    ship.turnLeft();
                }

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) { // If the right key is pressed, the ship turns
                                                                      // right
                    ship.turnRight();
                }
                if (pressedKeys.getOrDefault(KeyCode.UP, false)) { // If the up key is pressed, the ship accelerates
                    ship.accelerate();
                    // ship.character.setFill(Color.RED); // Change the color of the ship to white when accelerating
                    ship.changeShape();
                    thrustTimeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
                        // ship.character.setFill(Color.TRANSPARENT);
                        ship.restoreShape();
                    }));
                    thrustTimeline.play();
                }

                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && canShoot && canCollide) { // We shoot only once when keep
                                                                                  // holding the spacebar
                    // we shoot
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(),
                            (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);

                    /* make the projectile in the direction the ship is currently pointing */
                    double angle = Math.toRadians(ship.getCharacter().getRotate()); // convert ship rotation to radians
                    double speed = ship.getMovement().magnitude() + 5; // set projectile speed to ship speed + 5
                    double dx = Math.cos(angle) * speed; // calculate x component of movement vector
                    double dy = Math.sin(angle) * speed; // calculate y component of movement vector
                    projectile.setMovement(new Point2D(dx, dy));

                    pane.getChildren().add(projectile.getCharacter());
                    canShoot = false; // We shoot only once when keep holding the spacebar
                }
                /* If press shift, activate the hyperspace jump */
                if (pressedKeys.getOrDefault(KeyCode.SHIFT, false)) {
                    asteroids.forEach(asteroid -> {
                        ship.character.setTranslateX(Math.random() * WIDTH);
                        ship.character.setTranslateY(Math.random() * HEIGHT);
                        while (asteroid.collide(ship) || alien.collide(ship)) { // If the ship collides with an asteroid or alien, move the ship to a random location
                            ship.character.setTranslateX(Math.random() * WIDTH);
                            ship.character.setTranslateY(Math.random() * HEIGHT);
                        }
                    });
                    pressedKeys.clear();

                }
                if(ship.alive && (!alien.alive) && (Math.random() < 0.005)) { // If the ship is alive and the alien is not visible, set alien visible with a 0.5% chance
                    alien = new Alien(WIDTH,Random.nextInt(HEIGHT));
                    pane.getChildren().add(alien.character);
                } 

                ship.move(); // Move the ship
                MoveAlien(); // Move the alien

                /*If the alien collides with an asteroid, set alien alive 
                and change the location of the asteroid to make sure player get level up based on the destroyed asteroids*/
                asteroids.forEach(asteroid -> {
                    asteroid.move();
                    if (alien.collide(asteroid)) { 
                        alien.setAlive(false);
                        pane.getChildren().remove(alien.character);
                        asteroid.character.setTranslateX(App.WIDTH);
                        asteroid.character.setTranslateY(new Random().nextInt(App.HEIGHT));
                        
                    }
                });
                /*If the alien's bullet collides with an asteroid, set bullet
                and change the location of the asteroid to make sure player get level up based on the destroyed asteroids*/
                projectiles_alien.forEach(projectile_alien -> {
                    projectile_alien.move_alien();
                    asteroids.forEach(asteroid -> {
                        if (projectile_alien.collide(asteroid)) {
                            projectile_alien.setAlive(false);
                            asteroid.character.setTranslateX(App.WIDTH);
                            asteroid.character.setTranslateY(new Random().nextInt(App.HEIGHT));
                        }
                    });
                });
                /*The player can regain lives by scoring 10000 points if the remaining lives not exceed the maxlives*/
                if (points.get() - lasthighscores >10000 && remainingLives > 0 && remainingLives < MaxLives+1){
                    remainingLives += 1;
                    lasthighscores += 10000;
                }              
                /* Collision */
                if (canCollide) {
                    asteroids.forEach(asteroid -> {
                        if (ship.collide(asteroid)) {
                            asteroid.character.setTranslateX(App.WIDTH);
                            asteroid.character.setTranslateY(new Random().nextInt(App.HEIGHT));
                            remainingLives--; // Decrease the remaining lives by 1              
                            canCollide = false; // Set canCollide to false means the player is invincible for a short time
                            checkLives(remainingLives); // Check the remaining lives                   
                            pane.getChildren().remove(ship.character); // Remove the ship from the pane                      
                            projectiles.forEach(projectile -> pane.getChildren().remove(projectile.getCharacter())); // Remove the projectiles from the pane
                            projectiles_alien.forEach(projectile_alien -> pane.getChildren().remove(projectile_alien.getCharacter())); // Remove the alien's projectiles from the pane
                            ship = new Ship(WIDTH / 2, HEIGHT / 2); // Create a new ship at the center of the screen
                            pane.getChildren().add(ship.character);
                            invincible(); // Set the ship flash when it is invincible
                        }
                    });

                    projectiles_alien.forEach(projectile_alien -> {
                        if (ship.collide(projectile_alien)) {
                            projectile_alien.setAlive(false);                              
                            remainingLives--;
                            canCollide = false;                            
                            checkLives(remainingLives);
                            pane.getChildren().remove(ship.character);
                            projectiles.forEach(projectile -> pane.getChildren().remove(projectile.getCharacter())); // Remove the projectiles from the pane
                            ship = new Ship(WIDTH / 2, HEIGHT / 2);
                            pane.getChildren().add(ship.character);
                            invincible(); // Set the ship flash when it is invincible         
                        }      
                    });
                    if (ship.collide(alien)) {                     
                        alien.setAlive(false);
                        pane.getChildren().remove(alien.character);
                        remainingLives--;
                        canCollide = false;
                        checkLives(remainingLives);     
                        pane.getChildren().remove(ship.character);                      
                        projectiles.forEach(projectile -> pane.getChildren().remove(projectile.getCharacter())); // Remove the projectiles from the pane
                        projectiles_alien.forEach(projectile_alien -> pane.getChildren().remove(projectile_alien.getCharacter())); // Remove the alien's projectiles from the pane
                        ship = new Ship(WIDTH / 2, HEIGHT / 2); // Create a new ship at the center of the screen
                        pane.getChildren().add(ship.character);
                        invincible(); // Set the ship flash when it is invincible
                    }

                    projectiles.forEach(projectile -> {
                        projectile.move();
                        if (projectile.collide(alien)) {
                            projectile.setAlive(false);
                            alien.setAlive(false);
                            pane.getChildren().remove(alien.character);
                            Pointstext.setText("Points: " + points.addAndGet(200)); // Get 200 points when the alien is destroyed
                            bulletEffect(projectile); // Create a bullet effect when the alien is destroyed
                        }
                        asteroids.forEach(asteroid -> {
                            if (projectile.collide(asteroid)) {
                                if (asteroid.getSize() < 15) {
                                    Pointstext.setText("Points: " + points.addAndGet(100)); // Get 100 points when the small asteroid is destroyed
                                } else if (asteroid.getSize() < 45 && asteroid.getSize() >= 15) {
                                    Pointstext.setText("Points: " + points.addAndGet(50)); // Get 50 points when the medium asteroid is destroyed
                                } else {
                                    Pointstext.setText("Points: " + points.addAndGet(20));// Get 20 points when the large asteroid is destroyed
                                }
                                projectile.setAlive(false);
                                asteroid.setAlive(false);   
                                bulletEffect(projectile); // Create a bullet effect when the asteroid is destroyed
                            }  
                        });
                    });
                }

                /* Check if the level is cleared */
                if (asteroids.isEmpty()) {
                    levelCleared.setText("Level: " + level.addAndGet(1)); // Update level text with incremented level
                    currentLevel ++; // Increment current level counter
                    speed_L += 0.15; // increase the speed of the asteorids every round

                    for (int i = 0; i < currentLevel + 1; i++) { // Create asteroids based on current level
                        Random rnd = new Random();
                        randomAsteroids();
                        Asteroid<Double> asteroid = new Asteroid<Double>(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT), size, speed_L);
                        asteroids.add(asteroid); // Add created asteroid to the asteroids list
                        pane.getChildren().add(asteroid.getCharacter()); // Add asteroid character to the pane
                    }
                    levelClearedText.setVisible(true);
                    // pane.getChildren().remove(levelClearedText); // Remove level cleared message
                    Timeline levelClearedTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                        // pane.getChildren().remove(levelClearedText);
                        levelClearedText.setVisible(false);
                    }));
                    levelClearedTimeline.play();
                }

                /* Filter the projectiles list to find projectiles that destroy asteroids */
                List<Projectile> destroy_asteroid = projectiles.stream().filter(shot -> {
                    List<Asteroid<Double>> destroy = asteroids.stream()
                          .filter(asteroids -> asteroids.collide(shot)) // Check collision between projectile and asteroid
                            .collect(Collectors.toList());
                    if (destroy.isEmpty()) {
                        return false;
                    }
                    // Remove destroyed asteroid and create new smaller asteroids
                    destroy.stream().forEach(delete -> {
                        asteroids.remove(delete); // Remove destroyed asteroid from the asteroids list
                        pane.getChildren().remove(delete.getCharacter()); // Remove asteroid character from the pane
                        for (int i = 0; i < 2 ; i++) { // Create two smaller asteroids from the destroyed asteroid
                            if (delete.getSize() > 15) {
                                Random rnd = new Random();
                                double angle = (rnd.nextDouble()-0.5)*2*Math.PI; // add a random direction
                                double speed = rnd.nextDouble(); // add a random speed between 0 and 1
                                delete.setMovement(new Point2D(Math.cos(angle) * speed, Math.sin(angle) * speed)); //new created asteroid given a new direction based on the original and a random speed
                                asteroid = new Asteroid<Double>((int) (delete.getCharacter().getTranslateX() + delete.movement.getX()),
                                        (int) (delete.getCharacter().getTranslateY()+ delete.movement.getY()), delete.getSize() * scale, delete.getMovement().magnitude() + speed);//new created asteroid given a new speed based on the original and a random speed
                                asteroids.add(asteroid); // Add created asteroid to the asteroids list
                                pane.getChildren().add(asteroid.getCharacter()); // Add asteroid character to the pane
                            }
                        }
                    });
                    return true;
                }).collect(Collectors.toList());
                /* Remove projectiles and alien's projectiles that destroy asteroids and ship*/
                projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
                projectiles.removeAll(projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .collect(Collectors.toList()));

                projectiles_alien.stream()
                        .filter(projectile_alien -> !projectile_alien.isAlive())
                        .forEach(projectile_alien -> pane.getChildren().remove(projectile_alien.getCharacter()));
                projectiles_alien.removeAll(projectiles_alien.stream()
                        .filter(projectile_alien -> !projectile_alien.isAlive())
                        .collect(Collectors.toList()));

                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroids.removeAll(asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));
                        
                tailProjectiles.stream()
                        .filter(tailProjectile -> !tailProjectile.isAlive())
                        .forEach(tailProjectile -> pane.getChildren().remove(tailProjectile.getCharacter()));
                tailProjectiles.removeAll(tailProjectiles.stream()
                        .filter(tailProjectile -> !tailProjectile.isAlive())
                        .collect(Collectors.toList()));

                /* Background Canvas StarAnimation */
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, WIDTH, HEIGHT);
                gc.setFill(Color.WHITE);

                particles.forEach(p -> {
                    p.onUpdate();
                    gc.fillRect(p.x, p.y, 1, 1);
                });
            }
            /* Check the remaining lives of the player */
            public void checkLives(int remainingLives){
                if (remainingLives == 2){
                    pane.getChildren().remove(ship3.character);
                }
                if (remainingLives == 1){
                    pane.getChildren().remove(ship2.character);
                }
                if (remainingLives == 0) {
                    try {
                        GameOver gameOver = new GameOver();
                        gameOver.createGameOver(stage,points);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }           
                }  
            }
            /* Ship in invisible state and flash effect */
            public void invincible(){
                /* Set the ship flash when it is invincible */
                invincibleTimeline = new Timeline(
                                new KeyFrame(Duration.seconds(0.1), event -> ship.character.setStroke(Color.TRANSPARENT)),
                                new KeyFrame(Duration.seconds(0.2), event -> ship.character.setStroke(Color.WHITE))
                            );
                invincibleTimeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
                invincibleTimeline.play();
                /* Set canCollide back to true after a delay of 2 seconds */
                flashtimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                    canCollide = true;
                    ship.character.setStroke(Color.WHITE);
                    invincibleTimeline.stop();
                }));
                flashtimeline.play();  
            }
            /* bullet impact Effects */
            public void bulletEffect(Projectile projectile){
                for (int i = 0 ; i < 5 ; i++){
                    Projectile tailProjectile = new Projectile((int) projectile.getCharacter().getTranslateX(),
                    (int) projectile.getCharacter().getTranslateY());
                    tailProjectile.getCharacter().setRotate(projectile.getCharacter().getRotate());
                    tailProjectiles.add(tailProjectile);
                    tailProjectile.tailEffect();
                    pane.getChildren().add(tailProjectile.getCharacter());
                    Timeline tailProjectileTimeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
                        tailProjectile.move();
                    }));                                
                    tailProjectileTimeline.setCycleCount(Timeline.INDEFINITE);
                    tailProjectileTimeline.play();
                    Timeline tailtimeline = new Timeline(new KeyFrame(Duration.seconds(0.25), event -> {
                        pane.getChildren().remove(tailProjectile.getCharacter());
                    }));
                    tailtimeline.play();
                }
            }
        }.start();
    }

    // public static void main(String[] args) {
    // launch(args);
    // }
}