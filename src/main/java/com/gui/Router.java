package com.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

import com.gui.interfaces.PageSwitcher;
import com.gui.pages.*;

public class Router implements PageSwitcher {
    private Stage stage;

    public Router(Stage stage) {
        this.stage = stage;
    }

    public void gotoMainPage() {
        MainPage mainPage = new MainPage(this);
        Scene scene = new Scene(mainPage);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoPageLaporan() {
        PageLaporan pageLaporan = new PageLaporan(this);
        Scene scene = new Scene(pageLaporan);
        stage.setScene(scene);
        stage.show();
    }

}
