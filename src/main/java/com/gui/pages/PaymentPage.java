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

public class PaymentPage extends VBox implements RouterListener {

    private TableView<Payment> paymentTable;
    private ObservableList<Payment> payments = FXCollections.observableArrayList();
    private Stage stage;
    private Router router;
    private ComboBox<Integer> userIDComboBox = new ComboBox<>();

    public PaymentPage(
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
        Label titleLabel = new Label("Total Payment");
        titleLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-family: 'Georgia';" +
            "-fx-padding: 5px;"
        );
        HBox titleLayout = new HBox(titleLabel);
        titleLayout.setAlignment(Pos.CENTER);
        titleLayout.setPadding(new Insets(20, 0, 0, 20));
        container.setTop(titleLayout);

        // Create a VBox to hold the payment table
        VBox paymentBox = new VBox();
        paymentBox.setPadding(new Insets(10, 0, 0, 20));
        paymentBox.setSpacing(10);

        Label userIDLabel = new Label("User ID: ");

        // SAMPLE (List of Payments made by Many Users)
        List<Payment> sample = new ArrayList<>();
        // Add sample payment data to the table
        List<Product> products1 = new ArrayList<>();
        products1.add(new Product(1, "Product 1", 10, 10.0f, "Category 1"));
        products1.add(new Product(1, "Product 2", 15, 15.0f, "Category 1"));
        Payment payment1 = new Payment(1, products1, 25);

        List<Product> products2 = new ArrayList<>();
        products2.add(new Product(2, "Product 3", 20, 20.0f, "Category 2"));
        products2.add(new Product(3, "Product 4", 30, 25.0f, "Category 2"));
        Payment payment2 = new Payment(2, products2, 75);
        sample.add(payment1);
        sample.add(payment2);

        List<Integer> userIds = new ArrayList<>();
        for (Payment p : sample) {
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
                // TODO: change to router.getSystemPayments()
                for (Payment p : sample) {
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

        TableColumn<Payment, Float> totalPriceColumn = new TableColumn<>("Total Price");
        totalPriceColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getTotalPrice()).asObject());

        paymentTable = new TableView<>();
        paymentTable.setItems(FXCollections.observableArrayList(sample)); // TODO: replace sample
        paymentTable.getColumns().addAll(itemsColumn, totalPriceColumn);
        paymentTable.setItems(payments);
        paymentTable.widthProperty().addListener((source, oldWidth, newWidth) -> {
            itemsColumn.setPrefWidth(newWidth.doubleValue() / 2);
            totalPriceColumn.setPrefWidth(newWidth.doubleValue() / 2);
        });

        paymentBox.getChildren().add(paymentTable);
        paymentBox.setPadding(new Insets(20, 20, 20, 20));

        Button payButton = new Button("Confirm");
        payButton.setStyle(
            "-fx-background-color: green;" + 
            "-fx-text-fill: white;" +
            "-fx-padding: 10px 20px;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;"
        );
        
        HBox backHLayout = new HBox(payButton);
        backHLayout.setAlignment(Pos.BOTTOM_CENTER);
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
