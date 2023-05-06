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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
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
                
    public InventoryManagement(Router router, Stage stage) {
        this.router = router;

        VBox container = new VBox();

        // For Buttons
        HBox bottomBox = new HBox();

        // For Product List
        VBox searchingContainer = new VBox();

        // Set up the text labels for product list and shopping cart
        Label productListLabel = new Label("Products");
        productListLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-family: 'Georgia';" +
            "-fx-padding: 10px;" +
            "-fx-border-width: 1;" +
            "-fx-border-color: gray;" +
            "-fx-border-style: dashed;"
        );

        HBox productLabelLayout = new HBox(productListLabel);
        productLabelLayout.setAlignment(Pos.TOP_LEFT);
        productLabelLayout.setPadding(new Insets(10, 0, 0, 20));
        productLabelLayout.setAlignment(Pos.CENTER);

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

        // Add the components
        searchingContainer.getChildren().addAll(productLabelLayout, new Region(), searchField, productListView, new Region());
        VBox.setMargin(searchField,  new Insets(20, 0, 0, 0));
        VBox.setVgrow(searchingContainer.getChildren().get(1), Priority.ALWAYS);
        VBox.setVgrow(searchingContainer.getChildren().get(4), Priority.ALWAYS);

        bottomBox.getChildren().addAll(new Region(), addInventoryButton, new Region());
        HBox.setHgrow(bottomBox.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(bottomBox.getChildren().get(2), Priority.ALWAYS);
        searchingContainer.setPadding(new Insets(20, 20, 0, 20));

        container.getChildren().addAll(searchingContainer, bottomBox);
        VBox.setMargin(bottomBox, new Insets(20, 0, 20, 0));
        container.prefHeightProperty().bind(stage.heightProperty());


        addInventoryButton.setOnAction(
            e -> {
                AddProductInventory addProductPage = new AddProductInventory();
                addProductPage.show();
            }
        );

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
