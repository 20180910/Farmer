package com.zhizhong.farmer.module.home.network.response;

import com.zhizhong.farmer.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/8/19.
 */

public class HomeImgObj extends BaseObj{

    /**
     * roasting_list : [{"img_url":"http://121.40.186.118:5009/upload/1234.jpg"},{"img_url":"http://121.40.186.118:5009/upload/1234.jpg"},{"img_url":"http://121.40.186.118:5009/upload/1234.jpg"}]
     * type_list : [{"title":"咨讯","img_url":"http://121.40.186.118:5009/upload/7.png"},{"title":"下单","img_url":"http://121.40.186.118:5009/upload/7.png"},{"title":"植保","img_url":"http://121.40.186.118:5009/upload/7.png"}]
     * image : http://121.40.186.118:5009/upload/1234.jpg
     */

    private String image;
    private List<RoastingListBean> roasting_list;
    private List<TypeListBean> type_list;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<RoastingListBean> getRoasting_list() {
        return roasting_list;
    }

    public void setRoasting_list(List<RoastingListBean> roasting_list) {
        this.roasting_list = roasting_list;
    }

    public List<TypeListBean> getType_list() {
        return type_list;
    }

    public void setType_list(List<TypeListBean> type_list) {
        this.type_list = type_list;
    }

    public static class RoastingListBean {
        /**
         * img_url : http://121.40.186.118:5009/upload/1234.jpg
         */

        private String img_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }

    public static class TypeListBean {
        /**
         * title : 咨讯
         * img_url : http://121.40.186.118:5009/upload/7.png
         */

        private String title;
        private String img_url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}
