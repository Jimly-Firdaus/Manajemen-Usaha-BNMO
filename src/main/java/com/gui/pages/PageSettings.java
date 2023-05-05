package com.gui.pages;

import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import java.io.File;
import java.util.List;

import com.gui.interfaces.PageSwitcher;
import com.gui.components.*;
import com.logic.store.interfaces.Parseable;
import com.logic.store.ParserJSON;
import com.logic.store.ParserXML;
import com.logic.feature.Bill;

public class PageSettings extends VBox {
    private Stage stage;
    private String pathLabel = "";
    private String databaseReloadText = "";

    // Need reference resource to fill in from Parser (Bills, and other)
    // TODO: add more objects that are needs to be retrieved from data store
    public PageSettings(PageSwitcher pageCaller, String inputPath, List<Bill> storedBills, Stage stage) {
        this.stage = stage;

        // input file path dialog
        BaseCard fileDialogCard = new BaseCard("Input File Option", "");
        HBox fileDialogBox = new HBox();
        fileDialogBox.setSpacing(10);
        Label inputPathLabel = new Label("Data Store Path: " + pathLabel);
        BaseButton openFile = new BaseButton("Open File");
        fileDialogBox.getChildren().addAll(openFile, inputPathLabel);
        fileDialogBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(fileDialogBox, new Insets(0, 0, 0, 20));
        fileDialogCard.getChildren().addAll(fileDialogBox);
        fileDialogCard.setAlignment(Pos.CENTER_LEFT);

        BaseButton reloadDatabase = new BaseButton("Refresh Database");
        Label reloadDatabaseStatus = new Label("");
        reloadDatabase.setOnAction(event -> onReloadDatabase(reloadDatabaseStatus, storedBills, Bill.class));
        openFile.setOnAction(event -> this.chooseOutputPath(inputPath, inputPathLabel, reloadDatabaseStatus));
        // TODO : 2
        // Delete or unplug plugin

        // TODO : 3
        // Other config should be done in here

        this.getChildren().addAll(fileDialogCard);
    }

    public void chooseOutputPath(String outputPath, Label label, Label databaseStatusText) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Input File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("OBJ Files", "*.obj"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String inputPath = selectedFile.getAbsolutePath();
            // use the inputPath here
            this.pathLabel = inputPath;
            label.setText("Data Store Path: " + this.pathLabel);
            System.out.println("User file: " + inputPath);
        }
        this.databaseReloadText = "Reload required!";
        databaseStatusText.setText(this.databaseReloadText);
    }

    public <T> void onReloadDatabase(Label label, List<T> storedData, Class<T> classType) {
        String extension = this.pathLabel.substring(this.pathLabel.lastIndexOf(".") + 1);

        List<T> newData = null;

        switch (extension) {
            case "json":
                Parseable jsonParser = new ParserJSON(this.pathLabel);
                newData = jsonParser.readData(classType);
                break;
            case "xml":
                Parseable xmlParser = new ParserXML(this.pathLabel);
                newData = xmlParser.readData(classType);
                break;
        }

        if (newData != null) {
            storedData.clear();
            storedData.addAll(newData);
        }

        this.databaseReloadText = "";
        label.setText(this.databaseReloadText);
    }

}
