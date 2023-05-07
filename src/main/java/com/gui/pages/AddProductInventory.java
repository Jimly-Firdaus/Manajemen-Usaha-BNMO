package com.gui.pages;

import com.gui.components.BaseButton;
import com.logic.feature.Product;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class AddProductInventory extends Stage{

    private Product product = new Product();

    private boolean cancelBtn;

    private boolean validProduct;

    public AddProductInventory(){

        this.validProduct = false;
        this.cancelBtn = false;
        BaseButton cancelBtn = new BaseButton("Cancel");
        BaseButton saveBtn = new BaseButton("Save");
        Label title = new Label("{Harga}");
        String titleStyle =  "-fx-background-color: transparent;\n" + 
                        "-fx-text-fill: black;\n" +
                        "-fx-padding: 10px 20px;\n" +
                        "-fx-border-radius: 5px;\n" +
                        "-fx-background-radius: 5px;";
        title.setStyle(titleStyle);

        HBox headerLayout = new HBox(cancelBtn, new Region(), title, new Region(), saveBtn);
        String headerLayoutStyle = "-fx-background-color: #679abe; -fx-padding: 10px 30px; \n";
        headerLayout.setStyle(headerLayoutStyle);
        headerLayout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        HBox.setHgrow(headerLayout.getChildren().get(1), Priority.ALWAYS);
        HBox.setHgrow(headerLayout.getChildren().get(3), Priority.ALWAYS);

        Label productNumberMethod = new Label("Product Number : ");

        TextField textNumber = new TextField();
        textNumber.setPromptText("Rp 0");
        textNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                textNumber.setText(oldValue);
            }
        });

        HBox productNumberLayout = new HBox(productNumberMethod, new Region(), textNumber);
        HBox.setHgrow(productNumberLayout.getChildren().get(1), Priority.ALWAYS);
        productNumberLayout.setStyle("-fx-padding: 10px 30px;");

        Label productName = new Label("Product Name : ");

        TextField textName = new TextField();
        textName.setPromptText("Name");

        HBox productNameLayout = new HBox(productName, new Region(), textName);
        HBox.setHgrow(productNameLayout.getChildren().get(1), Priority.ALWAYS);
        productNameLayout.setStyle("-fx-padding: 10px 30px;");

        Label productBasePrice = new Label("Product Base Price : ");

        TextField basePrice = new TextField();
        basePrice.setPromptText("Rp 0");
        basePrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                basePrice.setText(oldValue);
            }
        });

        HBox productBasePriceLayout = new HBox(productBasePrice, new Region(), basePrice);
        HBox.setHgrow(productBasePriceLayout.getChildren().get(1), Priority.ALWAYS);
        productBasePriceLayout.setStyle("-fx-padding: 10px 30px;");

        Label productBoughtPrice = new Label("Product Bought Price : ");

        TextField boughtPrice = new TextField();
        boughtPrice.setPromptText("Rp 0");
        boughtPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                boughtPrice.setText(oldValue);
            }
        });

        HBox productBoughtPriceLayout = new HBox(productBoughtPrice, new Region(), boughtPrice);
        HBox.setHgrow(productBoughtPriceLayout.getChildren().get(1), Priority.ALWAYS);
        productBoughtPriceLayout.setStyle("-fx-padding: 10px 30px;");

        Label productCategory = new Label("Product Category : ");

        TextField category = new TextField();
        category.setPromptText("Category");

        HBox productCategoryLayout = new HBox(productCategory, new Region(), category);
        HBox.setHgrow(productCategoryLayout.getChildren().get(1), Priority.ALWAYS);
        productCategoryLayout.setStyle("-fx-padding: 10px 30px;");

        VBox root = new VBox(headerLayout, productNumberLayout, productNameLayout, productBasePriceLayout, productBoughtPriceLayout, productCategoryLayout);

        cancelBtn.setOnAction(e->{
            this.cancelBtn = true;
            close();
        });

        saveBtn.setOnAction(
            e->{
                String numberText = textNumber.getText();
                System.out.println(numberText);
                int numberResult = 0;
                if(numberText != null){
                    numberResult = Integer.parseInt(numberText);
                }
                String productNameResult = textName.getText();
                String basePriceText = basePrice.getText();
                int basePriceResult = 0;
                if(basePriceText != null){
                    basePriceResult = Integer.parseInt(basePriceText);
                }
                String boughtPriceText = boughtPrice.getText();
                int boughtPriceResult = 0;
                if(boughtPriceText != null){
                    boughtPriceResult = Integer.parseInt(boughtPriceText);
                }
                String productCategoryResult = category.getText();
                if(numberText != null && (productNameResult != null) && basePriceText != null && boughtPriceText != null && productCategoryResult != null){
                    Product tempProduct = new Product(numberResult, productNameResult, basePriceResult, boughtPriceResult, productCategoryResult);
                    this.product = tempProduct;
                    this.validProduct = true;
                    close();
                }else{
                    Label errorLabel = new Label("Invalid product");
                    root.getChildren().add(errorLabel);
                }

            }
        );

        root.setAlignment(Pos.CENTER);
        
        // Set the scene of the popup window
        Scene scene = new Scene(root, 508, 308);
        setScene(scene);
    }
}
