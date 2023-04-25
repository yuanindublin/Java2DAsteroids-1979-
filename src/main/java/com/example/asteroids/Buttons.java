package com.example.asteroids;
import javafx.scene.Cursor;
import javafx.scene.control.Button;

public class Buttons {
    // format buttons
    public static void setButton(Button Button) {
		Button.setStyle("-fx-font-family: Silkscreen; -fx-font-size: 30; -fx-background-color: transparent; -fx-text-fill: white;");
        Button.setOnMouseEntered(event -> {
			Button.setCursor(Cursor.HAND);
		});
        Button.setOnMouseExited(event -> {
			Button.setCursor(Cursor.DEFAULT);
		});
    }
    
}
