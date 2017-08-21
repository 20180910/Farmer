package com.zhizhong.farmer.module.order.network.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by administartor on 2017/8/21.
 */

public class XiaDingDanObj implements Serializable{

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
        private String name;
        private int ms;
        private String diseases_pests;
        private String zhuji;
        private String weifei;
        private String longyao;

        public int getMs() {
            return ms;
        }

        public void setMs(int ms) {
            this.ms = ms;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDiseases_pests() {
            return diseases_pests;
        }

        public void setDiseases_pests(String diseases_pests) {
            this.diseases_pests = diseases_pests;
        }

        public String getZhuji() {
            return zhuji;
        }

        public void setZhuji(String zhuji) {
            this.zhuji = zhuji;
        }

        public String getWeifei() {
            return weifei;
        }

        public void setWeifei(String weifei) {
            this.weifei = weifei;
        }

        public String getLongyao() {
            return longyao;
        }

        public void setLongyao(String longyao) {
            this.longyao = longyao;
        }

        public int getFarmer_id() {
            return farmer_id;
        }

        public void setFarmer_id(int farmer_id) {
            this.farmer_id = farmer_id;
        }
    }
}
