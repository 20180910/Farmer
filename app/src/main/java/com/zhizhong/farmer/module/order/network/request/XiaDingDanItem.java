package com.zhizhong.farmer.module.order.network.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by administartor on 2017/8/21.
 */

public class XiaDingDanItem implements Serializable{


    private List<BodyBean> body;

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean implements Serializable{
        /**
         * farmer_id : 1
         */

        private int farmer_id;

        public int getFarmer_id() {
            return farmer_id;
        }

        public void setFarmer_id(int farmer_id) {
            this.farmer_id = farmer_id;
        }
    }
}
