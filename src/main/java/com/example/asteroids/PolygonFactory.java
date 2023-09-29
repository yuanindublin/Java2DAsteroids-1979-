package com.example.asteroids;
import javafx.scene.shape.Polygon;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;


public class PolygonFactory {

    public Polygon createPolygon(double size) {
        Random rnd = new Random();
        
        List<Double> points = new ArrayList<>(); // Create a list to store vertex coordinates

        int numVertices = 6; // Number of vertices for the jagged shape
        double minX = 0.0;
        double minY = 0.0;

        // Calculate random angles for the vertices
        for (int i = 0; i < numVertices; i++) {
            double x, y;

            double angle = (Math.PI * 2 * i / numVertices) + ((rnd.nextDouble() - 0.5) * Math.PI / 5); // Add random variation to the angles
            x = size * Math.cos(angle);
            y = size * Math.sin(angle);


            if (i%2 != 0 & x < 0 & y > 0) {
                // Insert the desired points
                points.add( x / 2);
                points.add(2* y / 3);
                points.add( 1.3 * x);
                // points.add(0.0);
                points.add(2* y / 3);
                points.add(  1.3 * x);
                points.add( - 1.5 * y);
                // points.add( - 2* y / 3);
                points.add(  x / 2);
                points.add( - 2* y / 3);
            }

            if (i%2 != 0 & x < 0 & y < 0) {
                // Insert the desired points
                points.add( x / 2);
                points.add( 1* y /2);
                // points.add(- 1* y /2);
                points.add( 1.5 * x);
                points.add( 1* y /2);
                // points.add(- 1* y /2);
                // points.add(y);
                // points.add(2* y / 3);
                points.add(  1.5 * x);
                points.add(1.5 * y);
                // points.add( 2 * y);
                // points.add( - 1.5 * y);
                // points.add( - 2* y / 3);
                points.add(  x );
                // points.add(  x / 2);
                points.add(1.5 *  y);
                // points.add(- 1* y /2);
                // points.add(y);
                // points.add(  2* y / 3);
            }
            // if (i%2 != 0 & x > 0 & y > 0) {
            //     // Insert the desired points
            //     points.add( x / 2);
            //     points.add( 1* y /2);
            //     // points.add(- 1* y /2);
            //     points.add( 1.3 * x);
            //     points.add( 1* y /2);
            //     // points.add(- 1* y /2);
            //     // points.add(y);
            //     // points.add(2* y / 3);
            //     points.add(  1.3 * x);
            //     points.add(y);
            //     // points.add( 2 * y);
            //     // points.add( - 1.5 * y);
            //     // points.add( - 2* y / 3);
            //     points.add(  x / 2);
            //     points.add(y);
            //     // points.add(- 1* y /2);
            //     // points.add(y);
            //     // points.add(  2* y / 3);
            // }
            else {

            points.add(x);
            points.add(y);
            }

        }

        // Offset the polygon to make it irregular
        // int i = 0; // Initialize i outside the loop

        // while (i < points.size()) {
        //     if (i + 8 < points.size() && points.get(i) < 0 && points.get(i + 1) > 0 && points.get(i + 2) < 0 && points.get(i + 3) < 0) {
        //         // Insert the desired points
        //         points.add(i + 1, - size / 2);
        //         points.add(i + 2, size / 2);
        //         points.add(i + 3, - 2 * size);
        //         points.add(i + 4, size / 2);
        //         points.add(i + 5, - 2 * size);
        //         points.add(i + 6, - size / 2);
        //         points.add(i + 7,  size / 2);
        //         points.add(i + 8, - size / 2);
                
        //         // Move the index i to the end of the newly added points
        //         i += 9;
        //     } else {
        //         // Increment the index normally
        //         i += 2;
        //     }
        // }
        Polygon polygon = new Polygon(); // Create a new Polygon object
        polygon.getPoints().addAll(points); // Add the coordinates to the polygon's points
        System.out.println(points);
        // Apply a random rotation to the entire polygon
        double randomRotation = rnd.nextDouble() * 360; // Random rotation angle in degrees
        polygon.setRotate(randomRotation);

        return polygon;// Return the created polygon
    }

}