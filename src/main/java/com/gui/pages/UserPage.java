package com.gui.pages;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.gui.Router;
import com.gui.components.BaseButton;
import com.gui.interfaces.PageSwitcher;
import com.gui.interfaces.RouterListener;
import com.logic.feature.Bill;
import com.logic.feature.Customer;
import com.logic.feature.Inventory;
import com.logic.feature.ListOfProduct;
import com.logic.feature.Member;
import com.logic.feature.Product;
import com.logic.feature.VIP;

public class UserPage extends VBox implements RouterListener{

    private ObservableList<Product> productData = FXCollections.observableArrayList();

    private ObservableList<Product> cartData = FXCollections.observableArrayList();

    private ObservableList<Bill> billDArray = FXCollections.observableArrayList();

    private ObservableList<VIP> VIPbuyer = FXCollections.observableArrayList();

    private ObservableList<Member> memberBuyer = FXCollections.observableArrayList();

    private ObservableList<Customer> customerBuyer = FXCollections.observableArrayList();

    private String currentCustomerStatus;

    private int currentCustomerID;

    // private ObservableList<String> productStringData = FXCollections.observableArrayList();

    // private FilteredList<Product> productFilteredData = new FilteredList<>(productData, s -> true);
    private ObservableList<Product> localStorage = FXCollections.observableArrayList();

    private FloatProperty totalPrice = new SimpleFloatProperty();

    private Router router;

    public UserPage(Router router, Stage stage) {

        this.totalPrice.set(0.0f);

        this.currentCustomerStatus = "None";

        this.currentCustomerID = 0;

        this.router = router;

        // search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search");      
        
        // product filtered list
        FilteredList<Product> productFilteredData = new FilteredList<>(productData, s -> s.getCount() > 0);
        
        // product name string observable list 
        ObservableList<String> productStringData = FXCollections.observableArrayList();

        // copy product data name to name observable list
        productFilteredData.forEach(p -> productStringData.add(p.getProductName()));
        
        // name filtered list 
        FilteredList<String>  productFilteredStringData = new FilteredList<>(productStringData, s->true);

        // search bar implementation
        searchField.textProperty().addListener(obs -> {
            String filter = searchField.getText();
            if (filter == null || filter.length() == 0) {
                productFilteredStringData.setPredicate(s -> true);
            } else {
                productFilteredStringData.setPredicate(s -> s.contains(filter));
            }
        });

        // item list view
        ListView<String> listView = new ListView<>(productFilteredStringData);
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                ProductClick clicked = new ProductClick(selectedItem, this.localStorage, cartData);
                clicked.showAndWait();
                this.totalPrice.set(this.totalPrice.get() + clicked.getPriceOnTotal());
            }
        });

        // product data updated
        productData.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> c) {
                // update the productFilteredData list
                productFilteredData.setPredicate(p -> p.getCount() > 0);
                // update the productStringData list
                productStringData.clear();
                productFilteredData.forEach(p -> productStringData.add(p.getProductName()));
                // refresh the productFilteredStringData list
                productFilteredStringData.setPredicate(s -> true);
                // refresh the listView
                listView.refresh();
            }
        });

        listView.prefHeightProperty().bind(stage.heightProperty());

        // left container
        VBox leftContainer = new VBox(searchField, listView);
        leftContainer.prefWidthProperty().bind(stage.widthProperty().divide(2));
        leftContainer.prefHeightProperty().bind(stage.heightProperty());

        // add customer id
        Button addCustomerBtn = new Button("+Add Customer");
        String style = "-fx-background-color: transparent;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-padding: 10px 20px;\n" +
                "-fx-border-radius: 5px;\n" +
                "-fx-background-radius: 5px;";
        addCustomerBtn.setStyle(style);

        addCustomerBtn.setOnAction(
            e -> {
                addCustomer addCustomerPage = new addCustomer(VIPbuyer, memberBuyer, customerBuyer);
                addCustomerPage.showAndWait();
                this.currentCustomerStatus = addCustomerPage.getCurrentCustomerStatus();
                this.currentCustomerID = addCustomerPage.getCurrentCustomerID();
            }
        );

        // save bill button history
        BaseButton saveBillBtn = new BaseButton("Bill");
        saveBillBtn.setOnAction(
            event -> {
                ObservableList<Bill> temp = FXCollections.observableArrayList(this.billDArray);
                BillList billListPage = new BillList(temp);
                billListPage.showAndWait();
                List<Product> productList = new ArrayList<>();
                if(!billListPage.isCancelBtn()){
                    productList = billListPage.getChooseBill().getBasket().getProductList();
                    if (billListPage.isNew()) {
                        this.currentCustomerStatus = "None";
                        this.currentCustomerID = 0;
                    } else {
                        this.currentCustomerID = billListPage.getChooseBill().getIdCustomer();
                        this.setStatus(this.currentCustomerID);
                    }
                    this.cartData.clear();
                    this.cartData.addAll(productList);
                    this.totalPrice.set(0.0f);
                    for(Product p : this.cartData){
                        this.totalPrice.set(this.totalPrice.get() + (p.getBasePrice() * p.getCount()));
                    }
                    if (!billListPage.isNew()) {
                        this.billDArray.removeIf(bill -> bill.getIdCustomer() == this.currentCustomerID);
                        List<Bill> routerBills = router.getSystemBills();
                        routerBills.removeIf(bill -> bill.getIdCustomer() == this.currentCustomerID);
                        router.notifyListeners();
                    }
                }
            }
        );

        // right container header
        HBox rightContainerHeader = new HBox(saveBillBtn, new Region(), addCustomerBtn, new Region());
        String rightContainerStyle = "-fx-background-color: #679abe;\n";
        rightContainerHeader.setStyle(rightContainerStyle);
        rightContainerHeader.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        HBox.setHgrow(rightContainerHeader.getChildren().get(1), Priority.ALWAYS);
        HBox.setHgrow(rightContainerHeader.getChildren().get(3), Priority.ALWAYS);

        // save bill button
        Button saveBillActionBtn = new Button("Save Bill");
        String saveBillBtnStyle = "-fx-background-color: transparent;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-padding: 10px 20px;\n" +
                "-fx-border-radius: 5px;\n" +
                "-fx-background-radius: 5px;";
        saveBillActionBtn.setStyle(saveBillBtnStyle);

        // save bill button layout 
        HBox saveBillLayout = new HBox(new Region(), saveBillActionBtn, new Region());
        saveBillLayout.setStyle(rightContainerStyle);
        saveBillLayout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 1, 0, 1))));
        HBox.setHgrow(saveBillLayout.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(saveBillLayout.getChildren().get(2), Priority.ALWAYS);

        saveBillActionBtn.setOnAction(
            e->{
                if(!this.productData.isEmpty() && !(this.currentCustomerStatus.equals("None"))){
                    ListOfProduct cartList = new ListOfProduct(this.cartData);
                    Bill currentBill = new Bill(cartList, this.currentCustomerID, false, false);
                    this.billDArray.add(currentBill);
                    List<Bill> billList = this.router.getSystemBills();
                    billList.add(currentBill);
                    this.router.notifyListeners();
                    saveBillLayout.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("Invalid Bill"));
                }else{
                    Label errorLabel = new Label("Invalid Bill");
                    saveBillLayout.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("Invalid Bill"));
                    saveBillLayout.getChildren().add(errorLabel); // add 
                }
            }
        );

        // charge action button 
        Button chargeActionBtn = new Button("Charge " + totalPrice.get());
        totalPrice.addListener((obs, oldValue, newValue) -> {
            chargeActionBtn.setText("Charge " + newValue.toString());
        });

        String chargeBtnStyle = "-fx-background-color: transparent;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-padding: 10px 20px;\n" +
                "-fx-border-radius: 5px;\n" +
                "-fx-background-radius: 5px;";
        chargeActionBtn.setStyle(chargeBtnStyle);

        // charge layout 
        HBox chargeLayout = new HBox(new Region(), chargeActionBtn, new Region());
        chargeLayout.setStyle(rightContainerStyle);
        chargeLayout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        HBox.setHgrow(chargeLayout.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(chargeLayout.getChildren().get(2), Priority.ALWAYS);

        chargeActionBtn.setOnAction(e -> {
            if(!this.productData.isEmpty() && !(this.currentCustomerStatus.equals("None"))){
                billDArray.stream()
                .filter(bill -> bill.getIdCustomer() == this.currentCustomerID && !bill.isBillFixed())
                .findFirst()
                .ifPresent(bill -> bill.setBillFixed(true));
                List<Bill> routerBills = router.getSystemBills();
                routerBills.stream()
                    .filter(bill -> bill.getIdCustomer() == this.currentCustomerID)
                    .findFirst()
                    .ifPresent(bill -> bill.setBillFixed(true));
                this.router.notifyListeners();
                chargeLayout.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("Invalid Bill"));
            } else {
                Label errorLabel = new Label("Invalid Bill");
                chargeLayout.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("Invalid Bill"));
                    chargeLayout.getChildren().add(errorLabel); // add 
            }
        });

        // cart filtered list
        FilteredList<Product> cartFilteredData = new FilteredList<>(this.cartData, s -> true);

        // cart name observable list
        ObservableList<String> cartStringData = FXCollections.observableArrayList();

        // cart filtered string data 

        // copy cart data product to string filtered data
        cartFilteredData.forEach(p -> cartStringData.add(p.getProductName()));

        // filtered list data
        FilteredList<String> billData = new FilteredList<>(cartStringData, s -> true);

        ListView<String> billListView = new ListView<>(billData);

        this.cartData.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> c) {
                // update the productFilteredData list
                cartFilteredData.setPredicate(p -> true);
                // update the productStringData list
                cartStringData.clear();
                cartFilteredData.forEach(p -> cartStringData.add(p.getProductName()));
                // refresh the productFilteredStringData list
                billData.setPredicate(s -> true);
                // refresh the listView
                billListView.refresh();
            }
        });

        VBox.setVgrow(billListView, Priority.ALWAYS);

        // delete context menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> {
            String item = billListView.getSelectionModel().getSelectedItem();
            Product deletedProduct = cartData.filtered(product -> product.getProductName().equals(item)).get(0);
            this.totalPrice.set(this.totalPrice.get() - (deletedProduct.getBasePrice() * deletedProduct.getCount()));
            cartData.removeIf(product -> product.getProductName().equals(item));
            updateStorageDeleted(cartData, deletedProduct.getCount(), deletedProduct);
        });
        contextMenu.getItems().add(deleteMenuItem);

        billListView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell ;
        });
        

        // right container
        VBox rightContainer = new VBox();
        rightContainer.getChildren().addAll(rightContainerHeader, billListView, saveBillLayout, chargeLayout);
        rightContainer.prefWidthProperty().bind(stage.widthProperty().divide(2));
        rightContainer.prefHeightProperty().bind(stage.heightProperty());

        HBox content = new HBox();
        content.getChildren().addAll(leftContainer, rightContainer);
        content.prefHeightProperty().bind(stage.heightProperty());

        getChildren().addAll(content);
    }

    @Override
    public void onResourceUpdate() {
        this.productData.clear();
        this.copyData(this.router.getSystemProducts(), this.productData);
        this.localStorage = this.productData;
        this.billDArray.clear();
        this.copyData(this.router.getSystemBills(), this.billDArray);
        this.VIPbuyer.clear();
        this.copyData(this.router.getSystemVIPs(), this.VIPbuyer);
        this.memberBuyer.clear();
        this.copyData(this.router.getSystemMembers(), this.memberBuyer);
        this.customerBuyer.clear();
        this.copyData(this.router.getSystemCustomers(), this.customerBuyer);
    }

    public <T> void copyData(List<T> routerData, ObservableList<T> list){
        for (T data : routerData){
            list.add(data);
        }
    }

    public boolean updateStorageDeleted(ObservableList<Product> products, int value, Product product){
        for (Product p : products) {
            if (p.getProductName().equals(product.getProductName())) {
                if(p.getCount() - value < 0){
                    return false;
                }else{
                    p.setCount(p.getCount() + value);
                    break;
                }
            }else{
                products.add(product);
            }
        }
        return true;
    }

    public void setStatus(int id){
        if(!(VIPbuyer.filtered(b -> b.getId() == id).isEmpty())){
            this.currentCustomerStatus = "VIP";
        }else if(!(memberBuyer.filtered(b -> b.getId() == id).isEmpty())){
            this.currentCustomerStatus = "Member";
        }else if(!(customerBuyer.filtered(b -> b.getId() == id).isEmpty())){
            this.currentCustomerStatus = "Customer";
        }else{
            this.currentCustomerStatus = "None";
        }
    }

}
