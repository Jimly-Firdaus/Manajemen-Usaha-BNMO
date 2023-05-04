package com.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;

import com.gui.interfaces.PageSwitcher;
import com.gui.pages.*;

import com.logic.feature.interfaces.IBill;
import com.logic.constant.interfaces.IPayment;

public class Router implements PageSwitcher {
    private Stage stage;

    public Router(Stage stage) {
        this.stage = stage;
    }

    public void gotoMainPage() {
        MainPage mainPage = new MainPage(this);
        mainPage.setPrefSize(1080, 608);
        Scene scene = new Scene(mainPage);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    public void gotoPageLaporan(List<IBill> lsBills, List<IPayment> lsPayments) {
        PageLaporan pageLaporan = new PageLaporan(this, lsBills, lsPayments, this.stage);
        pageLaporan.setPrefSize(1080, 608);
        Scene scene = new Scene(pageLaporan);
        stage.setTitle("Laporan");
        stage.setScene(scene);
        stage.show();
    }

    // Add your pages below here
    public void gotoRegistrationPage() {
        RegistrationPage pageRegister = new RegistrationPage(this);
        pageRegister.setPrefSize(1080, 608);
        Scene scene = new Scene(pageRegister);
        stage.setTitle("Register");
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
    
    public void gotoUserPage(){
        UserPage page = new UserPage(this);
        page.setPrefSize(1080, 608);
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }
}
