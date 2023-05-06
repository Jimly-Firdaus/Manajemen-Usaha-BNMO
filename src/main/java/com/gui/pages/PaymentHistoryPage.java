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
import com.logic.constant.Payment;
import com.logic.feature.Product;
import com.gui.Router;
import com.gui.components.*;
import java.util.*;


public class PaymentHistoryPage extends VBox {

    private TableView<Payment> paymentTable;
    private ObservableList<Payment> payments = FXCollections.observableArrayList();
    private Stage stage;

    public PaymentHistoryPage(
            Router router,
            Stage stage) {
        this.stage = stage;
        
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

        // Add sample payment data to the table
        List<Product> products1 = new ArrayList<>();
        products1.add(new Product(1, "Product 1", 10, 10.0f, "Category 1"));
        products1.add(new Product(1, "Product 2", 15, 15.0f, "Category 1"));
        Payment payment1 = new Payment(1, products1, 25);
        
        List<Product> products2 = new ArrayList<>();
        products2.add(new Product(2, "Product 3", 20, 20.0f, "Category 2"));
        products2.add(new Product(3, "Product 4", 30, 25.0f, "Category 2"));
        Payment payment2 = new Payment(2, products2, 75);

        payments.addAll(payment1, payment2);
        paymentTable.getColumns().addAll(userIDColumn, itemsColumn, totalPriceColumn);
        paymentTable.setItems(payments);

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
}
