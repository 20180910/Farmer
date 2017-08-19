package com.zhizhong.farmer.module.my.network.response;

import com.zhizhong.farmer.base.BaseObj;

/**
 * Created by administartor on 2017/8/18.
 */

public class MyFarmerObj  extends BaseObj{

    /**
     * id : 2
     * farmers_name : 张二丰
     * phone_number : 13547655542
     * crops : 晚稻
     * addresss : 上海市长宁区
     * area : 500
     * farmland_addresss : 上海市浦东新区康桥镇周园路1228号
     */

    private int id;
    private String farmers_name;
    private String phone_number;
    private String crops;
    private String addresss;
    private String area;
    private String farmland_addresss;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFarmers_name() {
        return farmers_name;
    }

    public void setFarmers_name(String farmers_name) {
        this.farmers_name = farmers_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCrops() {
        return crops;
    }

    public void setCrops(String crops) {
        this.crops = crops;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFarmland_addresss() {
        return farmland_addresss;
    }

    public void setFarmland_addresss(String farmland_addresss) {
        this.farmland_addresss = farmland_addresss;
    }
}

