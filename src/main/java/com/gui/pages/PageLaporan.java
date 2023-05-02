package com.gui.pages;

import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.util.List;
import java.io.File;

import com.gui.components.*;
import com.gui.interfaces.PageSwitcher;

import com.logic.output.Printer;
import com.logic.feature.interfaces.IBill;
import com.logic.constant.interfaces.IPayment;

public class PageLaporan extends VBox {
    List<IBill> listBills;
    List<IPayment> listPayments;
    Stage stage;

    public PageLaporan(PageSwitcher pageCaller, List<IBill> lsBills, List<IPayment> lsPayments, Stage stage) {
        // keep this as reference for updates
        this.listBills = lsBills;
        this.listPayments = lsPayments;
        this.stage = stage;

        // Label label = new Label("Laporan");
        BaseButton button = new BaseButton("Go back to main");
        button.setOnAction(event -> pageCaller.gotoMainPage());

        BaseCard card = new BaseCard("Pilih Jenis Laporan yang Ingin Dicetak", "");

        VBox header = new VBox();
        Label headerLabel = new Label("Laporan");
        String headerLabelStyle =   "-fx-text-fill: #212121;\n" + 
                                    "-fx-padding: 10px 20px;\n" +
                                    "-fx-font-size: 24px\n";
        headerLabel.setStyle(headerLabelStyle);
        header.getChildren().addAll(headerLabel, button);
        header.setAlignment(Pos.CENTER);
        header.setPrefHeight(175);

        VBox body = new VBox();
        Label option = new Label("Please select one:");
        String opt1 = "Cetak Laporan Penjualan";
        String opt2 = "Cetak Fixed Bill Pelanggan";
        BaseButton printBtn = new BaseButton("Cetak laporan!");
        BaseToggle togglerBox = new BaseToggle(opt1, opt2, printBtn, false);
        printBtn.setOnAction(event -> this.handlePrintEvent(togglerBox.getSelected()));

        card.getChildren().addAll(option, togglerBox);
        card.setAlignment(Pos.CENTER);
        body.getChildren().addAll(card, printBtn);
        body.setAlignment(Pos.CENTER);
        VBox.setMargin(printBtn, new Insets(50, 0, 0, 0));
        getChildren().addAll(header, body);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public void handlePrintEvent(String option) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select output directory");
        File file = fileChooser.showSaveDialog(this.stage);
        if (file != null) {
            String outputPathName = file.getAbsolutePath();
            if (option.equals("Cetak Laporan Penjualan")) {
                Thread printer = new Printer<>(this.listPayments, outputPathName);
                printer.start();
            } 
            else if (option.equals("Cetak Fixed Bill Pelanggan")) {
                Thread printer = new Printer<>(this.listBills, outputPathName);
                printer.start();
            }
        }
    }

}
