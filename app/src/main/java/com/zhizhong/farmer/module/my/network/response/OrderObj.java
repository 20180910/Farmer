package com.zhizhong.farmer.module.my.network.response;

/**
 * Created by administartor on 2017/8/21.
 */

public class OrderObj {
    /**
     * id : 39
     * nh_order_no : N201708211541267304
     * farmer_name : 范磊
     * photo : http://121.40.186.118:5009/upload/201708/03/17080318212871209741.jpg
     * address :
     * region :
     * crops : 玉米
     * price : 0元/亩
     * area : 130499亩
     * request_time : 2017/8/21~2017/8/22  (1天)
     * nh_order_status : 1
     * add_time : 2017/8/21
     */

    private int id;
    private String nh_order_no;
    private String farmer_name;
    private String photo;
    private String address;
    private String region;
    private String crops;
    private String price;
    private String area;
    private String request_time;
    private int nh_order_status;
    private String add_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNh_order_no() {
        return nh_order_no;
    }

    public void setNh_order_no(String nh_order_no) {
        this.nh_order_no = nh_order_no;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public int getNh_order_status() {
        return nh_order_status;
    }

    public void setNh_order_status(int nh_order_status) {
        this.nh_order_status = nh_order_status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
