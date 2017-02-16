package com.king.mvpwelfare.live.model;

import java.io.Serializable;

/**
 * Created by 16230 on 2016/11/2.
 */

public class LiveBean implements Serializable{
    private String liveName;
    private String liveTitle;
    private String liveUrl;
    private String people;
    private String roomImage;
    private String idRoom;
    private int type;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getIdRoom() {
        return idRoom;
    }


    public String getLiveName() {
        return liveName;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    public String getPeople() {
        return people;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }
}
