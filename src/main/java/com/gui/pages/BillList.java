package com.gui.pages;

import com.gui.components.BaseButton;
import com.gui.interfaces.PageSwitcher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BillList extends Stage {
    
    public BillList(){
        // initModality(Modality.APPLICATION_MODAL);
        // // Set the stage style to UTILITY, which removes the default window decorations
        // initStyle(StageStyle.UTILITY);
        
        // Create the layout for the pop-up window
        // VBox layout = new VBox();
        // Scene scene = new Scene(layout);
        
        // Set the scene for the stage
        // Label label = new Label("Hello, World!");
        // label.setAlignment(Pos.CENTER);
        ObservableList<String> data = FXCollections.observableArrayList("One", "Two", "Three", "Four", "Five");
        
        BaseButton cancelBtn = new BaseButton("Cancel");
        BaseButton newBillBtn = new BaseButton("New Bill");
        Label title = new Label("Billing List");
        String titleStyle =  "-fx-background-color: transparent;\n" + 
                        "-fx-text-fill: black;\n" +
                        "-fx-padding: 10px 20px;\n" +
                        "-fx-border-radius: 5px;\n" +
                        "-fx-background-radius: 5px;";
        title.setStyle(titleStyle);

        HBox headerLayout = new HBox(cancelBtn, new Region(), title, new Region(), newBillBtn);
        String headerLayoutStyle = "-fx-background-color: #679abe; -fx-padding: 10px 30px; \n";
        headerLayout.setStyle(headerLayoutStyle);
        headerLayout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        HBox.setHgrow(headerLayout.getChildren().get(1), Priority.ALWAYS);
        HBox.setHgrow(headerLayout.getChildren().get(3), Priority.ALWAYS);

        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        

        FilteredList<String> billList = new FilteredList<>(data, s -> true);
        ListView<String> billListView = new ListView<>(billList);

        billListView.prefHeightProperty().bind(heightProperty());

        searchField.textProperty().addListener(obs -> {
            String filter = searchField.getText();
            if (filter == null || filter.length() == 0) {
                billList.setPredicate(s -> true);
            } else {
                billList.setPredicate(s -> s.contains(filter));
            }
        });

        VBox root = new VBox(headerLayout, searchField, billListView);
        root.setAlignment(Pos.CENTER);
        
        // Set the scene of the popup window
        Scene scene = new Scene(root, 880, 408);
        setScene(scene);

        cancelBtn.setOnAction(e->close());
        // Set the size of the pop-up window
        // setWidth(400);
        // setHeight(300);
    }

}
