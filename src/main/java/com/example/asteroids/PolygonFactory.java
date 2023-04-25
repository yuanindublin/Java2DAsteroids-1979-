package com.example.asteroids;
import javafx.scene.shape.Polygon;
import java.util.Random;
// import javafx.scene.GraphicsContext;


public class PolygonFactory {

    public Polygon createPolygon(double size) {
        Random rnd = new Random();
        
        Polygon polygon = new Polygon();// Create a new Polygon object

        // Calculate the coordinates for the vertices of the polygon using trigonometric functions
        double c1 = Math.cos(Math.PI * 2 / 5);
        double c2 = Math.cos(Math.PI / 5);
        double s1 = Math.sin(Math.PI * 2 / 5);
        double s2 = Math.sin(Math.PI * 4 / 5);

        // Set the points of the polygon using the calculated coordinates
        polygon.getPoints().addAll(
            size, 0.0,
            size * c1, -1 * size * s1,
            -1 * size * c2, -1 * size * s2,
            -1 * size * c2, size * s2,
            size * c1, size * s1);

        // Add random changes to the coordinates of the vertices of the polygon
        for (int i = 0; i < polygon.getPoints().size(); i++) {
            int change = rnd.nextInt(5) - 2; // Random change between -2 and 2
            polygon.getPoints().set(i, polygon.getPoints().get(i) + change); // Apply the random change to the coordinate
        }

        return polygon;// Return the created polygon
    }

}