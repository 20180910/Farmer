package com.zhizhong.farmer.module.my.network.response;

import com.zhizhong.farmer.base.BaseObj;

/**
 * Created by administartor on 2017/8/18.
 */

public class FenXiaoObj extends BaseObj {
    /**
     * user_id : 20
     * distribution_yard : X6VFXD
     * lower_level : 0人
     * commission : ￥0.00
     * voucher : 0张
     */

    private int user_id;
    private String distribution_yard;
    private String lower_level;
    private String commission;
    private String voucher;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDistribution_yard() {
        return distribution_yard;
    }

    public void setDistribution_yard(String distribution_yard) {
        this.distribution_yard = distribution_yard;
    }

    public String getLower_level() {
        return lower_level;
    }

    public void setLower_level(String lower_level) {
        this.lower_level = lower_level;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
}
