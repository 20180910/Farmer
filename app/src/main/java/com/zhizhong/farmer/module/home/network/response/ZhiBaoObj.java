package com.zhizhong.farmer.module.home.network.response;

import com.zhizhong.farmer.base.BaseObj;

/**
 * Created by administartor on 2017/8/18.
 */

public class ZhiBaoObj extends BaseObj{

    /**
     * id : 10
     * title : 要把特色优势转化为经济优势和发展优势
     * image_url : http://121.40.186.118:5009/upload/1.png
     * add_time : 2017/8/9
     */

    private int id;
    private String title;
    private String image_url;
    private String add_time;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
