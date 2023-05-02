package com.gui.pages;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import com.gui.interfaces.PageSwitcher;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.gui.components.*;

public class UpdateInfoPage extends VBox {

    public UpdateInfoPage(PageSwitcher pageCaller) {
        // For Fonts
        Font titleFont = Font.font("Georgia", FontWeight.BOLD,16);
        Font textFont = Font.font("Times New Roman", 15);

        // Title Label
        Label titleLabel = new Label("UPDATE YOUR INFORMATION");
        titleLabel.setFont(titleFont);

        // HBox for Title Label
        HBox titleHLayout = new HBox(titleLabel);
        titleHLayout.setAlignment(Pos.CENTER);

        // Create a grid pane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // For Name and Phone Number
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

        // For Membership
        Label memberLabel = new Label("Membership: ");
        memberLabel.setFont(textFont);
        gridPane.add(memberLabel, 0, 2);

        ObservableList<String> options = FXCollections.observableArrayList("Member", "VIP");
        final ComboBox membershipField = new ComboBox(options);
        membershipField.setPrefWidth(150);
        gridPane.add(membershipField, 1, 2);

        // Create a HBox container and add the GridPane to it
        HBox inputHLayout = new HBox(gridPane);
        inputHLayout.setAlignment(Pos.CENTER);

        // Create a button for submitting the registration form
        Button submitButton = new Button("Change");
        HBox submitHLayout = new HBox(submitButton);
        submitHLayout.setAlignment(Pos.CENTER);
        submitButton.setStyle(
                "-fx-background-color: blue;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-family: 'Georgia';" +
                        "-fx-font-size: 12px;" +
                        "-fx-pref-width: 250px"
        );

        BaseButton backButton = new BaseButton("Back");
        HBox backHLayout = new HBox(backButton);
        backHLayout.setAlignment(Pos.BOTTOM_RIGHT);

        // Create a VBox as a container
        VBox container = new VBox();
        container.getChildren().addAll(titleHLayout, inputHLayout, submitHLayout, backHLayout);

        VBox.setMargin(titleHLayout, new Insets(165, 0, 35, 0));
        VBox.setMargin(submitHLayout, new Insets(15, 0, 75, 0));
        VBox.setMargin(backHLayout, new Insets(105, 30, 0, 0));

        // Handle the button click event to save the member's information
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