package com.gui.pages;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.gui.interfaces.PageSwitcher;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

import com.logic.feature.Member;
import com.logic.feature.VIP;
import com.gui.Router;
import com.gui.components.*;

public class UpdateInfoPage extends Stage {
    private Stage stage;

    public UpdateInfoPage(
            Router router,
            Integer userId,
            Stage stage) {
        this.stage = stage;

        // For Fonts
        Font titleFont = Font.font("Georgia", FontWeight.BOLD, 16);
        Font textFont = Font.font("Times New Roman", 15);

        // Title Label
        Label titleLabel = new Label("UPDATE USER INFORMATION");
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
                        "-fx-pref-width: 250px");

        BaseButton saveButton = new BaseButton("Save");
        saveButton.setOnAction(
                event -> close());
        HBox saveHLayout = new HBox(saveButton);
        saveHLayout.setAlignment(Pos.BOTTOM_RIGHT);

        // Create a VBox as a container
        VBox container = new VBox();
        container.getChildren().addAll(titleHLayout, inputHLayout, submitHLayout, saveHLayout);

        VBox.setMargin(titleHLayout, new Insets(80, 0, 35, 0));
        VBox.setMargin(submitHLayout, new Insets(15, 0, 0, 0));
        VBox.setMargin(saveHLayout, new Insets(65, 30, 0, 0));

        // Handle the button click event to save the member's information
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String level = (String) membershipField.getValue();

            List<Member> storeMember = router.getSystemMembers();
            List<VIP> storeVIP = router.getSystemVIPs();
            // TODO: Save the information to the database
            if (!name.equals("") && !phone.equals("") && !level.equals(null)) {
                boolean isMember = false;
                Iterator<Member> memberIterator = storeMember.iterator();
                while (memberIterator.hasNext()) {
                    Member c = memberIterator.next();
                    if (c.getId() == userId) {
                        c.setName(name);
                        c.setPhoneNumber(phone);
                        if (level.equals("VIP")) {
                            storeVIP.add(c.upgradeToVIP(name, phone));
                            memberIterator.remove();
                        }
                        isMember = true;
                        router.notifyListeners();
                        break;
                    }
                }
                if (!isMember) {
                    Iterator<VIP> vipIterator = storeVIP.iterator();
                    while (vipIterator.hasNext()) {
                        VIP c = vipIterator.next();
                        if (c.getId() == userId) {
                            c.setName(name);
                            c.setPhoneNumber(phone);
                            if (level.equals("Member")) {
                                storeMember.add(new Member(userId, name, phone));
                                vipIterator.remove();
                            }
                            router.notifyListeners();
                            break;
                        }
                    }
                }
            }

            // Clear the input field
            nameField.clear();
            phoneField.clear();
        });

        // Set the scene of the popup window
        Scene scene = new Scene(container, 608, 405);
        setScene(scene);
    }
}
