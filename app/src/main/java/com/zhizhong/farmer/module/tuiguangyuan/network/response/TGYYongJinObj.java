package com.zhizhong.farmer.module.tuiguangyuan.network.response;

import com.zhizhong.farmer.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/8/17.
 */

public class TGYYongJinObj extends BaseObj {
    /**
     * commission : 236
     * commission_detail_list : [{"remark":"下级分销完成订单￥6000.00","value":"+60.00"},{"remark":"下级分销完成订单￥6000.00","value":"+60.00"},{"remark":"下级分销完成订单￥6000.00","value":"+60.00"},{"remark":"下级分销完成订单￥6000.00","value":"+60.00"},{"remark":"下级分销完成订单￥6000.00","value":"+60.00"}]
     */

    private double commission;
    private List<CommissionDetailListBean> commission_detail_list;

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public List<CommissionDetailListBean> getCommission_detail_list() {
        return commission_detail_list;
    }

    public void setCommission_detail_list(List<CommissionDetailListBean> commission_detail_list) {
        this.commission_detail_list = commission_detail_list;
    }

    public static class CommissionDetailListBean {
        /**
         * remark : 下级分销完成订单￥6000.00
         * value : +60.00
         */

        private String remark;
        private String value;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
