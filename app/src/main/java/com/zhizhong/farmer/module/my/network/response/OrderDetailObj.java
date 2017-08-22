package com.zhizhong.farmer.module.my.network.response;

import com.zhizhong.farmer.base.BaseObj;

/**
 * Created by administartor on 2017/8/21.
 */

public class OrderDetailObj extends BaseObj {
    /**
     * farmer_name : 范磊
     * farmer_phone : 13872228829
     * site : 上海市普陀区真北路1780号42号
     * order_status : 待完善
     * order_no : N201708211541267304
     * add_time : 2017/8/21  15:41
     * pay_way :
     * pay_status : 未支付
     * pay_message :
     * crops : 玉米
     * price : 待定价
     * area : 130499亩
     * youhui : 0
     * total_price : 待定价
     * request_time : 2017/8/21~2017/8/22  (1天)
     * transitions_number :
     * transitions_instructions :
     * condition :
     * obstacles :
     * remark : 张一丰:需要微肥、需要助剂、需要农药、病虫害(纹枯病)。1近距离:需要微肥、自购助剂、自购农药、病虫害(害虫)。
     * kefu_phone : 400-800-9888
     */

    private String farmer_name;
    private String farmer_phone;
    private String site;
    private String order_status;
    private String order_no;
    private String add_time;
    private String pay_way;
    private String pay_status;
    private String pay_message;
    private String crops;
    private String price;
    private String area;
    private double youhui;
    private String total_price;
    private String request_time;
    private String transitions_number;
    private String transitions_instructions;
    private String condition;
    private String obstacles;
    private String remark;
    private String kefu_phone;

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getFarmer_phone() {
        return farmer_phone;
    }

    public void setFarmer_phone(String farmer_phone) {
        this.farmer_phone = farmer_phone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPay_message() {
        return pay_message;
    }

    public void setPay_message(String pay_message) {
        this.pay_message = pay_message;
    }

    public String getCrops() {
        return crops;
    }

    public void setCrops(String crops) {
        this.crops = crops;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getYouhui() {
        return youhui;
    }

    public void setYouhui(double youhui) {
        this.youhui = youhui;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getTransitions_number() {
        return transitions_number;
    }

    public void setTransitions_number(String transitions_number) {
        this.transitions_number = transitions_number;
    }

    public String getTransitions_instructions() {
        return transitions_instructions;
    }

    public void setTransitions_instructions(String transitions_instructions) {
        this.transitions_instructions = transitions_instructions;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getObstacles() {
        return obstacles;
    }

    public void setObstacles(String obstacles) {
        this.obstacles = obstacles;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKefu_phone() {
        return kefu_phone;
    }

    public void setKefu_phone(String kefu_phone) {
        this.kefu_phone = kefu_phone;
    }
}
