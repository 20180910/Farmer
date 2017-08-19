package com.zhizhong.farmer.module.zixun.network.response;

import com.zhizhong.farmer.base.BaseObj;

/**
 * Created by administartor on 2017/8/19.
 */

public class ZiXunObj extends BaseObj{

    /**
     * id : 10
     * title : 农业部经管司长诠释土地制度改革:“三权分置”“有别于“联产承包”
     * image_url : http://121.40.186.118:5009/upload/1234.jpg
     * page_view : 874
     * add_time : 2017/8/9
     */

    private int id;
    private String title;
    private String image_url;
    private int page_view;
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

    public int getPage_view() {
        return page_view;
    }

    public void setPage_view(int page_view) {
        this.page_view = page_view;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
