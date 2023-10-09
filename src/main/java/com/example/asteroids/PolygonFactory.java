package com.example.asteroids;
import javafx.scene.shape.Polygon;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

// Define an enum for asteroid shapes
enum AsteroidShape {
    SHAPE1, // Add more shapes as needed
    SHAPE2,
    SHAPE3,
    SHAPE4
}

public class PolygonFactory {

    public Polygon createPolygon(double size) {
        Random rnd = new Random();
        
        List<Double> points = new ArrayList<>(); // Create a list to store vertex coordinates

        List<Double> points_1 = Arrays.asList(-5.0, -10.0, 0.0, -10.0, 6.0, -6.0, 5.0, -2.5, 6.0, 1.0, 3.0, 5.0, -1.0, 1.0, -5.0, 5.0, -10.0, 0.0, -10.0, -5.0);
        List<Double> points_2 = Arrays.asList(-7.0, -10.0, -5.0, -8.0, 0.0, -10.0, 5.0, -5.0, 2.0, -1.0, 6.0, 1.0, 2.0, 5.0, -2.0, 3.0, -6.0, 5.0, -10.0, 0.0, -9.0, -3.5, -10.0, -7.0);
        List<Double> points_3 = Arrays.asList(-7.0, -10.0, -3.0, -5.0, -3.0, -10.0, 1.0, -10.0, 4.0, -4.0, 4.0, -1.0, 1.0, 5.0, -4.0, 5.0, -10.0, 0.0, -6.0, -2.0, -10.0, -4.0);
        List<Double> points_4 = Arrays.asList(-7.0, -10.0, 0.0, -8.0, 2.0, -10.0, 5.0, -6.0, 0.0, -4.0, 5.0, -2.0, 5.0, 0.0, 0.0, 5.0, -5.0, 5.0, -4.0, 1.5, -10.0, 1.5, -10.0, -4.0);

         // Generate a random shape from the enum
        //  AsteroidShape shape = getRandomAsteroidShape();
         shape = getRandomAsteroidShape();

         switch (shape) {
             case SHAPE1:
                 points.addAll(points_1);
                 break;
             case SHAPE2:
                 points.addAll(points_2);
                 break;
             case SHAPE3:
                 points.addAll(points_3);
                 break;
             case SHAPE4:
                 points.addAll(points_4);
                 break;
         }
        

        Polygon polygon = new Polygon(); // Create a new Polygon object
        polygon.getPoints().addAll(points); // Add the coordinates to the polygon's points
        System.out.println(points);
        // Apply a random rotation to the entire polygon
        double randomRotation = rnd.nextDouble() * 360; // Random rotation angle in degrees
        polygon.setRotate(randomRotation);

        return polygon;// Return the created polygon
    }

    private AsteroidShape getRandomAsteroidShape() {
    //     // Generate a random shape from the enum
    //     AsteroidShape[] shapes = AsteroidShape.values();
    //     int randomIndex = new Random().nextInt(shapes.length);
    //     return shapes[randomIndex];
    // }
    private getRandomAsteroidShape() {
        // Generate a random shape from the enum
        shapes = AsteroidShape.values();
        int randomIndex = new Random().nextInt(shapes.length);
        return shapes[randomIndex];
    }

}