package com.gui;

import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
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
@Setter
@Getter
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
    private List<Bill> systemBills = new ArrayList<>();
    private List<Payment> systemPayments = new ArrayList<>();
    private List<Product> systemProducts = new ArrayList<>();
    private List<Member> systemMembers = new ArrayList<>();
    private List<VIP> systemVIPs = new ArrayList<>();
    private Inventory inventory = new Inventory();

    // TODO : Find a way to share resources to all pages by references
    public void restoreSystemBills(Collection<Bill> collection) {
        this.systemBills.clear();
        this.systemBills.addAll(collection);
    }

    public void restoreSystemPayments(Collection<Payment> collection) {
        this.systemPayments.clear();
        this.systemPayments.addAll(collection);
    }

    public void restoreSystemProducts(Collection<Product> collection) {
        this.systemProducts.clear();
        this.systemProducts.addAll(collection);
    }

    public void restoreSystemMembers(Collection<Member> collection) {
        this.systemMembers.clear();
        this.systemMembers.addAll(collection);
    }

    public void restoreSystemVIPs(Collection<VIP> collection) {
        this.systemVIPs.clear();
        this.systemVIPs.addAll(collection);
    }

    public Router(Stage stage) {
        this.stage = stage;

        // pages
        this.pageSettings = new PageSettings(this, this.stage);
        this.mainPage = new MainPage(this);
        this.pageLaporan = new PageLaporan(this, this.stage);
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
