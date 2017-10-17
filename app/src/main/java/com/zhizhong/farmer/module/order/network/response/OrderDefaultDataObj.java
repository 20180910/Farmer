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
    private String addresss;
    private List<ListBean> list;
    private NongYaoBean pesticide;
    private NongYaoBean additives;
    private NongYaoBean fertilizer;

    public NongYaoBean getPesticide() {
        return pesticide;
    }

    public void setPesticide(NongYaoBean pesticide) {
        this.pesticide = pesticide;
    }

    public NongYaoBean getAdditives() {
        return additives;
    }

    public void setAdditives(NongYaoBean additives) {
        this.additives = additives;
    }

    public NongYaoBean getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(NongYaoBean fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
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
    public static class NongYaoBean{
        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
