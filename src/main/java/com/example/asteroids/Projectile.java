package com.example.asteroids;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Projectile extends Character {

    private double distanceTraveled; // distance the projectile travel before disappearing
    private double maxDistance = App.HEIGHT*2/3;
    private double angle;
    private double speed;

    public Projectile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y); 
        this.character.setFill(Color.WHITE);
    }

    @Override
    public void move() { //projectile disappear automatically after travelling a set distance
     
        if (isAlive()) {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
        distanceTraveled += movement.magnitude(); // increment distance traveled
        if (distanceTraveled >= maxDistance) { // set max distance traveled before disappearing
            setAlive(false);
        }    
        if (this.character.getTranslateX() < 0 || this.character.getTranslateY() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + App.WIDTH);
            this.character.setTranslateY(this.character.getTranslateY() + App.HEIGHT);
        }
    
        if (this.character.getTranslateX() > App.WIDTH || this.character.getTranslateY() > App.HEIGHT) {
            this.character.setTranslateX(this.character.getTranslateX() % App.WIDTH);
            this.character.setTranslateY(this.character.getTranslateY() % App.HEIGHT);
        }
        }
    }

    public void move_alien() { //projectile disappear automatically after travelling a set distance
        maxDistance = App.HEIGHT/2;
     
        if (isAlive()) {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
        distanceTraveled += movement.magnitude(); // increment distance traveled
        if (distanceTraveled >= maxDistance) { // set max distance traveled before disappearing
            setAlive(false);
        } 
        }
    }

    public void tailEffect(){
        // super.move();
        angle = (rnd.nextDouble()-0.5)*2*Math.PI; // add a random direction
        speed = rnd.nextDouble()+1; // add a random speed between 0 and 1
        this.setMovement(new Point2D(Math.cos(angle) * speed, Math.sin(angle) * speed)); //new created asteroid given a new direction based on the original and a random speed
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
    }

}