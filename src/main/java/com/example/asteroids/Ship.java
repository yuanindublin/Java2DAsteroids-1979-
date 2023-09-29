package com.example.asteroids;
import javafx.scene.shape.Polygon;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Ship extends Character {
    
    Random rnd = new Random();
    private boolean shapeChanged = false; // Flag to track whether the shape has changed


    public Ship(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5, 1, 3, 1, -3), x, y);// create a ship with a polygon shape
        super.getCharacter().setRotate(-90);
        super.getCharacter().setStrokeWidth(1.8); // Set the stroke width to make the lines bold
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 3);// rotate the ship to the left
    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 3);// rotate the ship to the right
    }

    public void changeShape() {
        if (!shapeChanged) {
            
            // Change the ship's shape by updating its polygon points
            ObservableList<Double> points = FXCollections.observableArrayList(-5.0, -5.0, 10.0, 0.0, -5.0, 5.0, 1.0, 3.0, -10.0 ,0.0, 1.0, -3.0);
            ((Polygon) this.character).getPoints().setAll(points);

            this.character.setFill(Color.RED); // Change the color of the ship
            shapeChanged = true;
        }
    }

    public void restoreShape() {
        if (shapeChanged) {
            // Restore the ship's original shape
            ObservableList<Double> points = FXCollections.observableArrayList(-5.0, -5.0, 10.0, 0.0, -5.0, 5.0, 1.0, 3.0, 1.0, -3.0);
            ((Polygon) this.character).getPoints().setAll(points);
            this.character.setFill(Color.TRANSPARENT); // Restore the color of the ship
            shapeChanged = false;
        }
    }
}


