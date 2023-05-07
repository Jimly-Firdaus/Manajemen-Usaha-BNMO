package com.gui.pages;

import com.gui.components.BaseButton;
import com.logic.feature.Customer;
import com.logic.feature.Member;
import com.logic.feature.VIP;

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
public class addCustomer extends Stage {

    private String currentCustomerStatus;

    private int currentCustomerID;

    private int resultField;

    public addCustomer(ObservableList<VIP> VIPbuyer, ObservableList<Member> memberBuyer, ObservableList<Customer> customerBuyer){

        this.currentCustomerStatus = "None";
        BaseButton cancelBtn = new BaseButton("Cancel");
        BaseButton doneBtn = new BaseButton("Done");
        Label title = new Label("Customer");
        String titleStyle =  "-fx-background-color: transparent;\n" + 
                        "-fx-text-fill: black;\n" +
                        "-fx-padding: 10px 20px;\n" +
                        "-fx-border-radius: 5px;\n" +
                        "-fx-background-radius: 5px;";
        title.setStyle(titleStyle);

        HBox headerLayout = new HBox(cancelBtn, new Region(), title, new Region(), doneBtn);
        String headerLayoutStyle = "-fx-background-color: #679abe; -fx-padding: 10px 30px; \n";
        headerLayout.setStyle(headerLayoutStyle);
        headerLayout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        headerLayout.prefHeightProperty().bind(heightProperty());
        HBox.setHgrow(headerLayout.getChildren().get(1), Priority.ALWAYS);
        HBox.setHgrow(headerLayout.getChildren().get(3), Priority.ALWAYS);

        Label cashMethod = new Label("IdCustomer : ");

        TextField textNumber = new TextField();
        textNumber.setPromptText("customer ID");
        textNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textNumber.setText(oldValue);
            }
        });

        HBox cashLayout = new HBox(cashMethod, new Region(), textNumber);
        cashLayout.prefHeightProperty().bind(heightProperty());
        HBox.setHgrow(cashLayout.getChildren().get(1), Priority.ALWAYS);
        cashLayout.setStyle("-fx-padding: 10px 30px;");

        VBox root = new VBox(headerLayout, cashLayout);

        root.setAlignment(Pos.CENTER);
        
        // Set the scene of the popup window
        Scene scene = new Scene(root, 508, 158);
        setScene(scene);

        cancelBtn.setOnAction(e->close());

        doneBtn.setOnAction(
            e->{
                String numberProduct = textNumber.getText();
                if (!numberProduct.isEmpty()) {
                    resultField = Integer.parseInt(numberProduct);
                }
                this.currentCustomerID = resultField;
                System.out.println(resultField);
                if(!(VIPbuyer.filtered(b -> b.getId() == resultField).isEmpty())){
                    this.currentCustomerStatus = "VIP";
                    close();
                }else if(!(memberBuyer.filtered(b -> b.getId() == resultField).isEmpty())){
                    this.currentCustomerStatus = "Member";
                    close();
                }else if(!(customerBuyer.filtered(b -> b.getId() == resultField).isEmpty())){
                    this.currentCustomerStatus = "Customer";
                    close();
                }else{
                    Label errorLabel = new Label("The ID is invalid");
                    root.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("The ID is invalid"));
                    root.getChildren().add(errorLabel); // add 
                }
            }
        );
    }
}
