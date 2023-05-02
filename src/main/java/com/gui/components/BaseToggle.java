package com.gui.components;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class BaseToggle extends HBox {
    private ToggleGroup group;

    public BaseToggle(String opt1, String opt2, Button button, boolean isVertical) {
        this.group = new ToggleGroup();
        RadioButton radio1 = new RadioButton(opt1);
        radio1.setToggleGroup(group);
        RadioButton radio2 = new RadioButton(opt2);
        radio2.setToggleGroup(group);

        // TODO: styles

        // getChildren().addAll(radio1, radio2);
        
        if (isVertical) {
            VBox vbox = new VBox(radio1, radio2);
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            getChildren().add(vbox);
        } else {
            HBox hbox = new HBox(radio1, radio2);
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(10);
            getChildren().add(hbox);
        }
        setAlignment(Pos.CENTER);
        
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                button.setDisable(true);
            } else {
                button.setDisable(false);
            }
        });

        // Default
        button.setDisable(true);
    }

    public String getSelected() {
        Toggle selectedToggle = group.getSelectedToggle();
        if (selectedToggle == null) {
            return null;
        } else {
            return ((RadioButton) selectedToggle).getText();
        }
    }

}
