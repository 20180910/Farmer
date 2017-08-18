package com.zhizhong.farmer.module.tgyaccount.event;

/**
 * Created by administartor on 2017/8/17.
 */

public class DeleteTGYAccountEvent {
    public boolean isDeleteDefault;
    public DeleteTGYAccountEvent(boolean isDeleteDefault) {
        this.isDeleteDefault = isDeleteDefault;
    }
}
