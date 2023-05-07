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

import java.util.Iterator;
import java.util.List;

import com.gui.Router;
import com.logic.feature.Customer;
import com.logic.feature.Member;
import com.logic.feature.VIP;

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
        Label idLabel = new Label("ID: ");
        idLabel.setFont(textFont);
        gridPane.add(idLabel, 0, 0);

        TextField idField = new TextField();
        gridPane.add(idField, 1, 0);

        Label nameLabel = new Label("Name:");
        nameLabel.setFont(textFont);
        gridPane.add(nameLabel, 0, 1);

        TextField nameField = new TextField();
        gridPane.add(nameField, 1, 1);

        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(textFont);
        gridPane.add(phoneLabel, 0, 2);

        TextField phoneField = new TextField();
        gridPane.add(phoneField, 1, 2);

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

    

        // Create a VBox as a container
        VBox container = new VBox();
        container.getChildren().addAll(titleHLayout, inputHLayout, submitHLayout);

        VBox.setMargin(titleHLayout, new Insets(35, 0, 35, 0));
        VBox.setMargin(submitHLayout, new Insets(15, 0, 20, 0));

        // Submit Button
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String id = idField.getText();

            List<Member> storeMember = router.getSystemMembers();
            List<Customer> storeCustomer = router.getSystemCustomers();
            List<VIP> storeVIP = router.getSystemVIPs();
            if (!name.equals("")) {
                // Perform checking to system customer here, if exits then check if eligible to upgrade
                try {
                    int userId = Integer.parseInt(id);
                    boolean isCustomer = false;
                    Iterator<Customer> customerIterator = storeCustomer.iterator();
                    while (customerIterator.hasNext()) {
                        Customer c = customerIterator.next();
                        if (c.getId() == userId && c.getFirstPurchaseStatus()) {
                            storeMember.add(c.upgradeToMember(name, phone));
                            customerIterator.remove(); // Remove customer from storeCustomer list
                            isCustomer = true;
                            router.notifyListeners();
                            break;
                        }
                    }
                    if (!isCustomer) {
                        Iterator<Member> memberIterator = storeMember.iterator();
                        while (memberIterator.hasNext()) {
                            Member m = memberIterator.next();
                            if (m.getId() == userId) {
                                storeVIP.add(m.upgradeToVIP(name, phone));
                                memberIterator.remove(); // Remove member from storeMember list
                                router.notifyListeners();
                                break;
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    // Can display error label via here

                }
            }

            // Clear the input field
            nameField.clear();
            phoneField.clear();
            idField.clear();
        });

        // Append to VBox    
        getChildren().addAll(container);
        this.setAlignment(Pos.CENTER);
    }
}