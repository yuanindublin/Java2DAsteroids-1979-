package com.example.asteroids;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import java.util.Random;

public abstract class Character {

    public Polygon character;
    public Point2D movement;    
    public boolean alive = true;
    private static final double MAX_SPEED = 8;
    Random rnd = new Random();

    public Character(Polygon polygon, int x, int y) {
        this.character = polygon; // Set the polygon of the character
        polygon.setFill(Color.TRANSPARENT); // Set the fill color of the polygon to transparent
        polygon.setStroke(Color.WHITE); // Set the stroke (outline) color of the polygon to white
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.movement = new Point2D(0, 0);
    }

    public Polygon getCharacter() {
        return character;
    }

    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());// Update the x-coordinate of the character based on movement vector
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());// Update the y-coordinate of the character based on movement vector

        // Wrap the character around the screen if it goes out of bounds
        if (this.character.getTranslateX() < 0 || this.character.getTranslateY() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + App.WIDTH);
            this.character.setTranslateY(this.character.getTranslateY() + App.HEIGHT);
        }
        if (this.character.getTranslateX() > App.WIDTH || this.character.getTranslateY() > App.HEIGHT) {
            this.character.setTranslateX(this.character.getTranslateX() % App.WIDTH);
            this.character.setTranslateY(this.character.getTranslateY() % App.HEIGHT);
        }
    }

    public void accelerate() {
        if (this.getMovement().magnitude()<MAX_SPEED){
        // Update the movement vector based on the current rotation angle of the character
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));
        changeX *= 0.08; // Scale the change in x-coordinate to control the acceleration
        changeY *= 0.08; // Scale the change in y-coordinate to control the acceleration
        this.movement = this.movement.add(changeX, changeY);
        }
    }

    public Point2D getMovement(){
        return this.movement;
    }

    public void setMovement(Point2D movement){
        this.movement = new Point2D(movement.getX(), movement.getY());// Set the movement vector to a new Point2D object
    }

    public boolean isAlive() {
        return alive; // Check if the character is alive or not
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive; // Set the alive flag for the character
    }

    public boolean collide(Character other) { 
        // Check if the character intersects with another character
        Shape collisionArea = Shape.intersect(this.getCharacter(), other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
}