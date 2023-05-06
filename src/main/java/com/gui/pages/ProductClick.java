package com.gui.pages;

import com.gui.components.BaseButton;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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

public class ProductClick extends Stage {
    public ProductClick(String productName){
        BaseButton cancelBtn = new BaseButton("Cancel");
        BaseButton addBillBtn = new BaseButton("Add to Bill");
        Label title = new Label(productName);
        String titleStyle =  "-fx-background-color: transparent;\n" + 
                        "-fx-text-fill: black;\n" +
                        "-fx-padding: 10px 20px;\n" +
                        "-fx-border-radius: 5px;\n" +
                        "-fx-background-radius: 5px;";
        title.setStyle(titleStyle);

        HBox headerLayout = new HBox(cancelBtn, new Region(), title, new Region(), addBillBtn);
        String headerLayoutStyle = "-fx-background-color: #679abe; -fx-padding: 10px 30px; \n";
        headerLayout.setStyle(headerLayoutStyle);
        headerLayout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        HBox.setHgrow(headerLayout.getChildren().get(1), Priority.ALWAYS);
        HBox.setHgrow(headerLayout.getChildren().get(3), Priority.ALWAYS);

        Label productNumberMethod = new Label("Product Number : ");

        TextField textNumber = new TextField();
        textNumber.setText("0");
        textNumber.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d*")) ? change : null));

        HBox productNumberLayout = new HBox(productNumberMethod, new Region(), textNumber);
        HBox.setHgrow(productNumberLayout.getChildren().get(1), Priority.ALWAYS);
        productNumberLayout.setStyle("-fx-padding: 10px 30px;");

        VBox root = new VBox(headerLayout, productNumberLayout);
        root.setAlignment(Pos.CENTER);
        
        // Set the scene of the popup window
        Scene scene = new Scene(root, 480, 108);
        setScene(scene);

        cancelBtn.setOnAction(e->close());
    }
}
