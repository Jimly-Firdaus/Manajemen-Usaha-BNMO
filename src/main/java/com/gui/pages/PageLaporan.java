package com.gui.pages;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;

import com.gui.components.*;
import com.gui.interfaces.PageSwitcher;

public class PageLaporan extends VBox {
    public PageLaporan(PageSwitcher pageCaller) {
        // Label label = new Label("Laporan");
        BaseButton button = new BaseButton("Go back to main");
        button.setOnAction(event -> pageCaller.gotoMainPage());

        BaseCard card = new BaseCard("Card title", "Card content");

        VBox header = new VBox();
        header.setPrefHeight(175);
        Label headerLabel = new Label("Laporan");
        String headerLabelStyle =   "-fx-text-fill: #212121;\n" + 
                                    "-fx-padding: 10px 20px;\n" +
                                    "-fx-font-size: 24px\n";
        headerLabel.setStyle(headerLabelStyle);
        header.getChildren().addAll(headerLabel, button);
        header.setAlignment(Pos.CENTER);

        VBox body = new VBox();
        Label option = new Label("Pilih jenis laporan yang ingin dicetak");
        String opt1 = "Cetak Laporan Penjualan";
        String opt2 = "Cetak Fixed Bill Pelanggan";
        BaseButton printBtn = new BaseButton("Cetak laporan!");
        BaseToggle togglerBox = new BaseToggle(opt1, opt2, printBtn);
        printBtn.setOnAction(event -> this.handlePrintEvent(togglerBox.getSelected()));

        card.getChildren().addAll(option, togglerBox);
        body.getChildren().addAll(card, printBtn);
        body.setAlignment(Pos.CENTER);
        
        getChildren().addAll(header, body);
        VBox.setVgrow(body, Priority.ALWAYS);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public void handlePrintEvent(String option) {
        if (option.equals("Cetak Laporan Penjualan")) {

        } 
        else if (option.equals("Cetak Fixed Bill Pelanggan")) {

        } else {
            // TODO: prompt error to user
        }
    }

}
