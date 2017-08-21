package com.zhizhong.farmer.module.order.network.response;

/**
 * Created by administartor on 2017/8/21.
 */

public class OtherFarmerObj {
    /**
     * id : 3
     * farmers_name : 张一丰
     * area : 500
     * phone_number : 15657567897
     */

    private int id;
    private String farmers_name;
    private int area;
    private String phone_number;
    private String haiChong;
    private boolean isSelect;
    private boolean isSelectHaiChong;
    private boolean needZhuJi;
    private boolean needWeiFei;
    private boolean needNongYao;

    public boolean isNeedZhuJi() {
        return needZhuJi;
    }

    public String getHaiChong() {
        return haiChong;
    }

    public void setHaiChong(String haiChong) {
        this.haiChong = haiChong;
    }

    public void setNeedZhuJi(boolean needZhuJi) {
        this.needZhuJi = needZhuJi;
    }

    public boolean isNeedWeiFei() {
        return needWeiFei;
    }

    public void setNeedWeiFei(boolean needWeiFei) {
        this.needWeiFei = needWeiFei;
    }

    public boolean isNeedNongYao() {
        return needNongYao;
    }

    public void setNeedNongYao(boolean needNongYao) {
        this.needNongYao = needNongYao;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelectHaiChong() {
        return isSelectHaiChong;
    }

    public void setSelectHaiChong(boolean selectHaiChong) {
        isSelectHaiChong = selectHaiChong;
    }

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

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
