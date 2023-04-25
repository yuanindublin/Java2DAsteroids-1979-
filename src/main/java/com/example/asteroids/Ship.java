package com.example.asteroids;
import javafx.scene.shape.Polygon;
import java.util.Random;

public class Ship extends Character {
    
    Random rnd = new Random();

    public Ship(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5, 0.2, -0.2), x, y);// create a ship with a polygon shape
        super.getCharacter().setRotate(-90);
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 3);// rotate the ship to the left
    }
    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 3);// rotate the ship to the right
    }
}


