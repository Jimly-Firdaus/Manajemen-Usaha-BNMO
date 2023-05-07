package com.gui;

import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gui.interfaces.*;
import com.gui.pages.*;

import com.logic.feature.Bill;
import com.logic.feature.Customer;
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

    // Notification
    private List<RouterListener> listeners = new ArrayList<>();
    
    // Pages
    private MainPage mainPage;
    private PageLaporan pageLaporan;
    private RegistrationPage pageRegister;
    private UserInfosPage pageUserInfos;
    private UpdateInfoPage pageUpdateInfo;
    private MembershipDeactivationPage pageMembershipDeactivation;
    private PageSettings pageSettings;
    private InventoryManagement pageInventory;
    private UserPage pageUser;
    private PaymentPage pagePayment;
    private PaymentHistoryPage pagePaymentHistory;

    // System Data
    private List<Bill> systemBills = new ArrayList<>();
    private List<Payment> systemPayments = new ArrayList<>();
    private List<Product> systemProducts = new ArrayList<>();
    private List<Member> systemMembers = new ArrayList<>();
    private List<VIP> systemVIPs = new ArrayList<>();
    private List<Customer> systemCustomers = new ArrayList<>();
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

    public void notifyListeners() {
        for (RouterListener listener : listeners) {
            // if (listener != null) {
                listener.onResourceUpdate();
            // }
        }
    }

    public Router(Stage stage) {
        this.stage = stage;

        // pages
        this.mainPage = new MainPage(this);
    }

    public Node gotoMainPage() {
        this.mainPage.setPrefSize(1080, 608);
        return this.mainPage;
    }

    // If resources is visible to all, then the args is not needed here
    public Node gotoPageLaporan() {
        this.pageLaporan = new PageLaporan(this, this.stage);
        this.pageLaporan.setPrefSize(1080, 608);
        return this.pageLaporan;
    }

    // Add your pages below here
    public Node gotoRegistrationPage() {
        this.pageRegister = new RegistrationPage(this, this.stage);
        this.pageRegister.setPrefSize(1080, 608);
        return this.pageRegister;
    }

    public Node gotoUserInfosPage() {
        this.pageUserInfos = new UserInfosPage(this, this.stage);
        this.listeners.add(pageUserInfos);
        this.pageUserInfos.setPrefSize(1080, 608);
        return this.pageUserInfos;
    }

    public Node gotoMembershipDeactivationPage() {
        this.pageMembershipDeactivation = new MembershipDeactivationPage(this, this.stage);
        this.listeners.add(pageMembershipDeactivation);
        this.pageMembershipDeactivation.setPrefSize(1080, 608);
        return this.pageMembershipDeactivation;
    }
    
    public Node gotoUserPage(){
        this.pageUser = new UserPage(this, this.stage);
        this.pageUser.setPrefSize(1080, 608);
        return this.pageUser;
    }

    public Node gotoSettingsPage(){
        this.pageSettings = new PageSettings(this, this.stage);
        this.pageSettings.setPrefSize(1080, 608);
        return this.pageSettings;
    }

    public Node gotoInventoryPage(){ 
        this.pageInventory = new InventoryManagement(this, this.stage); 
        this.listeners.add(pageInventory);
        this.pageInventory.setPrefSize(1080, 608);
        return this.pageInventory;
    }

    public Node gotoPaymentPage(){  
        this.pagePayment = new PaymentPage(this, this.stage);
        this.listeners.add(pagePayment);
        this.pagePayment.setPrefSize(1080, 608);
        return this.pagePayment;
    }

    public Node gotoPaymentHistoryPage(){  
        this.pagePaymentHistory = new PaymentHistoryPage(this, this.stage);
        this.listeners.add(pagePaymentHistory);
        this.pagePaymentHistory.setPrefSize(1080, 608);
        return this.pagePaymentHistory;
    }
}
