package com.zhizhong.farmer.module.my;

/**
 * Created by Administrator on 2017/7/13.
 */

public class Constant {
    public static final String YHXY ="YHXY";
    public static final String CJWT = "CJWT";
    public static final String GYWM = "GYWM";
    public static final String title="title";
    public static final String vouchersType_0="0";
    public static final String vouchersType_1="1";
    public static final String vouchersType_2="2";
    public static final String update_name="update_name";
    public static final String update_phone="update_phone";
    public static final String type="type";
    //类别(0全部 1待完善 2待确认 3待接单 4已接单 5已完成 6已取消)
    public static final int type_0=0;
    public static final int type_1=1;
    public static final int type_2=2;
    public static final int type_3=3;
    public static final int type_4=4;
    public static final int type_5=5;
    public static final int type_6=6;
    public static final String orderNo="orderNo";

    public static class RCode{
        public static final int register=100;
        public static final int update_name=200;
        public static final int update_phone=300;
        public static final int login=400;
        public static final int addTool=1000;
        public static final int addHomework=10001;
        public static final int deleteDefaultAccount=200;
    }
    public static class IParam{
        public static final String phone="phone";
        public static final String name="name";
        public static final String user_id="user_id";
        public static final String msgId="msgId";
        public static final String title="title";
        public static final String code="code";
        public static final String account="account";
        public static final String orderType="orderType";
        public static final String orderNo="orderNo";
        public static final String address="address";
        public static final String editFarmer="editFarmer";
    }
}
