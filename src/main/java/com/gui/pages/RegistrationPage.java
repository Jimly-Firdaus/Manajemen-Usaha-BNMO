package com.gui.pages;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import com.gui.interfaces.PageSwitcher;

public class RegistrationPage extends VBox {

    public RegistrationPage(PageSwitcher pageCaller) {
        // For Fonts
        Font titleFont = Font.font("Georgia", FontWeight.BOLD,18);
        Font textFont = Font.font("Times New Roman", 15);

        // Create a grid pane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Title Label
        Label titleLabel = new Label("MEMBER REGISTRATION");
        titleLabel.setFont(titleFont);

        // HBox for Title Label
        HBox titleHLayout = new HBox(titleLabel);
        titleHLayout.setAlignment(Pos.CENTER);

        // Create labels and text fields for name and phone number input
        Label nameLabel = new Label("Name:");
        nameLabel.setFont(textFont);
        gridPane.add(nameLabel, 0, 0);

        TextField nameField = new TextField();
        gridPane.add(nameField, 1, 0);

        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(textFont);
        gridPane.add(phoneLabel, 0, 1);

        TextField phoneField = new TextField();
        gridPane.add(phoneField, 1, 1);

        // Create a button for submitting the registration form
        Button submitButton = new Button("Register");
        gridPane.add(submitButton, 1, 2);
        submitButton.setStyle(
                "-fx-background-color: blue;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-font-family: 'Georgia';" +
                "-fx-font-size: 12px;"
        );

        // Create a HBox container and add the GridPane to it
        HBox inputHLayout = new HBox(gridPane);
        inputHLayout.setAlignment(Pos.CENTER);

        // Create a VBox as a container
        VBox container = new VBox();
        container.getChildren().addAll(titleHLayout, inputHLayout);
        container.setSpacing(30);
        container.setAlignment(Pos.CENTER);

        // Submit Button
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String phone = phoneField.getText();

            // TODO: Save the information to the database

            // Clear the input field
            nameField.clear();
            phoneField.clear();
        });

        // Append to VBox    
        getChildren().addAll(container);
        this.setAlignment(Pos.CENTER);
    }
}