package com.example.asteroids;
import javafx.scene.shape.Polygon;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;


public class PolygonFactory {

    public Polygon createPolygon(double size) {
        Random rnd = new Random();
        List<Double> points = new ArrayList<>(); // Create a list to store vertex coordinates

        int numVertices = 12; // Number of vertices for the jagged shape

        // Calculate random angles for the vertices
        for (int i = 0; i < numVertices; i++) {
            double angle = Math.PI * 2 * i / numVertices + (rnd.nextDouble() - 0.5) * Math.PI / 5; // Add random variation to the angles
            double x = size * Math.cos(angle);
            double y = size * Math.sin(angle);

            points.add(x);
            points.add(y);
        }

        Polygon polygon = new Polygon(); // Create a new Polygon object
        polygon.getPoints().addAll(points); // Add the coordinates to the polygon's points

        // Apply a random rotation to the entire polygon
        double randomRotation = rnd.nextDouble() * 360; // Random rotation angle in degrees
        polygon.setRotate(randomRotation);

        return polygon;// Return the created polygon
    }

}