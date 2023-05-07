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

import com.gui.Router;
import com.gui.components.BaseButton;
import com.gui.interfaces.PageSwitcher;
import com.gui.interfaces.RouterListener;
import com.logic.feature.Bill;
import com.logic.feature.Inventory;
import com.logic.feature.Product;

public class UserPage extends VBox implements RouterListener{

    private ObservableList<Product> productData = FXCollections.observableArrayList();

    private ObservableList<Product> cartData = FXCollections.observableArrayList();

    // private ObservableList<String> productStringData = FXCollections.observableArrayList();

    // private FilteredList<Product> productFilteredData = new FilteredList<>(productData, s -> true);
    private ObservableList<Product> localStorage = FXCollections.observableArrayList();

    private FloatProperty totalPrice = new SimpleFloatProperty();

    private Router router;

    public UserPage(Router router, Stage stage) {

        this.totalPrice.set(0.0f);

        this.router = router;

        // search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search");

        // productData.addListener(new ListChangeListener<Product>() {
        //     @Override
        //     public void onChanged(Change<? extends Product> c){

        //     }
        // });

        // this.productData.setAll(router.getSystemProducts());        

        BaseButton checkButton = new BaseButton("Im checking");
        checkButton.setOnAction(e->{
            System.out.println(router.getInventory());
            System.out.println(this.productData);
            System.out.println(cartData);
            System.out.println(this.totalPrice.get());
        });

        // product filtered list
        FilteredList<Product> productFilteredData = new FilteredList<>(productData, s -> true);
        
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
                System.out.println(cartData);
                System.out.println(clicked.getPriceOnTotal());
                this.totalPrice.set(this.totalPrice.get() + clicked.getPriceOnTotal());
            }
        });

        // product data updated
        productData.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> c) {
                // update the productFilteredData list
                productFilteredData.setPredicate(p -> true);
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

        // save bill button history
        BaseButton saveBillBtn = new BaseButton("Bill");
        saveBillBtn.setOnAction(
            event -> {
                BillList billListPage = new BillList(router.getSystemBills());
                billListPage.show();
                List<Product> productList = new ArrayList<>();
                productList = billListPage.getChooseBill().getBasket().getProductList();
                ObservableList<Product> tempCartData = FXCollections.observableArrayList();
                tempCartData.addAll(productList);
                this.cartData = tempCartData;
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

        // charge action button 
        Button chargeActionBtn = new Button("Charge " + totalPrice.get());
        totalPrice.addListener((obs, oldValue, newValue) -> {
            chargeActionBtn.setText("Charge " + newValue.toString());
        });

        chargeActionBtn.setOnAction(e -> {
            Checkout checkoutPage = new Checkout();
            checkoutPage.show();
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

        // cart filtered list
        FilteredList<Product> cartFilteredData = new FilteredList<>(this.cartData, s -> true);

        // cart name observable list
        ObservableList<String> cartStringData = FXCollections.observableArrayList();

        // cart filtered string data 
        // FilteredList<String>  cartFilteredStringData = new FilteredList<>(cartStringData, s->true);

        // copy cart data product to string filtered data
        cartFilteredData.forEach(p -> cartStringData.add(p.getProductName()));

        // filtered list data
        FilteredList<String> billData = new FilteredList<>(cartStringData, s -> true);

        ListView<String> billListView = new ListView<>(billData);

        cartData.addListener(new ListChangeListener<Product>() {
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
                listView.refresh();
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
        // VBox.setVgrow(rightContainer.getChildren().get(1), Priority.ALWAYS);
        rightContainer.prefWidthProperty().bind(stage.widthProperty().divide(2));
        rightContainer.prefHeightProperty().bind(stage.heightProperty());

        HBox content = new HBox();
        content.getChildren().addAll(checkButton, leftContainer, rightContainer);
        content.prefHeightProperty().bind(stage.heightProperty());

        getChildren().addAll(content);
        // setPadding(new Insets(10));
        // setSpacing(10);
        // prefHeightProperty().bind(stage.heightProperty());
        // this.setAlignment(Pos.CENTER);
    }

    @Override
    public void onResourceUpdate() {
        this.productData.clear();
        List<Product> storedProducts = this.router.getSystemProducts();
        for (Product p : storedProducts) {
            this.productData.add(p);
        }
        this.localStorage = this.productData;
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


}
