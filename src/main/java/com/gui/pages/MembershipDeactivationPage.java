package com.gui.pages;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import com.gui.interfaces.PageSwitcher;
import javafx.geometry.Insets;
import com.gui.components.*;

public class MembershipDeactivationPage extends VBox {

    public MembershipDeactivationPage(PageSwitcher pageCaller) {
        // For Fonts
        Font titleFont = Font.font("Georgia", FontWeight.BOLD,18);
        Font textFont = Font.font("Times New Roman", 16);

        // Title Label
        Label titleLabel = new Label("MEMBERSHIP DEACTIVATION");
        titleLabel.setFont(titleFont);

        // HBox for Title Label
        HBox titleHLayout = new HBox(titleLabel);
        titleHLayout.setAlignment(Pos.CENTER);

        // For the Question
        Label questionLabel = new Label("Are you sure about your choice?");
        questionLabel.setFont(textFont);

        HBox questionHLayout = new HBox(questionLabel);
        questionHLayout.setAlignment(Pos.CENTER);

        // Deactivation Button
        Button confirmButton = new Button("Confirm");
        HBox confirmHLayout = new HBox(confirmButton);
        confirmHLayout.setAlignment(Pos.CENTER);
        confirmButton.setStyle(
                "-fx-background-color: red;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-family: 'Georgia';" +
                        "-fx-font-size: 12px;" +
                        "-fx-pref-width: 100px"
        );

        BaseButton backButton = new BaseButton("Back");
        HBox backHLayout = new HBox(backButton);
        backHLayout.setAlignment(Pos.BOTTOM_RIGHT);

        // Create a VBox as a container
        VBox container = new VBox();
        container.getChildren().addAll(titleHLayout, questionHLayout, confirmHLayout, backHLayout);

        VBox.setMargin(titleHLayout, new Insets(200, 0, 50, 0));
        VBox.setMargin(confirmHLayout, new Insets(15, 0, 75, 0));
        VBox.setMargin(backHLayout, new Insets(120, 30, 0, 0));

        // Append to VBox    
        getChildren().addAll(container);
        this.setAlignment(Pos.CENTER);
    }
}
