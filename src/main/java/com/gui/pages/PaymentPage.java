package com.gui.pages;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

import com.logic.constant.Payment;
import com.logic.feature.Bill;
import com.logic.feature.Customer;
import com.logic.feature.Member;
import com.logic.feature.Product;
import com.logic.feature.VIP;
import com.gui.Router;
import com.gui.interfaces.RouterListener;

public class PaymentPage extends VBox implements RouterListener {

    private TableView<Bill> billTable;
    private List<String> userIds;
    private ObservableList<Bill> bills = FXCollections.observableArrayList();
    private Stage stage;
    private Router router;
    private ComboBox<String> userIDComboBox = new ComboBox<>();

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
                        "-fx-padding: 5px;");
        HBox titleLayout = new HBox(titleLabel);
        titleLayout.setAlignment(Pos.CENTER);
        titleLayout.setPadding(new Insets(20, 0, 0, 20));
        container.setTop(titleLayout);

        // Create a VBox to hold the payment table
        VBox paymentBox = new VBox();
        paymentBox.setPadding(new Insets(10, 0, 0, 20));
        paymentBox.setSpacing(10);

        Label userIDLabel = new Label("User ID: ");

        userIds = new ArrayList<>();
        List<Bill> billList = router.getSystemBills();
        for (Bill b : billList) {
            int userId = b.getIdCustomer();
            if (!userIds.contains(userId) && b.isBillFixed() && !b.isBillDone()) {
                userIds.add(Integer.toString(userId));
            }
        }

        Collections.sort(userIds);
        userIDComboBox.setEditable(true);
        userIDComboBox.setItems(FXCollections.observableArrayList(userIds));

        // Disable the ComboBox editor
        userIDComboBox.getEditor().setDisable(true);

        // Enable the editor when the dropdown list is shown
        userIDComboBox.setOnShown(event -> {
            userIDComboBox.getEditor().setDisable(false);
            userIDComboBox.getEditor().requestFocus();
        });

        // Disable the editor when the dropdown list is hidden
        userIDComboBox.setOnHidden(event -> {
            userIDComboBox.getEditor().setDisable(true);
        });

        // Add a listener to filter the items based on user input
        userIDComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!userIDComboBox.isShowing()) {
                return;
            }

            userIDComboBox.setItems(FXCollections.observableArrayList(userIds));
            userIDComboBox.getItems()
                    .removeIf(item -> !item.toString().toLowerCase().startsWith(newValue.toLowerCase()));

            if (userIDComboBox.getItems().isEmpty()) {
                userIDComboBox.hide();
            } else {
                userIDComboBox.show();
            }
        });

        userIDComboBox.setOnAction(event -> {
            String selectedUserId = userIDComboBox.getSelectionModel().getSelectedItem();
            // Clear the bills list
            bills.clear();
            if (selectedUserId != null) {
                // Add only the bills corresponding to the selected user ID
                for (Bill b : billList) {
                    try {
                        Integer userId = Integer.parseInt(selectedUserId);
                        if (b.getIdCustomer() == userId && b.isBillFixed() && !b.isBillDone()) {
                            bills.add(b);
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
            // Refresh the billTable to update the view
            billTable.refresh();
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
        TableColumn<Bill, String> itemsColumn = new TableColumn<>("Items");
        itemsColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBasket().toString()));

        billTable = new TableView<>();
        billTable.setItems(FXCollections.observableArrayList(billList));
        billTable.getColumns().addAll(itemsColumn);
        billTable.setItems(bills);
        billTable.widthProperty().addListener((source, oldWidth, newWidth) -> {
            itemsColumn.setPrefWidth(newWidth.doubleValue());
        });

        billTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        paymentBox.getChildren().add(billTable);
        paymentBox.setPadding(new Insets(20, 20, 20, 20));

        Button payButton = new Button("Confirm");
        payButton.setStyle(
                "-fx-background-color: green;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;");
        payButton.setOnAction(
                event -> {
                    Bill selectedBill = billTable.getSelectionModel().getSelectedItem();
                    if (selectedBill == null) {
                        List<Customer> storeCustomers = router.getSystemCustomers();
                        int id = router.getSystemCustomers().size() + router.getSystemMembers().size() + router.getSystemVIPs().size();
                        storeCustomers.add(new Customer(id));
                    } else {
                        List<Bill> storeBills = router.getSystemBills();
                        Iterator<Bill> billIterator = storeBills.iterator();
                        while (billIterator.hasNext()) {
                            Bill b = billIterator.next();
                            if (selectedBill.equals(b)) {
                                b.setBillDone(true);
                                break;
                            }
                        }
                        List<Product> storeProducts = router.getSystemProducts();
                        List<Product> boughtItems = selectedBill.getBasket().getProductList();
                        float totalPrice = 0;
                        for (Product boughtItem : boughtItems) {
                            for (Product storeProduct : storeProducts) {
                                if (storeProduct.getProductName().equals(boughtItem.getProductName())) {
                                    int newQuantity = storeProduct.getCount() - boughtItem.getCount();
                                    storeProduct.setCount(newQuantity);
                                    break;
                                }
                            }
                            totalPrice += boughtItem.getBasePrice() * boughtItem.getCount();
                        }
                        Payment newPayment = new Payment(selectedBill.getIdCustomer(), boughtItems, totalPrice);
                        // Update user list
                        List<Customer> cList = router.getSystemCustomers();
                        List<Member> mList = router.getSystemMembers();
                        List<VIP> vList = router.getSystemVIPs();
                        List<Payment> storePayments = router.getSystemPayments();
                        int initialSize = storePayments.size();
    
                        Iterator<Customer> cIterator = cList.iterator();
                        while (cIterator.hasNext()) {
                            Customer c = cIterator.next();
                            if (selectedBill.getIdCustomer() == c.getId()) {
                                c.setMadeFirstPurchase(true);
                                storePayments.add(newPayment);
                                break;
                            }
                        }
    
                        if (storePayments.size() == initialSize) {
                            // Not a customer
                            Iterator<Member> mIterator = mList.iterator();
                            while (mIterator.hasNext()) {
                                Member m = mIterator.next();
                                if (selectedBill.getIdCustomer() == m.getId()) {
                                    float point = Math.round(totalPrice / 100.0f);
                                    m.getPaymentHistory().add(newPayment);
                                    float mPoint = m.getPoint();
                                    m.setPoint(mPoint + point);
                                    storePayments.add(newPayment);
                                    break;
                                }
                            }
                            if (storePayments.size() == initialSize) {
                                // Not a Member
                                Iterator<VIP> vIterator = vList.iterator();
                                while (vIterator.hasNext()) {
                                    VIP v = vIterator.next();
                                    if (selectedBill.getIdCustomer() == v.getId()) {
                                        float point = Math.round(totalPrice / 100.0f);
                                        newPayment.setTotalPrice(totalPrice - (totalPrice / 10));
                                        v.getPaymentHistory().add(newPayment);
                                        float mPoint = v.getPoint();
                                        v.setPoint(mPoint + point);
                                        storePayments.add(newPayment);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    router.notifyListeners();
                });

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
        this.userIds.clear();
        this.bills.clear();
        List<Bill> storedbills = this.router.getSystemBills();
        for (Bill p : storedbills) {
            if (p.isBillFixed() && !p.isBillDone()) {
                this.userIds.add(Integer.toString(p.getIdCustomer()));
                this.bills.add(p);
            }
        }
        ObservableList<String> newItems = FXCollections.observableArrayList(this.userIds);
        this.userIDComboBox.setItems(newItems);
        this.billTable.refresh();
    }
}
