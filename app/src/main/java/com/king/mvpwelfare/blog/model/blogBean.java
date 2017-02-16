package com.king.mvpwelfare.blog.model;

import java.io.Serializable;

/**
 * Created by 16230 on 2016/12/8.
 */

public class blogBean implements Serializable{
    private String nickName;
    private String title;
    private String viewPeople;
    private String url;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getViewPeople() {
        return viewPeople;
    }

    public void setViewPeople(String viewPeople) {
        this.viewPeople = viewPeople;
    }

    public String getNickName() {
        return nickName;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
