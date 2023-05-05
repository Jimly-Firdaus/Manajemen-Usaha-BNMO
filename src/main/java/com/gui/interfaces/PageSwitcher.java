package com.gui.interfaces;

import java.util.List;
import javafx.scene.Node;

import com.logic.feature.interfaces.IBill;
import com.logic.constant.interfaces.IPayment;

public interface PageSwitcher {
    Node gotoMainPage();
    Node gotoPageLaporan(List<IBill> lsBills, List<IPayment> lsPayments);
    Node gotoRegistrationPage();
    Node gotoUpdateInfoPage();
    Node gotoMembershipDeactivationPage();
}
