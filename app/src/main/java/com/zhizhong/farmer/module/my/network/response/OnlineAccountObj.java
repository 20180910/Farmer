package com.zhizhong.farmer.module.my.network.response;

/**
 * Created by administartor on 2017/8/28.
 */

public class OnlineAccountObj {
    /**
     * account_opener : 上海银行长宁路支行
     * account_number : 648884125655982213256
     * opening_bank : 上海银行
     */

    private String account_opener;
    private String account_number;
    private String opening_bank;

    public String getAccount_opener() {
        return account_opener;
    }

    public void setAccount_opener(String account_opener) {
        this.account_opener = account_opener;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getOpening_bank() {
        return opening_bank;
    }

    public void setOpening_bank(String opening_bank) {
        this.opening_bank = opening_bank;
    }
}
