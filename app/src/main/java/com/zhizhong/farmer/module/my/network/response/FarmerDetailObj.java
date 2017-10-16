package com.zhizhong.farmer.module.my.network.response;

import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.module.my.network.request.ZuoWuItem;

import java.util.List;

/**
 * Created by administartor on 2017/10/16.
 */

public class FarmerDetailObj extends BaseObj {
    /**
     * id : 50
     * farmers_name : 张三
     * phone_number : 15623698523
     * farmland_province_id : 1770
     * farmland_province : 北京市
     * farmland_city_id : 1804
     * farmland_city : 北京市
     * farmland_area_id : 2174
     * farmland_area : 东城区
     * farmland_addresss : 太可怜了
     * crops_list : [{"crops":"","area":""}]
     */

    private int id;
    private String farmers_name;
    private String phone_number;
    private int farmland_province_id;
    private String farmland_province;
    private int farmland_city_id;
    private String farmland_city;
    private int farmland_area_id;
    private String farmland_area;
    private String farmland_addresss;
    private List<ZuoWuItem> crops_list;

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

    public int getFarmland_province_id() {
        return farmland_province_id;
    }

    public void setFarmland_province_id(int farmland_province_id) {
        this.farmland_province_id = farmland_province_id;
    }

    public String getFarmland_province() {
        return farmland_province;
    }

    public void setFarmland_province(String farmland_province) {
        this.farmland_province = farmland_province;
    }

    public int getFarmland_city_id() {
        return farmland_city_id;
    }

    public void setFarmland_city_id(int farmland_city_id) {
        this.farmland_city_id = farmland_city_id;
    }

    public String getFarmland_city() {
        return farmland_city;
    }

    public void setFarmland_city(String farmland_city) {
        this.farmland_city = farmland_city;
    }

    public int getFarmland_area_id() {
        return farmland_area_id;
    }

    public void setFarmland_area_id(int farmland_area_id) {
        this.farmland_area_id = farmland_area_id;
    }

    public String getFarmland_area() {
        return farmland_area;
    }

    public void setFarmland_area(String farmland_area) {
        this.farmland_area = farmland_area;
    }

    public String getFarmland_addresss() {
        return farmland_addresss;
    }

    public void setFarmland_addresss(String farmland_addresss) {
        this.farmland_addresss = farmland_addresss;
    }

    public List<ZuoWuItem> getCrops_list() {
        return crops_list;
    }

    public void setCrops_list(List<ZuoWuItem> crops_list) {
        this.crops_list = crops_list;
    }

}
