package com.gui.pages;

import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import java.io.File;
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
        outDataStore.setOnAction(event -> this.onOutputDatabase(router));
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
            if (this.databaseDirectory != null) {
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
                    }
                    router.notifyListeners();
                }
            }

        this.databaseReloadText = "";
        label.setText(this.databaseReloadText);
    }

    public void onOutputDatabase(Router router) {
        Parseable parserOut = null;
        String ext = "";
        switch (this.outputDatabaseType) {
            case "JSON":
                parserOut = new ParserJSON(this.pathLabel);
                ext = ".json";
                break;
            case "XML":
                parserOut = new ParserXML(this.pathLabel);
                ext = ".xml";
                break;
            case "OBJ":
                parserOut = new ParserOBJ(this.pathLabel);
                ext = ".obj";
                break;
        }
        
        if (ext != "" && parserOut != null) {
            parserOut.setFilename(this.pathLabel + "/systemBills_out" + ext);
            parserOut.writeDatas(router.getSystemBills());
            parserOut.setFilename(this.pathLabel + "/systemMembers_out" + ext);
            parserOut.writeDatas(router.getSystemMembers());
            parserOut.setFilename(this.pathLabel + "/systemPayments_out" + ext);
            parserOut.writeDatas(router.getSystemPayments());
            parserOut.setFilename(this.pathLabel + "/systemProducts_out" + ext);
            parserOut.writeDatas(router.getSystemProducts());
            parserOut.setFilename(this.pathLabel + "/systemVIPs_out" + ext);
            parserOut.writeDatas(router.getSystemVIPs());
            // parserOut.setFilename(this.pathLabel + "/inventory_out" + ext);
            // parserOut.writeData(router.getInventory());
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
