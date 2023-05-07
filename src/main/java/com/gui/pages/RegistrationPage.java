package com.gui.pages;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.util.List;

import com.gui.interfaces.PageSwitcher;
import com.gui.Router;
import com.gui.components.*;
import com.logic.feature.Customer;
import com.logic.feature.Member;

public class RegistrationPage extends VBox {
    private Stage stage;

    public RegistrationPage(
            Router router,
            Stage stage) {
        this.stage = stage;

        // For Fonts
        Font titleFont = Font.font("Georgia", FontWeight.BOLD,18);
        Font textFont = Font.font("Times New Roman", 15);

        // Title Label
        Label titleLabel = new Label("MEMBER REGISTRATION");
        titleLabel.setFont(titleFont);

        // HBox for Title Label
        HBox titleHLayout = new HBox(titleLabel);
        titleHLayout.setAlignment(Pos.CENTER);

        // Create a grid pane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);

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

        // Create a HBox container and add the GridPane to it
        HBox inputHLayout = new HBox(gridPane);
        inputHLayout.setAlignment(Pos.CENTER);

        // Submit Button
        Button submitButton = new Button("Register");
        HBox submitHLayout = new HBox(submitButton);
        submitHLayout.setAlignment(Pos.CENTER);
        submitButton.setStyle(
                "-fx-background-color: blue;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-font-family: 'Georgia';" +
                "-fx-font-size: 12px;" +
                "-fx-pref-width: 250px"
        );

        // Back Button
        BaseButton backButton = new BaseButton("Back");
        HBox backHLayout = new HBox(backButton);
        backHLayout.setAlignment(Pos.BOTTOM_RIGHT);

        // Create a VBox as a container
        VBox container = new VBox();
        container.getChildren().addAll(titleHLayout, inputHLayout, submitHLayout, backHLayout);

        VBox.setMargin(titleHLayout, new Insets(200, 0, 35, 0));
        VBox.setMargin(submitHLayout, new Insets(15, 0, 75, 0));
        VBox.setMargin(backHLayout, new Insets(95, 30, 0, 0));

        // Submit Button
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String phone = phoneField.getText();

            List<Member> storeMember = router.getSystemMembers();
            List<Customer> storeCustomer = router.getSystemCustomers();
            if (!name.equals("")) {
                // Perform checking to system customer here, if exits then check if eligible to upgrade

                // if eligible
                storeMember.add(new Member(storeMember.size() + 1, name, phone));
                System.out.println("pass here");
                List<Member> res = router.getSystemMembers();
                for (Member mem : res) {
                    System.out.println(mem.toString());
                }
            }

            // Clear the input field
            nameField.clear();
            phoneField.clear();
        });

        // Append to VBox    
        getChildren().addAll(container);
        this.setAlignment(Pos.CENTER);
    }
}