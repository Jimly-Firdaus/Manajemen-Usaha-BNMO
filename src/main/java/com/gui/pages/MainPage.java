package com.gui.pages;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gui.Router;

public class MainPage extends VBox {

    public MainPage(Router router) {
        // Set Font
        Font titleFont = Font.font("Georgia", FontWeight.BOLD, 18);
        Font nameFont = Font.font("Arial", 15);

        // Set title
        Label titleLabel = new Label("Nama Aplikasi");
        titleLabel.setFont(titleFont);
        HBox titleHLayout = new HBox(titleLabel);
        titleHLayout.setAlignment(Pos.CENTER);

        // Set Logo
        /*
         * Image logo = new
         * Image(getClass().getResourceAsStream("/com/gui/components/logo.png"));
         * ImageView logoView = new ImageView(logo);
         * logoView.setFitHeight(100);
         * logoView.setFitWidth(200);
         * 
         * // Create a new HBox to hold the title and logo
         * HBox logoLayout = new HBox(logoView);
         * logoLayout.setAlignment(Pos.CENTER);
         */

        // Set NIM and Nama
        Label autLabel1 = new Label("13521XXX  A");
        autLabel1.setFont(nameFont);
        Label autLabel2 = new Label("13521XXX  B");
        autLabel2.setFont(nameFont);
        Label autLabel3 = new Label("13521XXX  C");
        autLabel3.setFont(nameFont);
        Label autLabel4 = new Label("13521XXX  D");
        autLabel4.setFont(nameFont);
        Label autLabel5 = new Label("13521XXX  E");
        autLabel5.setFont(nameFont);
        VBox autLabelsBox = new VBox();
        autLabelsBox.getChildren().addAll(autLabel1, autLabel2, autLabel3, autLabel4, autLabel5);
        autLabelsBox.setAlignment(Pos.CENTER);

        // Set Jam
        // Membuat objek Text untuk tanggal dan waktu
        Text tanggal = new Text();
        tanggal.setFont(Font.font("Arial", 18));
        tanggal.setFill(Color.BLACK);
        tanggal.setText(getCurrentDate());

        Text waktu = new Text();
        waktu.setFont(Font.font("Arial Black", 36));
        waktu.setFill(Color.BLACK);
        waktu.setText(getCurrentTime());

        // Create a task for updating the time label
        Task<Void> task = new Task<Void>() {
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
        new Thread(task).start();

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
        container.getChildren().addAll(titleLabel, jamHLayout, autLabelsBox);
        container.setAlignment(Pos.CENTER);

        // Set margins
        VBox.setMargin(titleHLayout, new Insets(200, 0, 35, 0));
        // VBox.setMargin(logoPane, new Insets(95, 30, 0, 0));
        VBox.setMargin(jamHLayout, new Insets(20, 0, 0, 0));
        VBox.setMargin(autLabelsBox, new Insets(20, 0, 0, 0));

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