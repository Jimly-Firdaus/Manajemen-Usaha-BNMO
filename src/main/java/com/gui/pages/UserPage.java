package com.gui.pages;

import javafx.scene.Scene;
import javafx.scene.control.*;
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
import com.logic.feature.Bill;
import com.logic.feature.Inventory;
import com.logic.feature.Product;

public class UserPage extends VBox {

    private ObservableList<Product> productData = FXCollections.observableArrayList();

    private ObservableList<Product> cartData = FXCollections.observableArrayList();

    private ObservableList<String> productStringData = FXCollections.observableArrayList();

    private FilteredList<Product> productFilteredData = new FilteredList<>(productData, s -> true);


    private Float totalPrice = 0.0f;

    public UserPage(Router router, Stage stage) {
        TextField searchField = new TextField();
        searchField.setPromptText("Search");

        ObservableList<Product> observableProductData = FXCollections.observableList(router.getInventory().getStorage());
        observableProductData.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> change) {
                productData.setAll(observableProductData);
                productStringData.clear();
                productFilteredData.forEach(p -> productStringData.add(p.getProductName()));
            }
        });
        productData.setAll(observableProductData);

        BaseButton checkButton = new BaseButton("Im checking");
        checkButton.setOnAction(e->{
            System.out.println(router.getInventory());
            System.out.println(this.productData);
        });

        FilteredList<Product> productFilteredData = new FilteredList<>(productData, s -> true);

        ObservableList<String> productStringData = FXCollections.observableArrayList();
        productFilteredData.forEach(p -> productStringData.add(p.getProductName()));

        FilteredList<String>  productFilteredStringData = new FilteredList<>(productStringData, s->true);

        searchField.textProperty().addListener(obs -> {
            String filter = searchField.getText();
            if (filter == null || filter.length() == 0) {
                productFilteredStringData.setPredicate(s -> true);
            } else {
                productFilteredStringData.setPredicate(s -> s.contains(filter));
            }
        });

        ListView<String> listView = new ListView<>(productFilteredStringData);
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                ProductClick clicked = new ProductClick(selectedItem, router.getInventory(), cartData);
                this.totalPrice += clicked.getPriceOnTotal();
                clicked.show();
            }
        });
        // listView.setStyle("-fx-control-inner-background: #94b8d1; -fx-text-fill: black;");
        listView.prefHeightProperty().bind(stage.heightProperty());

        VBox leftContainer = new VBox(searchField, listView);
        leftContainer.prefWidthProperty().bind(stage.widthProperty().divide(2));
        leftContainer.prefHeightProperty().bind(stage.heightProperty());

        Button addCustomerBtn = new Button("+Add Customer");
        String style = "-fx-background-color: transparent;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-padding: 10px 20px;\n" +
                "-fx-border-radius: 5px;\n" +
                "-fx-background-radius: 5px;";
        addCustomerBtn.setStyle(style);

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

        HBox rightContainerHeader = new HBox(saveBillBtn, new Region(), addCustomerBtn, new Region());
        String rightContainerStyle = "-fx-background-color: #679abe;\n";
        rightContainerHeader.setStyle(rightContainerStyle);
        rightContainerHeader.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        HBox.setHgrow(rightContainerHeader.getChildren().get(1), Priority.ALWAYS);
        HBox.setHgrow(rightContainerHeader.getChildren().get(3), Priority.ALWAYS);

        Button saveBillActionBtn = new Button("Save Bill");
        String saveBillBtnStyle = "-fx-background-color: transparent;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-padding: 10px 20px;\n" +
                "-fx-border-radius: 5px;\n" +
                "-fx-background-radius: 5px;";
        saveBillActionBtn.setStyle(saveBillBtnStyle);


        HBox saveBillLayout = new HBox(new Region(), saveBillActionBtn, new Region());
        saveBillLayout.setStyle(rightContainerStyle);
        saveBillLayout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 1, 0, 1))));
        HBox.setHgrow(saveBillLayout.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(saveBillLayout.getChildren().get(2), Priority.ALWAYS);

        Button chargeActionBtn = new Button("Charge " + this.totalPrice.toString());
        chargeActionBtn.setOnAction(
            e->{
                Checkout checkoutPage = new Checkout();
                checkoutPage.show();
            }
        );

        String chargeBtnStyle = "-fx-background-color: transparent;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-padding: 10px 20px;\n" +
                "-fx-border-radius: 5px;\n" +
                "-fx-background-radius: 5px;";
        chargeActionBtn.setStyle(chargeBtnStyle);

        HBox chargeLayout = new HBox(new Region(), chargeActionBtn, new Region());
        chargeLayout.setStyle(rightContainerStyle);
        chargeLayout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        HBox.setHgrow(chargeLayout.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(chargeLayout.getChildren().get(2), Priority.ALWAYS);

        FilteredList<Product> cartFilteredData = new FilteredList<>(this.cartData, s -> true);

        ObservableList<String> cartStringData = FXCollections.observableArrayList();

        FilteredList<String>  cartFilteredStringData = new FilteredList<>(cartStringData, s->true);

        cartFilteredData.forEach(p -> cartFilteredStringData.add(p.getProductName()));

        FilteredList<String> billData = new FilteredList<>(cartFilteredStringData, s -> true);
        ListView<String> billListView = new ListView<>(billData);

        VBox.setVgrow(billListView, Priority.ALWAYS);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> {
            String item = billListView.getSelectionModel().getSelectedItem();
            Product deletedProduct = cartData.filtered(product -> product.getProductName().equals(item)).get(0);
            this.totalPrice -= deletedProduct.getBasePrice() * deletedProduct.getCount();
            cartData.removeIf(product -> product.getProductName().equals(item));
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
}
