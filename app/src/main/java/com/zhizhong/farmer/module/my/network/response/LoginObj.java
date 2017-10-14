package com.zhizhong.farmer.module.my.network.response;

import com.zhizhong.farmer.base.BaseObj;

/**
 * Created by administartor on 2017/8/17.
 */

public class LoginObj  extends BaseObj{
    /**
     * user_id : 16
     * user_name : 15601772925
     * nick_name : 15601772925
     * avatar :
     * sex :
     * mobile : 15601772925
     * birthday : 2017-08-17 16:25:07
     * is_authentication : 0
     * user_level : 0
     * distribution_yard : 2N2RVZ
     */

    private int user_id;
    private String user_name;
    private String nick_name;
    private String avatar;
    private String sex;
    private String mobile;
    private String birthday;
    private int is_authentication;
    private int user_level;
    private String distribution_yard;
    private String address;

    private  int live_province_id;
    private  String live_province;

    private  int live_city_id;
    private  String live_city;

    private  int live_area_id;
    private  String live_area;



    public String getLive_province() {
        return live_province;
    }

    public void setLive_province(String live_province) {
        this.live_province = live_province;
    }



    public String getLive_city() {
        return live_city;
    }

    public void setLive_city(String live_city) {
        this.live_city = live_city;
    }

    public int getLive_province_id() {
        return live_province_id;
    }

    public void setLive_province_id(int live_province_id) {
        this.live_province_id = live_province_id;
    }

    public int getLive_city_id() {
        return live_city_id;
    }

    public void setLive_city_id(int live_city_id) {
        this.live_city_id = live_city_id;
    }

    public int getLive_area_id() {
        return live_area_id;
    }

    public void setLive_area_id(int live_area_id) {
        this.live_area_id = live_area_id;
    }

    public String getLive_area() {
        return live_area;
    }

    public void setLive_area(String live_area) {
        this.live_area = live_area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getIs_authentication() {
        return is_authentication;
    }

    public void setIs_authentication(int is_authentication) {
        this.is_authentication = is_authentication;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public String getDistribution_yard() {
        return distribution_yard;
    }

    public void setDistribution_yard(String distribution_yard) {
        this.distribution_yard = distribution_yard;
    }
}
