package com.gui.pages;

import java.lang.reflect.AnnotatedWildcardType;

import com.gui.components.BaseButton;
import com.gui.interfaces.PageSwitcher;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class UserPage extends VBox {

    public UserPage(PageSwitcher pageCaller, Stage stage) {
        TextField searchField = new TextField();
        searchField.setPromptText("Search");

        ObservableList<String> data = FXCollections.observableArrayList("One", "Two", "Three", "Four", "Five");
        FilteredList<String> filteredData = new FilteredList<>(data, s -> true);

        searchField.textProperty().addListener(obs -> {
            String filter = searchField.getText();
            if (filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(s -> s.contains(filter));
            }
        });

        ListView<String> listView = new ListView<>(filteredData);
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
                BillList billListPage = new BillList();
                billListPage.show();
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

        Button chargeActionBtn = new Button("Charge {Price}");
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

        FilteredList<String> billData = new FilteredList<>(data, s -> true);
        ListView<String> billListView = new ListView<>(billData);

        VBox.setVgrow(billListView, Priority.ALWAYS);
        
        VBox rightContainer = new VBox();
        rightContainer.getChildren().addAll(rightContainerHeader, billListView, saveBillLayout, chargeLayout);
        // VBox.setVgrow(rightContainer.getChildren().get(1), Priority.ALWAYS);
        rightContainer.prefWidthProperty().bind(stage.widthProperty().divide(2));
        rightContainer.prefHeightProperty().bind(stage.heightProperty());

        HBox content = new HBox();
        content.getChildren().addAll(leftContainer, rightContainer);
        content.prefHeightProperty().bind(stage.heightProperty());

        getChildren().addAll(content);
        // setPadding(new Insets(10));
        // setSpacing(10);
        // prefHeightProperty().bind(stage.heightProperty());
        // this.setAlignment(Pos.CENTER);
    }
}
