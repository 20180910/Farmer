package com.zhizhong.farmer.module.account.event;

/**
 * Created by administartor on 2017/8/17.
 */

public class DeleteAccountEvent {
    public boolean isDeleteDefault;
    public DeleteAccountEvent(boolean isDeleteDefault) {
        this.isDeleteDefault = isDeleteDefault;
    }
}
