package com.gui.pages;

import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import java.io.File;
import java.util.List;

import com.gui.interfaces.PageSwitcher;
import com.gui.components.*;
import com.logic.store.interfaces.Parseable;
import com.logic.store.ParserJSON;
import com.logic.store.ParserXML;
import com.logic.store.ParserOBJ;
import com.logic.feature.Bill;
import com.logic.feature.Customer;
import com.logic.feature.Inventory;

public class TransactionHistory extends VBox {

    private Label titleLabel;
    private ComboBox<Customer> customerComboBox;
    private ListView<String> transactionListView;

    public TransactionHistory(Stage stage) {
        titleLabel = new Label("Transaction History");

        // Initialize ComboBox with list of customers
        ObservableList<Customer> customerList = FXCollections.observableArrayList(Store.getInstance().getCustomers());
        customerComboBox = new ComboBox<>(customerList);
        customerComboBox.setPromptText("Select a customer");

        // Initialize ListView
        transactionListView = new ListView<>();

        // Add listener to ComboBox selection change
        customerComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // If a customer is selected, show transaction history
            if (newValue != null) {
                StringBuilder sb = new StringBuilder();
                List<Bill> bills = Store.getInstance().getBillsByCustomerId(newValue.getId());
                for (Bill bill : bills) {
                    sb.append(bill.toString()).append("\n");
                }
                transactionListView.getItems().clear();
                transactionListView.getItems().addAll(sb.toString().split("\n"));
            } else {
                // If no customer is selected, clear ListView
                transactionListView.getItems().clear();
            }
        });

        // Set VBox properties
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.getChildren().addAll(titleLabel, customerComboBox, transactionListView);
    }
}
