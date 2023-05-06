package com.gui.interfaces;

import javafx.scene.Node;

public interface PageSwitcher {
    Node gotoMainPage();
    Node gotoPageLaporan();
    Node gotoRegistrationPage();
    Node gotoUserInfosPage();
    Node gotoUpdateInfoPage();
    Node gotoMembershipDeactivationPage();
    Node gotoUserPage();
    Node gotoSettingsPage();
    Node gotoInventoryPage();
    Node gotoPaymentHistoryPage();
}
