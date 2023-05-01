package com.gui.components;

import javafx.scene.control.Button;

public class BaseButton extends Button {
    public BaseButton (String text) {
        super(text);
        String style =  "-fx-background-color: #024F94;\n" + 
                        "-fx-text-fill: white;\n" +
                        "-fx-padding: 10px 20px;\n" +
                        "-fx-border-radius: 5px;\n" +
                        "-fx-background-radius: 5px;";
        setStyle(style);
    }
}
