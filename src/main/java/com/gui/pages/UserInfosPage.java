package com.gui.pages;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

import com.gui.Router;
import com.gui.interfaces.RouterListener;
import com.gui.components.*;
import com.logic.feature.*;

public class UserInfosPage extends VBox implements RouterListener {

    private TableView<Member> userTable;
    private ObservableList<Member> users = FXCollections.observableArrayList();
    // private ObservableList<VIP> vips = FXCollections.observableArrayList();
    private Stage stage;
    private Router router;

    public UserInfosPage(
            Router router,
            Stage stage) {
        this.stage = stage;
        this.router = router;

        BorderPane container = new BorderPane();

        // Create a label for the title of the page
        Label titleLabel = new Label("User Information");
        titleLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-family: 'Georgia';" +
            "-fx-padding: 10px;" +
            "-fx-border-width: 1;" +
            "-fx-border-color: gray;" +
            "-fx-border-style: dashed;"
        );
        HBox titleLayout = new HBox(titleLabel);
        titleLayout.setAlignment(Pos.CENTER);
        titleLayout.setPadding(new Insets(20, 0, 0, 20));
        container.setTop(titleLayout);

        // Create a VBox to hold the user table
        VBox userBox = new VBox();
        userBox.setPadding(new Insets(10, 0, 0, 20));
        userBox.setSpacing(10);

        // Table to display user information
        TableColumn<Member, Integer> userIDColumn = new TableColumn<>("User ID");
        userIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        userTable = new TableView<>();

        // Sample
        Member member1 = new Member(1, "abc", "123");
        VIP member2 = new VIP(2, "cde", "456");

        users.addAll(member1, member2);
        userTable.getColumns().addAll(userIDColumn);
        
        userTable.setItems(users);
        userTable.widthProperty().addListener((source, oldWidth, newWidth) -> {
            userIDColumn.setPrefWidth(newWidth.doubleValue());
        });

        userBox.getChildren().add(userTable);
        userBox.setPadding(new Insets(20, 20, 20, 20));

        Button updateButton = new Button("Update");
        updateButton.setStyle(
            "-fx-background-color: green;" + 
            "-fx-text-fill: white;" +
            "-fx-padding: 10px 20px;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;"
        );
        // TODO: When the updateButton is clicked, user go to updateInfoPage

        BaseButton backButton = new BaseButton("Back");
        HBox backHLayout = new HBox();
        backHLayout.getChildren().addAll(updateButton, backButton);
        backHLayout.setAlignment(Pos.BOTTOM_RIGHT);

        HBox.setMargin(backButton, new Insets(0, 0, 0, 880));
        backHLayout.setPadding(new Insets(0, 20, 20, 20));

        container.setCenter(userBox);
        container.setBottom(backHLayout);

        // Append to VBox    
        getChildren().addAll(container);
        this.setAlignment(Pos.CENTER);
    }

    @Override
    public void onResourceUpdate() {
        List<Member> systemMember = this.router.getSystemMembers();
        List<VIP> systemVIPs = this.router.getSystemVIPs();
        this.users.clear();
        this.users.addAll(systemMember);
        this.users.addAll(systemVIPs);
        this.userTable.refresh();
    }
}

