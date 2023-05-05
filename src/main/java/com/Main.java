package com;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.gui.Router;
import com.gui.pages.*;

public class Main extends Application {
    private TabPane tabPane;
    private Stage primaryStage;
    Router router;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        router = new Router(primaryStage);

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1080, 608);

        // Center the window
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() - scene.getWidth()) / 2;
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() - scene.getHeight()) / 2;
        primaryStage.setX(centerX);
        primaryStage.setY(centerY);

        // Make menubar
        MenuBar menuBar = new MenuBar();
        root.setTop(menuBar);

        // Make Menu dropdown
        Menu halamanMenu = new Menu("Menu");

        // Set Item in Menu
        MenuItem halaman1 = new MenuItem("Dashboard");
        halaman1.setOnAction(event -> createNewTab("Dashboard"));
        MenuItem halaman2 = new MenuItem("Membership");
        halaman2.setOnAction(event -> createNewTab("Membership"));
        MenuItem halaman3 = new MenuItem("Halaman 3");
        halaman3.setOnAction(event -> createNewTab("Halaman 3"));
        MenuItem laporan = new MenuItem("Laporan");
        laporan.setOnAction(event -> createNewTab("Laporan"));
        halamanMenu.getItems().addAll(halaman1, halaman2, halaman3, laporan);
        menuBar.getMenus().add(halamanMenu);
        menuBar.setPadding(new Insets(10));

        // Make tabPane
        tabPane = new TabPane();
        root.setCenter(tabPane);

        // Default tab "Dashboard"
        createNewTab("Dashboard");

        // router.gotoMainPage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Create a new tab according to the menu options
    private void createNewTab(String menuName) {
        Tab tab = new Tab(menuName);
        tab.setClosable(true);

        if (menuName.equals("Dashboard")) {
            tab.setContent(new MainPage(router));
        } else if (menuName.equals("Membership")) {
            tab.setContent(new MembershipDeactivationPage(router));
        } else if (menuName.equals("Laporan")) {
            tab.setContent(router.gotoPageLaporan(null, null));
        } 
        else {
            tab.setContent(new Label("Lanjutkan"));
        }

        // Add tab to tabpane
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
