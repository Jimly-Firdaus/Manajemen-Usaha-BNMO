package com.gui.pages;

import java.util.List;

import com.gui.components.BaseButton;
import com.gui.interfaces.PageSwitcher;
import com.logic.feature.Bill;
import com.logic.feature.ListOfProduct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
import lombok.Getter;

@Getter
public class BillList extends Stage {

    private ObservableList<Bill> notFixedBillData = FXCollections.observableArrayList();

    private Bill chooseBill = new Bill();

    private boolean cancelBtn;

    private boolean newBill;
    
    public BillList(ObservableList<Bill> systemBills){
        // initModality(Modality.APPLICATION_MODAL);
        // // Set the stage style to UTILITY, which removes the default window decorations
        // initStyle(StageStyle.UTILITY);
        
        // Create the layout for the pop-up window
        // VBox layout = new VBox();
        // Scene scene = new Scene(layout);
        
        // Set the scene for the stage
        // Label label = new Label("Hello, World!");
        // label.setAlignment(Pos.CENTER);

        this.cancelBtn = false;
        this.newBill = false;
        this.notFixedBillData = systemBills.filtered(b -> !(b.isBillFixed()));

        ObservableList<String> stringData = FXCollections.observableArrayList();

        this.notFixedBillData.forEach(p -> stringData.add(Integer.toString(p.getIdCustomer())));

        FilteredList<String> filteredStringData = new FilteredList<>(stringData, s->true);
        
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
        

        FilteredList<String> billList = new FilteredList<>(filteredStringData, s -> true);
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

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> {
            String item = billListView.getSelectionModel().getSelectedItem();

            this.notFixedBillData.filtered(bill -> Integer.toString(bill.getIdCustomer()).equals(item));
            systemBills.removeIf(bill -> Integer.toString(bill.getIdCustomer()).equals(item));
            // Bill deletedBill = data.filtered(product -> product.getProductName().equals(item)).get(0);
            // this.totalPrice -= deletedProduct.getBasePrice() * deletedProduct.getCount();
            // cartData.removeIf(product -> product.getProductName().equals(item));
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

        // ListView<String> listView = new ListView<>(filteredStringData);
        billListView.setOnMouseClicked(event -> {
            System.out.println("hello");
            if (event.getClickCount() == 2) {
                String selectedItem = billListView.getSelectionModel().getSelectedItem();
                this.chooseBill = this.notFixedBillData.filtered(bill -> Integer.toString(bill.getIdCustomer()).equals(selectedItem)).get(0);
                close();
            }
        });

        VBox root = new VBox(headerLayout, searchField, billListView);
        root.setAlignment(Pos.CENTER);
        
        // Set the scene of the popup window
        Scene scene = new Scene(root, 880, 408);
        setScene(scene);

        cancelBtn.setOnAction(e->{
            this.cancelBtn = true;
            close();
        });

        newBillBtn.setOnAction(
            e -> {
                // ListOfProduct basket = new ListOfProduct();
                // this.chooseBill = new Bill(basket, -1, false, false);\
                this.chooseBill = new Bill();
                this.newBill = true;
                close();
            }
        );
        // Set the size of the pop-up window
        // setWidth(400);
        // setHeight(300);
    }

}
