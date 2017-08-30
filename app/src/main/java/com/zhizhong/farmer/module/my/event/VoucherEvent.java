package com.zhizhong.farmer.module.my.event;

import com.zhizhong.farmer.module.my.network.response.VouchersObj;

/**
 * Created by administartor on 2017/8/30.
 */

public class VoucherEvent {
    public VouchersObj vouchersObj;

    public VoucherEvent(VouchersObj vouchersObj) {
        this.vouchersObj = vouchersObj;
    }
}
