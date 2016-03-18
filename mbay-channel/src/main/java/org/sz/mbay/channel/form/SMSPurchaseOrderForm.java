package org.sz.mbay.channel.form;

import org.sz.mbay.base.qo.BaseForm;

public class SMSPurchaseOrderForm extends BaseForm{

    public SMSPurchaseOrderForm () {

    }

    /**美贝预存流水号***/
    private String depositNumber;

    private String userNumber;

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }
}
