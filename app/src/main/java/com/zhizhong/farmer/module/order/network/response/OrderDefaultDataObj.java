package com.zhizhong.farmer.module.order.network.response;

import com.zhizhong.farmer.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/8/19.
 */

public class OrderDefaultDataObj extends BaseObj {
    /**
     * farmer_name : 范磊
     * mobile : 13872228829
     * list : [{"crop_name":"花生","area":0},{"crop_name":"小麦","area":0},{"crop_name":"大豆","area":0},{"crop_name":"早稻","area":0},{"crop_name":"玉米","area":0}]
     */

    private String farmer_name;
    private String mobile;
    private List<ListBean> list;

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * crop_name : 花生
         * area : 0
         */

        private String crop_name;
        private int area;

        public String getCrop_name() {
            return crop_name;
        }

        public void setCrop_name(String crop_name) {
            this.crop_name = crop_name;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }
    }
}
