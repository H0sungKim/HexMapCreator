package com.example.hexmap.Activity;

import com.example.hexmap.Activity.OnItemClickListener;

public class Data {

    private String title;
    private String subTitle;
    private String mapCode;
    private OnItemClickListener onItemClickListener;

    public Data(String title, String subTitle, String mapCode) {
        this.title = title;
        this.subTitle = subTitle;
        this.mapCode = mapCode;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setMapCode(String mapCode) {
        this.mapCode = mapCode;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public String getMapCode() {
        return this.mapCode;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

}