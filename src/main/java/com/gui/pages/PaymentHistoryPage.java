package com.gui.pages;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;

import com.logic.constant.Payment;
import com.gui.Router;
import com.gui.components.*;
import com.gui.interfaces.RouterListener;

public class PaymentHistoryPage extends VBox implements RouterListener {

    private TableView<Payment> paymentTable;
    private ObservableList<Payment> payments = FXCollections.observableArrayList();
    private Stage stage;
    private Router router;

    public PaymentHistoryPage(
            Router router,
            Stage stage) {
        this.stage = stage;
        this.router = router;
        BorderPane container = new BorderPane();

        // Create a label for the title of the page
        Label titleLabel = new Label("Payment History");
        titleLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-family: 'Georgia';" +
            "-fx-padding: 5px;"
        );
        HBox titleLayout = new HBox(titleLabel);
        titleLayout.setAlignment(Pos.TOP_LEFT);
        titleLayout.setPadding(new Insets(20, 0, 0, 20));
        container.setTop(titleLayout);

        // Create a VBox to hold the payment table
        VBox paymentBox = new VBox();
        paymentBox.setPadding(new Insets(10, 0, 0, 20));
        paymentBox.setSpacing(10);

        // Table to display payment information
        TableColumn<Payment, Integer> userIDColumn = new TableColumn<>("User ID");
        userIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserID()).asObject());

        TableColumn<Payment, String> itemsColumn = new TableColumn<>("Items");
        itemsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBoughtItems().toString()));

        TableColumn<Payment, Float> totalPriceColumn = new TableColumn<>("Total Price");
        totalPriceColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getTotalPrice()).asObject());

        paymentTable = new TableView<>();

        paymentTable.getColumns().addAll(userIDColumn, itemsColumn, totalPriceColumn);
        paymentTable.setItems(payments);
        paymentTable.widthProperty().addListener((source, oldWidth, newWidth) -> {
            userIDColumn.setPrefWidth(newWidth.doubleValue() / 3);
            itemsColumn.setPrefWidth(newWidth.doubleValue() / 3);
            totalPriceColumn.setPrefWidth(newWidth.doubleValue() / 3);
        });

        paymentBox.getChildren().add(paymentTable);
        paymentBox.setPadding(new Insets(20, 20, 20, 20));

        BaseButton backButton = new BaseButton("Back");
        HBox backHLayout = new HBox(backButton);
        backHLayout.setAlignment(Pos.BOTTOM_RIGHT);
        backHLayout.setPadding(new Insets(0, 20, 20, 20));

        container.setCenter(paymentBox);
        container.setBottom(backHLayout);

        // Append to VBox    
        getChildren().addAll(container);
        this.setAlignment(Pos.CENTER);
    }

    @Override
    public void onResourceUpdate() {
        this.payments.clear();
        List<Payment> storedPayments = this.router.getSystemPayments();
        for (Payment p : storedPayments) {
            this.payments.add(p);
        }
        this.paymentTable.refresh();
    }
}
