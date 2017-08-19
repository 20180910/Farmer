package com.zhizhong.farmer.module.tuiguangyuan.network.response;

/**
 * Created by administartor on 2017/8/17.
 */

public class MessageObj {

    /**
     * id : 16
     * title : 注册成功!
     * zhaiyao : 恭喜，您已成功注册益农宝会员，系统自动赠送您价值200元的优惠券，快去看看吧～
     * add_time : 2017/8/9
     */

    private int id;
    private String title;
    private String zhaiyao;
    private String add_time;

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

    public String getZhaiyao() {
        return zhaiyao;
    }

    public void setZhaiyao(String zhaiyao) {
        this.zhaiyao = zhaiyao;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
