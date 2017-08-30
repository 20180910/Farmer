package com.zhizhong.farmer.module.my.network.response;

import java.io.Serializable;

/**
 * Created by administartor on 2017/8/18.
 */

public class VouchersObj  implements Serializable {
    /**
     * coupons_id : 1
     * title : 优惠券
     * face_value : 1
     * available : 20
     * deadline : 2017/8/25~2017/8/29
     */

    private int coupons_id;
    private String title;
    private int face_value;
    private int available;
    private String deadline;

    public int getCoupons_id() {
        return coupons_id;
    }

    public void setCoupons_id(int coupons_id) {
        this.coupons_id = coupons_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFace_value() {
        return face_value;
    }

    public void setFace_value(int face_value) {
        this.face_value = face_value;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
