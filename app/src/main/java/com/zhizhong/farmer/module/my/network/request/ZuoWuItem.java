package com.zhizhong.farmer.module.my.network.request;

/**
 * Created by administartor on 2017/10/14.
 */

public class ZuoWuItem  {
    /**
     * crops : sample string 1
     * area : 2
     */

    private int type;
    private int my_farmers_crops_id;
    private String crops;
    private int area;

    public String getCrops() {
        return crops;
    }

    public void setCrops(String crops) {
        this.crops = crops;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getArea() {
        return area;
    }

    public int getMy_farmers_crops_id() {
        return my_farmers_crops_id;
    }

    public void setMy_farmers_crops_id(int my_farmers_crops_id) {
        this.my_farmers_crops_id = my_farmers_crops_id;
    }

    public void setArea(int area) {
        this.area = area;
    }
}
