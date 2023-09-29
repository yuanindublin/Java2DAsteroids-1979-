package com.example.asteroids;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import java.util.Random;

public class Alien extends Character{
    // coordinates of the points of polygon
    static double points[] = { 2.0d, 28.0d, 6.0d, 22.0d, 8.0d,
        10.0d, 10.0d, 8.0d, 22.0d, 6.0d, 28.0d, 2.0d };
        
    private double distanceTraveled; // distance the alien ship travel before change to another random path
    private double maxDistance = App.HEIGHT/4;

    Random rnd = new Random();

    double angle = (rnd.nextDouble()-0.5)*2*Math.PI; // the alien ship follows a random direction from -pi to pi
    double speed = rnd.nextDouble() + 1; // set alien ship speed to a random value between 1 and 2
                    
    public Alien(int x, int y) {
        super(new Polygon(points), x, y);
        super.getCharacter().setRotate(45); // set the rotation of the alien ship to 45 degrees
        super.getCharacter().setStrokeWidth(1.8); 
    }

    @Override
    public void move() { //alien ship change to a random direction after travelling a set distance
        super.setMovement(new Point2D(Math.cos(angle) * speed, Math.sin(angle) * speed));
                
        if (isAlive()) {
            this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
            this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
            distanceTraveled += movement.magnitude(); // increment distance traveled
            if (distanceTraveled >= maxDistance) { // set max distance traveled before disappearing
                angle = (rnd.nextDouble()-0.5)*2*Math.PI; // the alien ship follow another random direction
                speed = rnd.nextDouble() * 2 + 0.5;                
                super.setMovement(new Point2D(Math.cos(angle) * speed, Math.sin(angle) * speed));
                distanceTraveled-=maxDistance;
            }    
        }
    }

    public boolean isVisible() { // Check if the alien ship is Visible on the screen or not
        if (this.character.getTranslateX() >20 & this.character.getTranslateX() <  (App.WIDTH-20) & this.character.getTranslateY() >10 & this.character.getTranslateY() <  (App.HEIGHT-20)) {
            return true;
        }
        else{
            return false;
        }
    }
}

