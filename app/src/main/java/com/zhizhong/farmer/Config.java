package com.zhizhong.farmer;

/**
 * Created by Administrator on 2017/7/12.
 */

public class Config {
    public static final Object KEY = "D98TZQWpTVlb1nqfkfO615U5ZEignoqW";
    //用户id
    public static final String user_id="user_id";
    public static final String user_name="user_name";
    public static final String sex="sex";
    public static final String area="area";
    public static final String mobile="mobile";
    public static final String avatar="avatar";
    public static final String isFirstIntoApp="isFirstIntoApp";
    public static final String userType="userType";
    public static final int userType_farmer =2;//农户
    public static final int userType_tgy =3;//推广员
    public static final String exitAPP ="exitAPP";//退出登录参数名


    public static final String loginAppType_1 ="1";//飞首app
    public static final String loginAppType_2 ="2";//农户app
    public static class SP{
        public static final String dai_kuan_edu_order="dai_kuan_edu_order";
        public static final String dai_kuan_money_order="dai_kuan_money_order";
        public static final String dai_kuan_qixian_order="dai_kuan_qixian_order";

        public static final String xinyong_youhui_bank="xinyong_youhui_bank";
        public static final String xinyong_youhui_type="xinyong_youhui_type";
    }

    //百度地图-KEY：8aFeUXK0pzqiFi58WUniIV8S2RWCVNq7

    public static final String pwd="pwd";
    public static final String nick_name="nick_name";
    public static final String distribution_yard="distribution_yard";
    public static final String birthday="birthday";
    public static final String level="level";
    public static final String authentication="authentication";


    /*广播参数*/
    public static class Bro{
        public static final String operation ="operation";
        public static final String flag="flag";
        public static final int exit_login=1000;
        public static final int addHomeworkSuccess=1001;
    }
    public static class IParam{
        //退出action
        public static final String selectUser ="selectUser";
    }
}
