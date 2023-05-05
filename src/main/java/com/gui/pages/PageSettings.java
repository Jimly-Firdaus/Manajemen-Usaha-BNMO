package com.gui.pages;

import com.gui.interfaces.PageSwitcher;

import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

import java.io.File;

import com.gui.components.*;
import com.logic.store.ParserJSON;
import com.logic.store.ParserXML;

public class PageSettings extends VBox {
    private Stage stage;
    private String pathLabel = "";
    private Boolean databaseReloadStatus = true;
    private String databaseReloadText = "";

    // Need reference resource to fill in from Parser
    public PageSettings(PageSwitcher pageCaller, String inputPath, Stage stage) {
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
        reloadDatabase.setOnAction(event -> onReloadDatabase(reloadDatabaseStatus));
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
                new FileChooser.ExtensionFilter("OBJ Files", "*.obj")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String inputPath = selectedFile.getAbsolutePath();
            // use the inputPath here
            this.pathLabel = inputPath;
            label.setText("Data Store Path: " + this.pathLabel);
            System.out.println("User file: " + inputPath);
        }
        this.databaseReloadStatus = true;
        this.databaseReloadText = "Reload required!";
        databaseStatusText.setText(this.databaseReloadText);
    }

    public void onReloadDatabase(Label label) {


        this.databaseReloadStatus = false;
        this.databaseReloadText = "";
        label.setText(this.databaseReloadText);
    }

}
