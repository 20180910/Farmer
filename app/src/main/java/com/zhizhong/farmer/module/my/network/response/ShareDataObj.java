package com.zhizhong.farmer.module.my.network.response;

import com.zhizhong.farmer.base.BaseObj;

/**
 * Created by administartor on 2017/8/30.
 */

public class ShareDataObj extends BaseObj {
    /**
     * title : 飞农宝可以下载啦!
     * content : 飞农宝是利用互联网和人工智能两大工具为现代化智慧农业打造的一个服务平台，提供飞防服务、植保大数据服务、农资服务、农业金融服务。为农业、农村、农民提供个性化定制、集约化、规模化、全产业链的农业社会化生态服务
     * share_link : https://www.Android.com
     */

    private String title;
    private String content;
    private String share_link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }
}
