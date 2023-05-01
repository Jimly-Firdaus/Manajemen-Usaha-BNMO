package com;

import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.Scene;
import com.gui.Router;
import javafx.scene.layout.StackPane;

public class Main extends Application {
    // private StackPane root = new StackPane();

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1080, 608);

        // Center the window
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() - scene.getWidth()) / 2;
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() - scene.getHeight()) / 2;
        primaryStage.setX(centerX);
        primaryStage.setY(centerY);

        Router router = new Router(primaryStage);
        router.gotoMainPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
