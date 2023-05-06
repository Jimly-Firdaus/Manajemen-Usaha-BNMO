package com.gui;

import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.Data;

import java.util.List;

import com.gui.interfaces.PageSwitcher;
import com.gui.pages.*;

import com.logic.feature.Bill;
import com.logic.feature.Product;
import com.logic.feature.Inventory;
import com.logic.feature.Member;
import com.logic.feature.VIP;
import com.logic.constant.Payment;

@Data
public class Router implements PageSwitcher {
    private Stage stage;

    // Pages
    private MainPage mainPage;
    private PageLaporan pageLaporan;
    private RegistrationPage pageRegister;
    private UpdateInfoPage pageUpdateInfo;
    private MembershipDeactivationPage pageMembershipDeactivation;
    private PageSettings pageSettings;
    private InventoryManagement pageInventory;
    private UserPage pageUser;

    // System Data
    private List<Bill> systemBills;
    private List<Payment> systemPayments;
    private List<Product> systemProducts;
    private List<Member> systemMembers;
    private List<VIP> systemVIPs;
    private Inventory inventory = new Inventory();

    // TODO : Find a way to share resources to all pages by references

    public Router(Stage stage) {
        this.stage = stage;

        // pages
        this.pageSettings = new PageSettings(this, this.systemBills, this.systemPayments, this.systemProducts, this.systemMembers, this.systemVIPs, this.inventory, this.stage);
        this.mainPage = new MainPage(this);
        this.pageLaporan = new PageLaporan(this, this.systemBills, this.systemPayments, this.stage);
        this.pageRegister = new RegistrationPage(this);
        this.pageUpdateInfo = new UpdateInfoPage(this);
        this.pageMembershipDeactivation = new MembershipDeactivationPage(this);
        this.pageUser = new UserPage(this, this.stage);
        this.pageInventory = new InventoryManagement(this, this.stage);
    }

    public Node gotoMainPage() {
        this.mainPage.setPrefSize(1080, 608);
        return this.mainPage;
    }

    // If resources is visible to all, then the args is not needed here
    public Node gotoPageLaporan() {
        this.pageLaporan.setPrefSize(1080, 608);
        return this.pageLaporan;
    }

    // Add your pages below here
    public Node gotoRegistrationPage() {
        this.pageRegister.setPrefSize(1080, 608);
        return this.pageRegister;
    }

    public Node gotoUpdateInfoPage() {
        this.pageUpdateInfo.setPrefSize(1080, 608);
        return this.pageUpdateInfo;
    }

    public Node gotoMembershipDeactivationPage() {
        this.pageMembershipDeactivation.setPrefSize(1080, 608);
        return this.pageMembershipDeactivation;
    }
    
    public Node gotoUserPage(){
        this.pageUser.setPrefSize(1080, 608);
        return this.pageUser;
    }

    public Node gotoSettingsPage(){
        this.pageSettings.setPrefSize(1080, 608);
        return this.pageSettings;
    }

    public Node gotoInventoryPage(){  
        this.pageInventory.setPrefSize(1080, 608);
        return this.pageInventory;
    }
}
