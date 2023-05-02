package com.gui.pages;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.gui.Router;

public class MainPage extends VBox {
    public MainPage(Router router) {
        Label label = new Label("This is the main page");

        getChildren().addAll(label);
    }
}
