package com.gui.interfaces;

import java.util.List;

import com.logic.feature.interfaces.IBill;
import com.logic.constant.interfaces.IPayment;

public interface PageSwitcher {
    void gotoMainPage();
    void gotoPageLaporan(List<IBill> lsBills, List<IPayment> lsPayments);
    void gotoRegistrationPage();
    void gotoUpdateInfoPage();
    void gotoMembershipDeactivationPage();
}
