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
        mainPage.setPrefSize(1080, 608);
        Scene scene = new Scene(mainPage);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoPageLaporan() {
        PageLaporan pageLaporan = new PageLaporan(this);
        pageLaporan.setPrefSize(1080, 608);
        Scene scene = new Scene(pageLaporan);
        stage.setScene(scene);
        stage.show();
    }

    // Add your pages below here
    public void gotoRegistrationPage() {
        RegistrationPage pageRegister = new RegistrationPage(this);
        pageRegister.setPrefSize(1080, 608);
        Scene scene = new Scene(pageRegister);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoUpdateInfoPage() {
        UpdateInfoPage pageUpdateInfo = new UpdateInfoPage(this);
        pageUpdateInfo.setPrefSize(1080, 608);
        Scene scene = new Scene(pageUpdateInfo);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoMembershipDeactivationPage() {
        MembershipDeactivationPage pageMembershipDeactivation = new MembershipDeactivationPage(this);
        pageMembershipDeactivation.setPrefSize(1080, 608);
        Scene scene = new Scene(pageMembershipDeactivation);
        stage.setScene(scene);
        stage.show();
    }
    
}
