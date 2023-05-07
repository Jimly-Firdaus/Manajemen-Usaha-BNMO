package com.gui.components;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BaseCard extends VBox {
    public BaseCard(String title, String content) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        getChildren().add(titleLabel);

        Label contentLabel = new Label(content);
        getChildren().add(contentLabel);

        setStyle("-fx-padding: 10px; -fx-border-color: #d3d3d3; -fx-border-width: 1px;");
    }
}
