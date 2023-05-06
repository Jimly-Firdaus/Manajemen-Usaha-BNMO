package com.gui.pages;

import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import com.gui.components.*;
import com.gui.Router;

import com.logic.output.Printer;
import com.logic.feature.Bill;
import com.logic.constant.Payment;

public class PageLaporan extends VBox {
    private Stage stage;
    private String userId;
    private Label warningLabel;
    private Timeline timeline;

    public PageLaporan(Router router, Stage stage) {
        // keep this as reference for updates
        this.stage = stage;
        // Label label = new Label("Laporan");
        // BaseButton button = new BaseButton("Go back to main");
        // button.setOnAction(event -> this.checker(router));

        BaseCard card = new BaseCard("Pilih Jenis Laporan yang Ingin Dicetak", "");

        VBox header = new VBox();
        Label headerLabel = new Label("Laporan");
        String headerLabelStyle = "-fx-text-fill: #212121;\n" +
                "-fx-padding: 10px 20px;\n" +
                "-fx-font-size: 24px\n";
        headerLabel.setStyle(headerLabelStyle);
        header.getChildren().addAll(headerLabel);
        header.setAlignment(Pos.CENTER);
        header.setPrefHeight(175);

        VBox body = new VBox();
        Label option = new Label("Please select one:");
        String opt1 = "Cetak Laporan Penjualan";
        String opt2 = "Cetak Fixed Bill Pelanggan";
        BaseButton printBtn = new BaseButton("Cetak laporan!");
        BaseToggle togglerBox = new BaseToggle(opt1, opt2, printBtn, false);
        printBtn.setOnAction(event -> this.handlePrintEvent(router, togglerBox.getSelected()));

        Label hint = new Label("Masukkan id (jika ingin mencetak fixed bill): ");
        TextField userId = new TextField();
        userId.textProperty().addListener((observable, oldValue, newValue) -> {
            this.userId = newValue;
        });
        HBox userIdBox = new HBox();

        userIdBox.setAlignment(Pos.CENTER);
        userIdBox.getChildren().addAll(hint, userId);
        userIdBox.setSpacing(10);

        // Warning label
        this.warningLabel = new Label("Error: User id must be numeric!");
        this.warningLabel.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        this.warningLabel.setVisible(false);
        // Hide after 3 seconds
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            this.warningLabel.setVisible(false);
        }));
        this.timeline.setCycleCount(1);

        card.getChildren().addAll(option, togglerBox);
        card.setAlignment(Pos.CENTER);
        body.setSpacing(10);
        body.getChildren().addAll(card, userIdBox, printBtn, warningLabel);
        body.setAlignment(Pos.CENTER);
        VBox.setMargin(printBtn, new Insets(50, 0, 0, 0));
        getChildren().addAll(header, body);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public void handlePrintEvent(Router router, String option) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select output directory");
        fileChooser.setInitialFileName("Laporan.pdf");
        File file = fileChooser.showSaveDialog(this.stage);
        if (file != null) {
            String outputPathName = file.getAbsolutePath();
            if (option.equals("Cetak Laporan Penjualan")) {
                Thread printer = new Printer<>(router.getSystemPayments(), outputPathName);
                printer.start();
            } else if (option.equals("Cetak Fixed Bill Pelanggan")) {
                List<Bill> systemBills = router.getSystemBills();
                List<Bill> chosenUserBill = new ArrayList<>();
                try {
                    int userId = Integer.parseInt(this.userId);
                    for (Bill bill : systemBills) {
                        if (bill.getIdCustomer() == userId && bill.isFixedBill()) {
                            chosenUserBill.add(bill);
                        }
                    }
                    if (chosenUserBill.size() > 0) {
                        Thread printer = new Printer<>(chosenUserBill, outputPathName);
                        printer.start();
                    }
                    System.out.println("Parsed int value: " + userId);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + this.userId + " is not a valid integer.");
                    this.warningLabel.setVisible(true);
                    this.timeline.play();
                }
            }
        }
    }

    public void checker(Router router) {
        for (Payment payment : router.getSystemPayments()) {
            System.out.println(payment.toString());
        }
        for (Bill bill : router.getSystemBills()) {
            System.out.println(bill.toString());
        }
        System.out.println(router.getInventory().toString());
    }

}
