package com.gui;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.Data;

import java.util.List;

import com.gui.interfaces.PageSwitcher;
import com.gui.pages.*;

import com.logic.feature.Bill;
import com.logic.constant.interfaces.IPayment;
import com.logic.feature.interfaces.IBill;

@Data
public class Router implements PageSwitcher {
    private Stage stage;
    private String inputPath = "";
    private List<Bill> systemBills;
    // private List<Payment> systemPayments;


    // TODO : Find a way to share resources to all pages by references

    public Router(Stage stage) {
        this.stage = stage;

    }

    public Node gotoMainPage() {
        MainPage mainPage = new MainPage(this);
        mainPage.setPrefSize(1080, 608);
        return mainPage;
    }

    // If resources is visible to all, then the args is not needed here
    public Node gotoPageLaporan(List<IBill> lsBills, List<IPayment> lsPayments) {
        PageLaporan pageLaporan = new PageLaporan(this, lsBills, lsPayments, this.stage);
        pageLaporan.setPrefSize(1080, 608);
        return pageLaporan;
    }

    // Add your pages below here
    public Node gotoRegistrationPage() {
        RegistrationPage pageRegister = new RegistrationPage(this);
        pageRegister.setPrefSize(1080, 608);
        return pageRegister;
    }

    public Node gotoUpdateInfoPage() {
        UpdateInfoPage pageUpdateInfo = new UpdateInfoPage(this);
        pageUpdateInfo.setPrefSize(1080, 608);
        return pageUpdateInfo;
    }

    public Node gotoMembershipDeactivationPage() {
        MembershipDeactivationPage pageMembershipDeactivation = new MembershipDeactivationPage(this);
        pageMembershipDeactivation.setPrefSize(1080, 608);
        return pageMembershipDeactivation;
    }
    
    public Node gotoUserPage(){
        UserPage page = new UserPage(this, this.stage);
        page.setPrefSize(1080, 608);
        return page;
    }

    public Node gotoSettingsPage(){
        PageSettings pageSettings = new PageSettings(this, this.inputPath, this.systemBills, this.stage);
        pageSettings.setPrefSize(1080, 608);
        return pageSettings;
    }
}
