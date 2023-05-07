package com.gui.pages;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;

import com.logic.constant.Payment;
import com.logic.feature.Product;
import com.gui.Router;
import com.gui.components.*;
import com.gui.interfaces.RouterListener;

public class PaymentHistoryPage extends VBox implements RouterListener {

    private TableView<Payment> paymentTable;
    private ObservableList<Payment> payments = FXCollections.observableArrayList();
    private List<Integer> userIds;
    private Stage stage;
    private Router router;
    private ComboBox<Integer> userIDComboBox = new ComboBox<>();

    public PaymentHistoryPage(
            Router router,
            Stage stage) {
        this.stage = stage;
        this.router = router;
        BorderPane container = new BorderPane();

        VBox upperLayout = new VBox();
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Create a label for the title of the page
        Label titleLabel = new Label("Payment History");
        titleLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-family: 'Georgia';" +
            "-fx-padding: 5px;"
        );
        HBox titleLayout = new HBox(titleLabel);
        titleLayout.setAlignment(Pos.TOP_LEFT);
        titleLayout.setPadding(new Insets(20, 0, 15, 15));
        container.setTop(titleLayout);

        // Create a VBox to hold the payment table
        VBox paymentBox = new VBox();
        paymentBox.setPadding(new Insets(20, 0, 0, 20));
        paymentBox.setSpacing(10);

        Label userIDLabel = new Label("User ID: ");

        userIds = new ArrayList<>();
        List<Payment> paymentList = router.getSystemPayments();
        for (Payment p : paymentList) {
            int userId = p.getUserID();
            if (!userIds.contains(userId)) {
                userIds.add(userId);
            }
        }
        Collections.sort(userIds);
        userIDComboBox.setItems(FXCollections.observableArrayList(userIds));

        userIDComboBox.setOnAction(event -> {
            Integer selectedUserId = userIDComboBox.getSelectionModel().getSelectedItem();
            if (selectedUserId != null) {
                // Clear the payments list
                payments.clear();
                // Add only the payments corresponding to the selected user ID
                for (Payment p : paymentList) {
                    if (p.getUserID() == selectedUserId) {
                        payments.add(p);
                    }
                }
                // Refresh the paymentTable to update the view
                paymentTable.refresh();
            }
        });

        
        HBox userIdBox = new HBox();
        gridPane.add(userIDLabel, 0, 0);
        gridPane.add(userIDComboBox, 1, 0);
        userIdBox.getChildren().addAll(gridPane);
        userIdBox.setAlignment(Pos.TOP_LEFT);
        userIdBox.setPadding(new Insets(2, 0, 0, 20));
        upperLayout.getChildren().addAll(titleLayout, userIdBox);
        container.setTop(upperLayout);


        // Table to display payment information
        TableColumn<Payment, String> itemsColumn = new TableColumn<>("Items");
        itemsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBoughtItems().toString()));

        // Custom TableCell to display products in different rows
        
        TableColumn<Payment, Float> totalPriceColumn = new TableColumn<>("Total Price");
        totalPriceColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getTotalPrice()).asObject());

        paymentTable = new TableView<>();

        paymentTable.getColumns().addAll(itemsColumn, totalPriceColumn);
        paymentTable.setItems(payments);
        paymentTable.widthProperty().addListener((source, oldWidth, newWidth) -> {
            itemsColumn.setPrefWidth(newWidth.doubleValue() / 2);
            totalPriceColumn.setPrefWidth(newWidth.doubleValue() / 2);
        });

        paymentBox.getChildren().add(paymentTable);
        paymentBox.setPadding(new Insets(20, 20, 20, 20));

        container.setCenter(paymentBox);

        // Append to VBox    
        getChildren().addAll(container);
        this.setAlignment(Pos.CENTER);
    }

    @Override
    public void onResourceUpdate() {
        this.userIds.clear();
        this.payments.clear();
        List<Payment> storedPayments = this.router.getSystemPayments();
        for (Payment p : storedPayments) {
            if (!this.userIds.contains(p.getUserID())) { // Remove duplicate id
                this.userIds.add(p.getUserID());
            }
            this.payments.add(p);
        }
        Collections.sort(this.userIds);
        ObservableList<Integer> newItems = FXCollections.observableArrayList(this.userIds);
        this.userIDComboBox.setItems(newItems);
        this.paymentTable.refresh();
    }
}
