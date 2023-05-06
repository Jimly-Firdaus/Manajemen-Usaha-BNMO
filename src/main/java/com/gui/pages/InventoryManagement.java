package com.gui.pages;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.util.List;
import java.util.ArrayList;

import com.gui.Router;
import com.gui.components.BaseButton;
import com.gui.interfaces.*;
import com.logic.feature.Product;

public class InventoryManagement extends VBox implements RouterListener {
    // Product List
    private ObservableList<String> products =
            FXCollections.observableArrayList(
                    "Product 1",
                    "Product 2",
                    "Product 3");

    Router router;

    private FilteredList<String> productList;
    private ListView<String> productListView;
                
    // Cart
    // private ObservableList<String> cart = FXCollections.observableArrayList();

    public InventoryManagement(Router router, Stage stage) {
        this.router = router;

        VBox container = new VBox();

        // For Buttons
        HBox bottomBox = new HBox();

        // For Product List
        VBox searchingContainer = new VBox();
        // leftBox.setPadding(new Insets(15));
        // leftBox.setAlignment(Pos.CENTER);

        // For Cart
        // VBox rightBox = new VBox(10);
        // rightBox.setPadding(new Insets(15));
        // rightBox.setAlignment(Pos.CENTER_LEFT);

        // Set up the text labels for product list and shopping cart
        Text productListLabel = new Text("Products");
        productListLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        Text shoppingCartLabel = new Text("Shopping Cart");
        shoppingCartLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 16));

        // Set up the list view for the products
        this.productList = new FilteredList<>(products, s -> true);
        this.productListView = new ListView<>(productList);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> {
            String item = productListView.getSelectionModel().getSelectedItem();
            products.remove(item);
        });
        contextMenu.getItems().add(deleteMenuItem);

        productListView.setCellFactory(lv -> {
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
        // productList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // productList.setPrefWidth(775);

        // Set up the list view for the cart
        // ListView<String> cartList = new ListView<>(cart);

        // Add to Cart Button and Checkout Button
        BaseButton addInventoryButton = new BaseButton("Add to inventory");

        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        searchField.textProperty().addListener(obs -> {
            String filter = searchField.getText();
            if (filter == null || filter.length() == 0) {
                productList.setPredicate(s -> true);
            } else {
                productList.setPredicate(s -> s.contains(filter));
            }
        });
        // addInventoryButton.setOnAction(event -> {
        //     String selectedProduct = productList.getSelectionModel().getSelectedItem();
        //     if (selectedProduct != null) {
        //         cart.add(selectedProduct);
        //     }
        // });

        // Button removeButton = new Button("Remove");
        // removeButton.setStyle(
        //         "-fx-background-color: red;" +
        //         "-fx-text-fill: #ffffff;" +
        //         "-fx-font-size: 12px;" +
        //         "-fx-padding: 10px 20px;" +
        //         "-fx-border-radius: 5px;" +
        //         "-fx-background-radius: 5px;"
        // );
        // removeButton.setOnAction(event -> {
        //     String selectedItem = cartList.getSelectionModel().getSelectedItem();
        //     if(selectedItem != null) {
        //         cart.remove(selectedItem);
        //     }
        // });

        // Button checkoutButton = new Button("Checkout");
        // checkoutButton.setStyle(
        //         "-fx-background-color: green;" +
        //                 "-fx-text-fill: #ffffff;" +
        //                 "-fx-font-size: 12px;" +
        //                 "-fx-padding: 10px 20px;" +
        //                 "-fx-border-radius: 5px;" +
        //                 "-fx-background-radius: 5px;"
        // );
        // checkoutButton.setOnAction(event -> {
        //     // TODO: Logic
        // });

        // Add the components
        searchingContainer.getChildren().addAll(productListLabel, new Region(), searchField, productListView, new Region());
        VBox.setVgrow(searchingContainer.getChildren().get(1), Priority.ALWAYS);
        VBox.setVgrow(searchingContainer.getChildren().get(4), Priority.ALWAYS);

        // HBox upperContainer = new HBox(new Region(), searchingContainer, new Region());
        // HBox.setHgrow(upperContainer.getChildren().get(0), Priority.ALWAYS);
        // HBox.setHgrow(upperContainer.getChildren().get(2), Priority.ALWAYS);
        // HBox.setMargin(removeButton, new Insets(0, 0, 0, 335));
        bottomBox.getChildren().addAll(new Region(), addInventoryButton, new Region());
        HBox.setHgrow(bottomBox.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(bottomBox.getChildren().get(2), Priority.ALWAYS);
        // rightBox.getChildren().addAll(shoppingCartLabel, cartList);

        container.getChildren().addAll(searchingContainer, bottomBox);
        container.prefHeightProperty().bind(stage.heightProperty());

        addInventoryButton.setOnAction(
            e -> {
                AddProductInventory addProductPage = new AddProductInventory();
                addProductPage.show();
            }
        );
        // VBox.setVgrow(container.getChildren().get(1), Priority.ALWAYS);
        // container.setRight(rightBox);

        // Append to VBox    
        getChildren().addAll(container);
        this.setAlignment(Pos.CENTER);
    }

    public void refreshProducts() {
        List<Product> storedProducts = this.router.getSystemProducts();
        
        // Get the latest products data from the router
        List<String> latestProducts = new ArrayList<>();
        for (Product p : storedProducts) {
            latestProducts.add(p.getProductName());
        }

        // Update the products list
        products.clear();
        products.addAll(latestProducts);

        // Refresh the filtered list to show the latest data
        productList.setPredicate(s -> true);

        // Update the list view to show the latest data
        productListView.refresh();
    }

    @Override
    public void onResourceUpdate() {
        this.refreshProducts();
    }
}
