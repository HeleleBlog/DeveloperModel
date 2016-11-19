package com.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class Post {
    private String headImg;//用户头像
    private String name;//昵称
    private String contentStr;//发布信息的内容
    private List<String> contentImgUrl;//发布信息的图片地址数据集

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

    public List<String> getContentImgUrl() {
        return contentImgUrl;
    }

    public void setContentImgUrl(List<String> contentImgUrl) {
        this.contentImgUrl = contentImgUrl;
    }
}
