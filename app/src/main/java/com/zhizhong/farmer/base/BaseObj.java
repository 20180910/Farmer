package com.zhizhong.farmer.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/20.
 */

public class BaseObj implements Serializable {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    private String SMSCode;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSMSCode() {
        return SMSCode;
    }

    public void setSMSCode(String SMSCode) {
        this.SMSCode = SMSCode;
    }

    private double allmoney;//全部金额
    private String agreement;//提现协议
    private String promoters_agreement;//推广员注册协议
    private String realname_authentication;//实名认证协议

    public String getPromoters_agreement() {
        return promoters_agreement;
    }

    public void setPromoters_agreement(String promoters_agreement) {
        this.promoters_agreement = promoters_agreement;
    }

    public String getRealname_authentication() {
        return realname_authentication;
    }

    public void setRealname_authentication(String realname_authentication) {
        this.realname_authentication = realname_authentication;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public double getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(double allmoney) {
        this.allmoney = allmoney;
    }
}
