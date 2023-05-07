package com.gui.pages;

import com.gui.Router;
import com.gui.components.BaseButton;
import com.logic.feature.Inventory;
import com.logic.feature.Product;

import javafx.collections.ObservableList;
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
import lombok.Getter;

@Getter
public class ProductClick extends Stage {
    private float priceOnTotal;

    private Product currentProduct = new Product();

    private Router router;

    public ProductClick(String productName, ObservableList<Product> productData, ObservableList<Product> cartData){

        BaseButton cancelBtn = new BaseButton("Cancel");
        BaseButton addBillBtn = new BaseButton("Add to Bill");
        BaseButton checkProduct = new BaseButton("Checking");

        float basePrice = 0.0f;


        Product currentProduct = productData.filtered(p -> p.getProductName().equals(productName)).get(0);

        if(currentProduct != null){
            basePrice = currentProduct.getBasePrice();
            System.out.println(basePrice);
        }

        String basePriceString = Float.toString(basePrice);

        Label title = new Label(basePriceString);
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
        textNumber.setText("1");
        textNumber.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty() || newText == null) {
                textNumber.setText("1");
                return null;
            } else if (newText.matches("[1-9]\\d*")) {
                return change;
            } else {
                return null;
            }
        }));

        HBox productNumberLayout = new HBox(productNumberMethod, new Region(), textNumber);
        HBox.setHgrow(productNumberLayout.getChildren().get(1), Priority.ALWAYS);
        productNumberLayout.setStyle("-fx-padding: 10px 30px;");


        checkProduct.setOnAction(
            e->{
                System.out.println(currentProduct.getProductName());
            }
        );

        cancelBtn.setOnAction(e->close());

        VBox root = new VBox(checkProduct, headerLayout, productNumberLayout);

        addBillBtn.setOnAction(e -> {
            String numberProduct = textNumber.getText();
        int value = Integer.parseInt(numberProduct);
            boolean check = this.updateStorage(productData, value, productName);
            if(check){
                this.priceOnTotal = currentProduct.getBasePrice() * value;
                System.out.println(this.getPriceOnTotal());
                Product cartProduct = new Product(value, currentProduct.getProductName(), currentProduct.getBasePrice(), currentProduct.getBoughtPrice(), currentProduct.getCategory());
                cartData.add(cartProduct);
                close();
                // If there was an error label in the root, remove it
                root.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("The amount is not enough"));
            }else{
                Label errorLabel = new Label("The amount is not enough");
                // If there was already an error label in the root, remove it before adding the new one
                root.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("The amount is not enough"));
                root.getChildren().add(errorLabel); // add errorLabel to the root
            }
        });
    
        root.setAlignment(Pos.CENTER);
        
        // Set the scene of the popup window
        Scene scene = new Scene(root, 480, 108);
        setScene(scene);
    }

    public boolean updateStorage(ObservableList<Product> products, int value, String productName){
        for (Product p : products) {
            if (p.getProductName().equals(productName)) {
                if(p.getCount() - value < 0){
                    return false;
                }else{
                    p.setCount(p.getCount() - value);
                    break;
                }
            }
        }
        return true;
    }
}
