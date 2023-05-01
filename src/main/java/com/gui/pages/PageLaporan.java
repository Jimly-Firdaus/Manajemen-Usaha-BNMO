package com.gui.pages;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import com.gui.components.*;

import com.gui.interfaces.PageSwitcher;

public class PageLaporan extends VBox {
    public PageLaporan(PageSwitcher pageCaller) {
        Label label = new Label("Laporan");
        BaseButton button = new BaseButton("Go back to main");
        button.setOnAction(event -> pageCaller.gotoMainPage());

        getChildren().addAll(label, button);
    }

}
