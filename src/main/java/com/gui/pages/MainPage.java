package com.gui.pages;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gui.Router;

public class MainPage extends VBox {

    public MainPage(Router router) {
        // Set Font
        Font title1Font = Font.font("Georgia", FontWeight.BOLD, 20);
        Font title2Font = Font.font("Georgia", FontWeight.BOLD, 15);
        Font nameFont = Font.font("Courier New", 14);
        Font dateFont = Font.font("Arial", 18);
        Font clockFont = Font.font("Arial", FontWeight.BOLD, 26);

        // Set title
        Label titleLabel1 = new Label("WELCOME TO");
        titleLabel1.setFont(title2Font);
        titleLabel1.setTextAlignment(TextAlignment.LEFT);
        HBox titleHLayout1 = new HBox(titleLabel1);
        titleHLayout1.setAlignment(Pos.CENTER);

        Label titleLabel2 = new Label("Manajemen Usaha BNMO");
        titleLabel2.setFont(title1Font);
        titleLabel2.setTextAlignment(TextAlignment.LEFT);
        HBox titleHLayout2 = new HBox(titleLabel2);
        titleHLayout2.setAlignment(Pos.CENTER);

        // Set Logo
        /*
         * Image logo = null;
         * try {
         * File file = new File("../src/main/java/com/gui/components/logo.png");
         * InputStream inputStream = new FileInputStream(file);
         * logo = new Image(inputStream);
         * } catch (FileNotFoundException e) {
         * e.printStackTrace();
         * }
         * 
         * ImageView logoView = new ImageView(logo);
         * logoView.setFitHeight(100);
         * logoView.setFitWidth(200);
         * 
         * // Create a new HBox to hold the title and logo
         * HBox logoLayout = new HBox(logoView);
         * logoLayout.setAlignment(Pos.CENTER);
         */

        Image gifImage = null;
        try {
            File file = new File("../src/main/java/com/gui/components/holaLogo.gif");
            InputStream inputStream = new FileInputStream(file);
            gifImage = new Image(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Create an ImageView for the GIF image
        ImageView logoGIFView = new ImageView();
        logoGIFView.setImage(gifImage);
        logoGIFView.setFitHeight(150);
        logoGIFView.setFitWidth(150);

        // Create a StackPane layout and add the ImageView to it
        StackPane logoGIFPane = new StackPane();
        logoGIFPane.getChildren().add(logoGIFView);

        // Load the GIF image on a separate thread
        Thread loadGifThread = new Thread(() -> {
            Image preloadedGifImage = null;
            try {
                File file = new File("../src/main/java/com/gui/components/holaLogo.gif");
                InputStream inputStream = new FileInputStream(file);
                preloadedGifImage = new Image(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            logoGIFView.setImage(preloadedGifImage);
        });
        loadGifThread.setDaemon(true);
        loadGifThread.start();

        // Set NIM and Nama
        Label autLabel = new Label(" Author:");
        autLabel.setFont(nameFont);
        Label autLabel1 = new Label("   13521054  Wilson Tansil");
        autLabel1.setFont(nameFont);
        Label autLabel2 = new Label("   13521064  Bill Clinton");
        autLabel2.setFont(nameFont);
        Label autLabel3 = new Label("   13521077  Husnia Munzayana");
        autLabel3.setFont(nameFont);
        Label autLabel4 = new Label("   13521102  Jimly Firdaus");
        autLabel4.setFont(nameFont);
        Label autLabel5 = new Label("   13521125  Asyifa Nurul Syafira");
        autLabel5.setFont(nameFont);
        VBox autLabelsBox = new VBox();
        autLabelsBox.getChildren().addAll(autLabel, autLabel1, autLabel2, autLabel3, autLabel4, autLabel5);
        autLabelsBox.setAlignment(Pos.BASELINE_LEFT);

        // Set Jam
        // Membuat objek Text untuk tanggal dan waktu
        Text tanggal = new Text();
        tanggal.setFont(dateFont);
        tanggal.setFill(Color.BLACK);
        tanggal.setText(getCurrentDate());

        Text waktu = new Text();
        waktu.setFont(clockFont);
        waktu.setFill(Color.BLACK);
        waktu.setText(getCurrentTime());

        // Create a task for updating the time label
        Task<Void> updateTimeTask = new Task<Void>() {
            @Override
            protected Void call() {
                while (true) {
                    // Update the time label
                    Platform.runLater(() -> {
                        tanggal.setText(getCurrentDate());
                        waktu.setText(getCurrentTime());
                    });

                    // Wait for one second
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
                return null;
            }
        };

        // Start the task in a new thread
        new Thread(updateTimeTask).start();

        // Membuat objek GridPane untuk tanggal dan waktu
        GridPane jamLayout = new GridPane();
        jamLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        jamLayout.setAlignment(Pos.CENTER);
        jamLayout.setHgap(0);
        jamLayout.setVgap(0);
        jamLayout.setPadding(new Insets(20));
        jamLayout.setPrefWidth(400);
        jamLayout.setBorder(new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(0.5))));
        jamLayout.add(tanggal, 0, 0);
        GridPane.setHalignment(tanggal, HPos.CENTER);
        GridPane.setValignment(tanggal, VPos.CENTER);

        jamLayout.add(waktu, 0, 1);
        GridPane.setHalignment(waktu, HPos.CENTER);
        GridPane.setValignment(waktu, VPos.CENTER);

        HBox jamHLayout = new HBox(jamLayout);
        jamHLayout.setAlignment(Pos.CENTER);

        // Create a VBox as a container
        VBox container = new VBox(20);
        container.getChildren().addAll(titleHLayout1, titleHLayout2, logoGIFPane, jamHLayout, autLabelsBox);
        container.setAlignment(Pos.CENTER);

        // Set margins
        VBox.setMargin(titleHLayout1, new Insets(0, 0, 0, 0));
        // VBox.setMargin(logoPane, new Insets(95, 30, 0, 0));
        // VBox.setMargin(jamHLayout, new Insets(20, 0, 0, 0));
        // VBox.setMargin(autLabelsBox, new Insets(20, 0, 0, 0));

        // Append to VBox
        getChildren().addAll(container);
        this.setAlignment(Pos.CENTER);
    }

    // Get the current time in the format of "EEE, dd MMMMMMMMM yyyy"
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMMMMMMMM yyyy");
        String date = dateFormat.format(new Date());
        return date + "\n";
    }

    // Get the current time in the format of "HH:mm:ss"
    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String time = timeFormat.format(new Date());
        return time;
    }
}