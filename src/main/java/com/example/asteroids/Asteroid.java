package com.example.asteroids;
import javafx.scene.shape.Shape;
import java.util.Random;

public class Asteroid<move_speed> extends Character {
    public   double move_speed;
    public double rotationalMovement;
    public int x;
    public int y;
    public double size;
    public double speed;

    // Constructor for the Asteroid class
    public Asteroid(int x, int y, double size, double speed) {

        // Call the constructor of the AbstractGameElement class with a Polygon shape as the character,
        // and the given x, y coordinates as the initial position of the Asteroid
        super(new PolygonFactory().createPolygon(size), x, y);

        // Create a new Random object to generate random values
        Random rnd = new Random();

        // Set the size and move speed of the Asteroid based on the input parameters
        this.size = size;
        this.move_speed = speed;

        // Set the initial rotation of the Asteroid to a random angle between 0 and 359 degrees
        super.getCharacter().setRotate(rnd.nextInt(360));

        // Generate a random value between -0.5 and 0.5 for rotational movement
        this.rotationalMovement = 0.5 - rnd.nextDouble();

        int accelerationAmount = 1 + rnd.nextInt(10);// Generate a random acceleration amount between 1 and 10, and apply acceleration to the asteroid that many times
        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }
    }

    // Method to move the Asteroid
    public void move() {
        // Calculate the change in x and y coordinates based on the current rotation angle
        double changeX = Math.cos(Math.toRadians(this.getCharacter().getRotate()));
        double changeY = Math.sin(Math.toRadians(this.getCharacter().getRotate()));

        // Update the x and y coordinates of the Asteroid based on the change in x and y
        // and the current move speed
        this.getCharacter().setTranslateX(getCharacter().getTranslateX() + changeX * this.move_speed);
        this.getCharacter().setTranslateY(this.getCharacter().getTranslateY() + changeY * this.move_speed);
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
    }

    // Method to get the size of the Asteroid
    public double getSize() {
        return this.size;
    }
    
    // Method to increase the move speed of the Asteroid
    public double Move_speed_up() {
        this.move_speed += 0.3;
        return this.move_speed;
    }

    // Method to check collision with another AbstractGameElement
    public boolean collide(Character other) {
        // Intersect the shapes of the two game elements to get the collision area
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());

        // Check if the width of the collision area is not -1, which indicates collision
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
}