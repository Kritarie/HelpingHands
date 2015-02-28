package com.awsickapps.helpinghands;

import android.content.SharedPreferences;

import java.util.List;

/**
 * Created by kritarie on 2/28/15.
 *
 * Defines User
 */
public class User {

    private String gcmId;
    private List<String> needHelpWith;
    private List<String> canHelpWith;

    public User() {
    }

    public User(String gcmId, List<String> needHelpWith, List<String> canHelpWith) {
        this.gcmId = gcmId;
        this.needHelpWith = needHelpWith;
        this.canHelpWith = canHelpWith;
    }

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public List<String> getNeedHelpWith() {
        return needHelpWith;
    }

    public void setNeedHelpWith(List<String> needHelpWith) {
        this.needHelpWith = needHelpWith;
    }

    public List<String> getCanHelpWith() {
        return canHelpWith;
    }

    public void setCanHelpWith(List<String> canHelpWith) {
        this.canHelpWith = canHelpWith;
    }
}
