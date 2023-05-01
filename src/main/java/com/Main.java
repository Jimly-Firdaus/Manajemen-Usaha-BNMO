package com;

import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.gui.Router;

public class Main extends Application {
    // private StackPane root = new StackPane();

    @Override
    public void start(Stage primaryStage) {
        Router router = new Router(primaryStage);
        router.gotoMainPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
