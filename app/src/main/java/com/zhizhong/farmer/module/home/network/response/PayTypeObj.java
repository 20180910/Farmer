package com.zhizhong.farmer.module.home.network.response;

import com.zhizhong.farmer.base.BaseObj;

/**
 * Created by administartor on 2017/8/28.
 */

public class PayTypeObj extends BaseObj {

    /**
     * payment_type : 1
     * payment_url : http://121.40.186.118:5009/app/AliSecurity/notifyUrl.aspx
     */

    private int payment_type;
    private String payment_url;

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }
}
