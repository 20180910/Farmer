package com.zhizhong.farmer.module.my.network.request;

import java.util.List;

/**
 * Created by administartor on 2017/10/14.
 */

public class AddFarmerItem {
    private List<ZuoWuItem> body;

    public List<ZuoWuItem> getBody() {
        return body;
    }

    public void setBody(List<ZuoWuItem> body) {
        this.body = body;
    }
}
