package com.king.mvpwelfare.image.model;

import java.io.Serializable;

/**图片实体类
 * Created by 16230 on 2016/11/1.
 */

public class ImageBean implements Serializable{
    /**
     * 图片list的id
     * 为了加载当前封面的图片集合
     */
    private String id;
    /**
     * 封面图片
     */
    private String url;
    /**
     * 图集标题
     */
    private String title;
    /**
     * 图集大小
     */
    private String size;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getSize() {
        return size;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSize(String size) {
        this.size = size;
    }


}
