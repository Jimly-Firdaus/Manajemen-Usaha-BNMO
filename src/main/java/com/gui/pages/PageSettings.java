package com.gui.pages;

import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

import com.gui.Router;
import com.gui.components.*;
import com.logic.store.interfaces.Parseable;
import com.logic.store.ParserJSON;
import com.logic.store.ParserXML;
import com.logic.store.ParserOBJ;
import com.logic.feature.Bill;
import com.logic.feature.Product;
import com.logic.feature.Inventory;
import com.logic.feature.Member;
import com.logic.feature.VIP;
import com.logic.constant.Payment;

@Getter
public class PageSettings extends VBox {
    private Stage stage;
    private String pathLabel = "";
    private String databaseReloadText = "";
    private String outputDatabaseType = "";
    private File databaseDirectory;

    // Need reference resource to fill in from Parser (Bills, and other)
    // TODO: add more objects that are needs to be retrieved from data store
    public PageSettings(
            Router router,
            Stage stage) {
        this.stage = stage;

        // input file path dialog
        BaseCard fileDialogCard = new BaseCard("Database Config: ", "");
        HBox fileDialogBox = new HBox();
        fileDialogBox.setSpacing(10);
        Label inputPathLabel = new Label("Data Store Path: " + pathLabel);
        BaseButton openFile = new BaseButton("Open File");
        fileDialogBox.getChildren().addAll(openFile, inputPathLabel);
        fileDialogBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(fileDialogBox, new Insets(0, 0, 0, 20));
        fileDialogCard.setSpacing(10);

        // Database state dialog
        VBox databaseStateBox = new VBox();
        BaseButton reloadDatabase = new BaseButton("Refresh Database");
        Label reloadDatabaseStatus = new Label("");
        reloadDatabase.setOnAction(event -> onReloadDatabase(reloadDatabaseStatus, router));
        openFile.setOnAction(event -> this.chooseOutputPath(inputPathLabel, reloadDatabaseStatus));
        HBox databaseDialogBox = new HBox();
        databaseDialogBox.getChildren().addAll(reloadDatabase);
        BaseButton outDataStore = new BaseButton("Output Database");
        outDataStore.setOnAction(event -> this.onOutputDatabase());
        databaseStateBox.setSpacing(10);
        databaseStateBox.getChildren().addAll(databaseDialogBox, outDataStore);

        // Database output type dialog
        HBox databaseOutputBox = new HBox();
        ObservableList<String> options = FXCollections.observableArrayList("JSON", "XML", "OBJ");
        final ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.outputDatabaseType = (String) newVal;
        });
        Label comboBoxLabel = new Label("Output Data Store type: ");
        databaseOutputBox.getChildren().addAll(comboBoxLabel, comboBox);
        databaseOutputBox.setAlignment(Pos.CENTER_LEFT);

        fileDialogCard.getChildren().addAll(fileDialogBox, databaseOutputBox, databaseStateBox);
        fileDialogCard.setAlignment(Pos.CENTER_LEFT);

        // TODO : 2
        // Delete or unplug plugin

        // Other config should be done in here

        this.getChildren().addAll(fileDialogCard);
    }

    public void chooseOutputPath(Label label, Label databaseStatusText) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Input Directory");
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            this.databaseDirectory = selectedDirectory;
            String inputPath = selectedDirectory.getAbsolutePath();
            this.pathLabel = inputPath;
            label.setText("Data Store Path: " + this.pathLabel);
            System.out.println("User file: " + inputPath);
        }
        this.databaseReloadText = "Reload required!";
        databaseStatusText.setText(this.databaseReloadText);
    }

    public <T> void onReloadDatabase(
            Label label,
            Router router
        ) {
        File[] files = this.databaseDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

                // Check if file name and extension match
                if (fileName.startsWith("systemProducts")) {
                    router.restoreSystemProducts(this.processParsing(Product.class, fileExtension, file.getAbsolutePath()));

                } else if (fileName.startsWith("systemPayments")) {
                    router.restoreSystemPayments(this.processParsing(Payment.class, fileExtension, file.getAbsolutePath()));
                } else if (fileName.startsWith("systemBills")) {
                    router.restoreSystemBills(this.processParsing(Bill.class, fileExtension, file.getAbsolutePath()));
                } else if (fileName.startsWith("systemMembers")) {
                    router.restoreSystemMembers(this.processParsing(Member.class, fileExtension, file.getAbsolutePath()));
                } else if (fileName.startsWith("systemVIPs")) {
                    router.restoreSystemVIPs(this.processParsing(VIP.class, fileExtension, file.getAbsolutePath()));
                } 
                else if (fileName.startsWith("inventory")) {
                    Inventory parsedInventory = null;
                    switch (fileExtension) {
                        case "json":
                            Parseable jsonParser = new ParserJSON(file.getAbsolutePath());
                            parsedInventory = (Inventory) jsonParser.readData(Inventory.class);
                            break;
                        case "xml":
                            Parseable xmlParser = new ParserXML(file.getAbsolutePath());
                            parsedInventory = (Inventory) xmlParser.readData(Inventory.class);
                            break;
                        case "obj":
                            Parseable objParser = new ParserOBJ(file.getAbsolutePath());
                            parsedInventory = (Inventory) objParser.readData(Inventory.class);
                            break;

                    }
                    router.getInventory().getStorage().clear();
                    router.getInventory().getStorage().addAll(parsedInventory.getStorage());
                }
            }
        }

        System.out.println("------------------Payments------------------");
        for (Payment payment : router.getSystemPayments()) {
            System.out.println(payment.toString());
        }
        System.out.println("------------------Bills------------------");
        for (Bill bill : router.getSystemBills()) {
            System.out.println(bill.toString());
        }
        System.out.println("------------------Members------------------");
        for (Member bill : router.getSystemMembers()) {
            System.out.println(bill.toString());
        }
        System.out.println("------------------Products------------------");
        for (Product bill : router.getSystemProducts()) {
            System.out.println(bill.toString());
        }
        System.out.println("------------------VIPs------------------");
        for (VIP bill : router.getSystemVIPs()) {
            System.out.println(bill.toString());
        }
        System.out.println("------------------Inventory------------------");
        System.out.println(router.getInventory());

        this.databaseReloadText = "";
        label.setText(this.databaseReloadText);
    }

    public void onOutputDatabase() {

        switch (this.outputDatabaseType) {
            // TODO: change this to correlated reference list objects
            case "JSON":
                Parseable jsonParser = new ParserJSON(this.pathLabel);
                jsonParser.writeData(null);
                break;
            case "XML":
                Parseable xmlParser = new ParserXML(this.pathLabel);
                xmlParser.writeData(null);
                break;
            case "OBJ":
                Parseable objParser = new ParserOBJ(this.pathLabel);
                objParser.writeData(null);
                break;
        }
        System.out.println(this.outputDatabaseType);
    }

    public <T> List<T> processParsing(Class<T> classType, String extension, String absPath) {
        List<T> data = null;
        switch (extension) {
            case "json":
                Parseable jsonParser = new ParserJSON(absPath);
                data = jsonParser.readDatas(classType);
                break;
            case "xml":
                Parseable xmlParser = new ParserXML(absPath);
                data = xmlParser.readDatas(classType);
                break;
            case "obj":
                Parseable objParser = new ParserOBJ(absPath);
                data = objParser.readDatas(classType);
                break;
            default:
                break;
        }
        return data;
    }

}
