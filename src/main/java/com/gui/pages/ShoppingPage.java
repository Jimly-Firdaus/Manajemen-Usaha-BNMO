package com.gui.pages;
import com.gui.interfaces.PageSwitcher;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import com.gui.components.BaseButton;

public class ShoppingPage extends VBox {
    // Product List
    private ObservableList<String> products =
            FXCollections.observableArrayList(
                    "Product 1",
                    "Product 2",
                    "Product 3");

    // Cart
    private ObservableList<String> cart = FXCollections.observableArrayList();

    public ShoppingPage(PageSwitcher pageCaller) {
        BorderPane container = new BorderPane();

        // For Buttons
        HBox bottomBox = new HBox(55);
        bottomBox.setPadding(new Insets(10, 19, 0, 0));
        bottomBox.setAlignment(Pos.CENTER_RIGHT);

        // For Product List
        VBox leftBox = new VBox(10);
        leftBox.setPadding(new Insets(15));
        leftBox.setAlignment(Pos.CENTER_LEFT);

        // For Cart
        VBox rightBox = new VBox(10);
        rightBox.setPadding(new Insets(15));
        rightBox.setAlignment(Pos.CENTER_LEFT);

        // Set up the text labels for product list and shopping cart
        Text productListLabel = new Text("Products");
        productListLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        Text shoppingCartLabel = new Text("Shopping Cart");
        shoppingCartLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 16));

        // Set up the list view for the products
        ListView<String> productList = new ListView<>(products);
        productList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productList.setPrefWidth(775);

        // Set up the list view for the cart
        ListView<String> cartList = new ListView<>(cart);

        // Add to Cart Button and Checkout Button
        BaseButton addToCartButton = new BaseButton("Add to cart");
        addToCartButton.setOnAction(event -> {
            String selectedProduct = productList.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                cart.add(selectedProduct);
            }
        });

        Button removeButton = new Button("Remove");
        removeButton.setStyle(
                "-fx-background-color: red;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;"
        );
        removeButton.setOnAction(event -> {
            String selectedItem = cartList.getSelectionModel().getSelectedItem();
            if(selectedItem != null) {
                cart.remove(selectedItem);
            }
        });

        Button checkoutButton = new Button("Checkout");
        checkoutButton.setStyle(
                "-fx-background-color: green;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;"
        );
        checkoutButton.setOnAction(event -> {
            // TODO: Logic
        });

        // Add the components
        leftBox.getChildren().addAll(productListLabel, productList);
        HBox.setMargin(removeButton, new Insets(0, 0, 0, 335));
        bottomBox.getChildren().addAll(addToCartButton, removeButton, checkoutButton);
        rightBox.getChildren().addAll(shoppingCartLabel, cartList);

        container.setLeft(leftBox);
        container.setBottom(bottomBox);
        container.setRight(rightBox);

        // Append to VBox    
        getChildren().addAll(container);
        this.setAlignment(Pos.CENTER);
    }
}
