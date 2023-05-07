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
public class ProductDesc extends Stage{

    public ProductDesc(Product product){

        BaseButton cancelBtn = new BaseButton("Done");
        Label title = new Label("Description");
        String titleStyle =  "-fx-background-color: transparent;\n" + 
                        "-fx-text-fill: black;\n" +
                        "-fx-padding: 10px 20px;\n" +
                        "-fx-border-radius: 5px;\n" +
                        "-fx-background-radius: 5px;";
        title.setStyle(titleStyle);

        HBox headerLayout = new HBox(cancelBtn, new Region(), title, new Region());
        String headerLayoutStyle = "-fx-background-color: #679abe; -fx-padding: 10px 30px; \n";
        headerLayout.setStyle(headerLayoutStyle);
        headerLayout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        // HBox.setHgrow(headerLayout.getChildren().get(1), Priority.ALWAYS);
        HBox.setHgrow(headerLayout.getChildren().get(3), Priority.ALWAYS);

        Label productNumberMethod = new Label("Product Number : " + product.getCount());

        HBox productNumberLayout = new HBox(productNumberMethod);
        // HBox.setHgrow(productNumberLayout.getChildren().get(1), Priority.ALWAYS);
        productNumberLayout.setStyle("-fx-padding: 10px 30px;");

        Label productName = new Label("Product Name : " + product.getProductName());

        HBox productNameLayout = new HBox(productName);
        // HBox.setHgrow(productNameLayout.getChildren().get(1), Priority.ALWAYS);
        productNameLayout.setStyle("-fx-padding: 10px 30px;");

        Label productBasePrice = new Label("Product Base Price : " + product.getBasePrice());

        HBox productBasePriceLayout = new HBox(productBasePrice);
        // HBox.setHgrow(productBasePriceLayout.getChildren().get(1), Priority.ALWAYS);
        productBasePriceLayout.setStyle("-fx-padding: 10px 30px;");

        Label productBoughtPrice = new Label("Product Bought Price : " + product.getBoughtPrice());

        HBox productBoughtPriceLayout = new HBox(productBoughtPrice);
        // HBox.setHgrow(productBoughtPriceLayout.getChildren().get(1), Priority.ALWAYS);
        productBoughtPriceLayout.setStyle("-fx-padding: 10px 30px;");

        Label productCategory = new Label("Product Category : " + product.getCategory());

        HBox productCategoryLayout = new HBox(productCategory);
        // HBox.setHgrow(productCategoryLayout.getChildren().get(1), Priority.ALWAYS);
        productCategoryLayout.setStyle("-fx-padding: 10px 30px;");

        VBox root = new VBox(headerLayout, productNumberLayout, productNameLayout, productBasePriceLayout, productBoughtPriceLayout, productCategoryLayout);

        cancelBtn.setOnAction(e->{
            close();
        });

        root.setAlignment(Pos.CENTER);
        
        // Set the scene of the popup window
        Scene scene = new Scene(root, 508, 308);
        setScene(scene);
    }
}
